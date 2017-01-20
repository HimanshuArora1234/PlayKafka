package controllers;

import akka.actor.ActorRef;
import annexe.ActorCreator;
import annexe.KafkaProducerActor;
import annexe.LogProducer;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import play.*;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;

public class Application extends Controller {

    public Result getUser(String id) {
        JsonNode res = Json.toJson(User.find
                .fetch("addresses")
                .setUseQueryCache(true)
                .where().eq("user_id", Integer.parseInt(id))
                .findList());
        return ok(res);
    }

    public Result getAllUsers() {
        JsonNode res = Json.toJson(User.find
                .fetch("addresses")
                .setUseQueryCache(true)
                .findList());
        return ok(res);
    }
}
