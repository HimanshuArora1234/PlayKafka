package filters;

import akka.actor.ActorRef;
import akka.stream.Materializer;
import annexe.ActorCreator;
import annexe.KafkaProducerActor;
import annexe.LogProducer;
import org.apache.kafka.clients.producer.Producer;
import play.Logger;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;
import utils.LogUtility;

import javax.inject.Inject;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class LoggingFilter extends Filter {

    Materializer mat;
    private ActorCreator actorCreator;
    @Inject
    public LoggingFilter(Materializer mat, ActorCreator creator) {
        super(mat);
        this.mat = mat;
        this.actorCreator = creator;
    }

    @Override
    public CompletionStage<Result> apply(Function<Http.RequestHeader, CompletionStage<Result>> nextFilter, Http.RequestHeader requestHeader) {
        long startTime = System.currentTimeMillis();
        return nextFilter.apply(requestHeader).thenApply(result -> {
            long endTime = System.currentTimeMillis();
            long requestTime = endTime- startTime;
            result.headers().forEach((k, v) -> System.out.println("Key = " + k + " Val = " + v));
            akka.util.ByteString body = play.core.j.JavaResultExtractor.getBody(result, 10000l, mat);

            if (result.status() >= 500) {
                Logger.error(">> Status {} ({}ms) : {}",
                        result.status(), requestTime, body.decodeString("UTF-8"));
            }
            else if (result.status() >= 400) {
                Logger.warn(">> Status {} ({}ms) : {}",
                        result.status(), requestTime, body.decodeString("UTF-8"));
            }
            else {
                Logger.info(">> Status {}({}ms)",
                        result.status(), requestTime);
            }
            actorCreator.getKafkaProducerActorRef().tell(new KafkaProducerActor.LogMessage(LogUtility.toJson(result, Optional.of(requestTime))), ActorRef.noSender());
            return result;
        });
    }
}
