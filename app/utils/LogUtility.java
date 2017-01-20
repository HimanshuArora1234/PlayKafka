package utils;


import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Configuration;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * Created by J16DL00 on 20/01/2017.
 */
public class LogUtility {
    @Inject
    private static Configuration config;
    private LogUtility() {
      //Utility class
    }

    /**
     * Coverts http request parameters to json string.
     * @param request
     * @return
     */
    public static String toJson(Http.Request request) {
        ObjectNode json = Json.newObject();
        json.put("Type", "Request");
        json.put("Method", request.method());
        json.put("Uri", request.uri());
        json.put("Body", Json.stringify(request.body().asJson()));
        json.put("Caller", getCaller(request.getHeader("access_token")));
        json.put("Host", request.host());
        json.put("RemoteAddress", request.remoteAddress()); //Request caller's IP address
        json.put("User-Agent", request.getHeader("User-Agent"));
        json.put("When", LocalDateTime.now().toString());
        return json.toString();
    }

    public static String toJson(Result result, Optional<Long> requestTime) {
        ObjectNode json = Json.newObject();
        json.put("Type", "Response");
        json.put("Status", String.valueOf(result.status()));
        if (requestTime.isPresent()) json.put("Request-execution-time-ms", requestTime.get().toString());
        json.put("When", LocalDateTime.now().toString());
        return json.toString();
    }


    public static String getCaller(String tokenRequest) {
        if(ValidationUtils.isNotEmpty(tokenRequest)) {
            String[] caller = new String[1];
            Map<String, Object> callersMap = (Map<String, Object>) config.getObject("app.callers");
            // 1er niveau
            callersMap.forEach((theCallerKey, theCallerValue) -> {
                Map<String, Object> callerMap = (Map<String, Object>) theCallerValue;
                final String[] token = {(String) callerMap.get("token")};
                if(token[0] == null) {
                    // 2e niveau si token non trouvé
                    callerMap.forEach((key, value) -> {
                        Map<String, Object> valueMap = (Map<String, Object>) value;
                        token[0] = (String) valueMap.get("token");
                    });
                }

                // Si le token du header match avec un token en conf alors on retourne le label identifiant l'appelant
                if (token[0]!=null && token[0].equals(tokenRequest)) {
                    caller[0] = (String) callerMap.get("label");
                }
            });
            return caller[0];
        }
        return null;
    }
}
