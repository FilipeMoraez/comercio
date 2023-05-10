package br.com.comercio.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class JwtUtil {

        private String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        private static final String TOKEN_PREFIX = "Bearer ";
        public static final long EXPIRATION_TIME = 900_000; // 15 mins

        public Map<String, Claim> getAllClaimsFromToken(String token) {
                try{
                    token = token.replace(TOKEN_PREFIX, "");
                    Map<String, Claim> claims = new HashMap<String, Claim>();
                    claims = JWT.require(Algorithm.HMAC512(secret))
                            .build()
                            .verify(token)
                            .getClaims();
                    return claims;                   
                } catch (Exception e) {
                        System.out.println("Token Invalido(01): " + e.getMessage());
                        return null;
                }
        }

        public Date getExpiration(String token) {
            try{
                token = token.replace(TOKEN_PREFIX, "");
                    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret))
                            .build()
                            .verify(token);
                    return decodedJWT.getExpiresAt();
                } catch (Exception e) {
                        System.out.println("Token Invalido(02): " + e.getMessage());
                        return null;
                }
        }

        public String getUsuarioKey(String token) {
            Map<String, Claim> claims = this.getAllClaimsFromToken(token);
            try {
                return claims.get("code").toString();
            } catch (Exception e) {
                System.out.println(">>>>> Falha ao processar Token JWT!!! Token inválido. Usuario nao encontrado: " + e.getMessage());
                return null;
                }
        }

        private boolean isTokenExpired(String token) {
            try {
                return this.getExpiration(token).before(new Date());
            } catch (Exception e){
                System.out.println("Token Invalido(isTokenExpired): " + e.getMessage());
                return false; 
            }
        }

        public boolean isInvalid(String token) {
                try {
                        String usuario = this.getUsuarioKey(token);
                        if (usuario == null) {
                                return false;
                        }
                } catch (Exception e) {
                        System.out.println("Token Invalido(isInvalid): " + e.getMessage());
                        e.printStackTrace();
                        return false;
                }
                return this.isTokenExpired(token);
        }
}
