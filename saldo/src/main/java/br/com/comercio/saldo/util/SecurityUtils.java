package br.com.comercio.saldo.util;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SecurityUtils {
    private SecurityUtils(){}

    public static String getCodeAccess(String authorization){
        authorization = authorization.replace("Barrier ", "");
        String base = authorization.split("\\.")[1];

        byte[] bites = Base64.getDecoder().decode(base);
        String json = new String(bites, StandardCharsets.UTF_8);
        var object = new JSONObject(json);

        return object.getString("code");

    }
}
