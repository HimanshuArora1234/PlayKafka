package controllers;

import com.avaje.ebean.Ebean;
import models.User;
import play.*;
import play.libs.Json;
import play.mvc.*;

public class Application extends Controller {

    public Result getUser(String id) {
        return ok(Json.toJson(User.find
                .fetch("addresses")
                .setUseQueryCache(true)
                .where().eq("user_id", Integer.parseInt(id))
                .findList()));
    }

    public Result getAllUsers() {
        return ok(Json.toJson(User.find
                .fetch("addresses")
                .setUseQueryCache(true)
                .findList()));
    }
}
