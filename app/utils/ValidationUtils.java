package utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import play.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static int SIREN_LENGTH = 9;
    private static int NUM_PVC_LENGTH = 20;
    private static int NUM_CO_PRODUIT_LENGTH = 16;

    /**
     * Méthode de vérification que le siren passé en paramètre respecte les règles suivantes :
     *  - Longeur de champ égale à 9
     *  - Composé de chiffre uniquement
     * @param siren Le siren à vérifier
     * @return
     */
    public static boolean isSiren(String siren) {
        if (isNotEmpty(siren) && SIREN_LENGTH == siren.length()) {
            try {
                Integer.parseInt(siren);
                return true;
            } catch( NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    public static boolean isNumPvc(String numPvc) {
        if(isNotEmpty(numPvc) && lengthMustBeOfMax(NUM_PVC_LENGTH ,numPvc)) {
            return true;
        }
        return false;
    }

    public static boolean isCoProduit(String coProduit){
        if(isNotEmpty(coProduit) && NUM_CO_PRODUIT_LENGTH==coProduit.length()) {
            return true;
        }
        return false;
    }

    public static boolean isNumeric(JsonNode jsonNode) {
        for (char c : jsonNode.asText().toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static boolean lengthMustBeOf(int length, JsonNode jsonNode) {
        return jsonNode.asText().length() == length;
    }

    public static boolean lengthMustBeOfMax (int length, JsonNode jsonNode) {
        return lengthMustBeOfMax(length,jsonNode.asText());
    }

    public static boolean lengthMustBeOfMax (int length, String item) {
        return item.length() <= length;
    }

    public static boolean isOptional(JsonNode jsonNode) {
        return jsonNode==null;
    }

    // TODO CORRECTION DE CETTE METHODE
    public static boolean isValidDateFormat(String value, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            Logger.info("Erreur de formatage de la date");
        }
        return date != null;
    }

    public static boolean isValidEmail(String email) {
         String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(List str) {
        return CollectionUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(List str) {
        return !isEmpty(str);
    }
}