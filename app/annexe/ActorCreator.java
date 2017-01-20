package annexe;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by J16DL00 on 19/01/2017.
 */
@Singleton
public class ActorCreator {
    private final ActorRef kafkaProducerActorRef;
    @Inject
    public ActorCreator(ActorSystem system, LogProducer producer) {
        kafkaProducerActorRef = system.actorOf(KafkaProducerActor.props(producer), "kafka-producer-actor");
    }
    public ActorRef getKafkaProducerActorRef() {
        return kafkaProducerActorRef;
    }
}
