package annexe;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.pf.ReceiveBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;

/**
 * Created by J16DL00 on 19/01/2017.
 */
public class KafkaProducerActor extends AbstractLoggingActor {

    private final LogProducer producer;

    public KafkaProducerActor(LogProducer producer) {
        this.producer = producer;
    }

    //Protocol
    public static class LogMessage {
        public final String msg;
        public LogMessage(String msg) {
            this.msg = msg;
        }
    }

    //Message handler
    {
        receive(ReceiveBuilder
                .match(LogMessage.class, this::onMessage)
                .build()
        );
    }

    private void onMessage(LogMessage msg) {
        producer.send(msg.msg);
    }

    //Props factory
    public static Props props(LogProducer producer) {
        return Props.create(KafkaProducerActor.class, producer);
    }
}
