package action;

import akka.actor.ActorRef;
import annexe.ActorCreator;
import annexe.KafkaProducerActor;
import annexe.LogProducer;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import utils.LogUtility;
import utils.ValidationUtils;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class LoggingAction implements play.http.ActionCreator  {

    @Inject
    private Configuration config;
    @Inject
    private ActorCreator actorCreator;

    @Override
    public Action createAction(Http.Request request, Method actionMethod) {
        Logger.info("<<< {} : {} {} {}", LogUtility.getCaller(request.getHeader("access_token")), request.method(), request.uri(), Json.stringify(request.body().asJson()));
        actorCreator.getKafkaProducerActorRef().tell(new KafkaProducerActor.LogMessage(LogUtility.toJson(request)), ActorRef.noSender());
        return new Action.Simple() {
            @Override
            public CompletionStage<Result> call(Http.Context ctx) {
                return delegate.call(ctx);
            }
        };
    }

}
