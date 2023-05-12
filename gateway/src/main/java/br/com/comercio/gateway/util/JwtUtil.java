package br.com.comercio.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;


@Component
public class JwtUtil {

        private String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        private static final String TOKEN_PREFIX = "Bearer ";
        public static final long EXPIRATION_TIME = 900_000; // 15 mins



        public Jws<Claims> getAllClaimsFromToken(String token) {

                try{
                    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                            SignatureAlgorithm.HS256.getJcaName());

                    token = token.replace(TOKEN_PREFIX, "");

                    Jws<Claims> claims = Jwts.parserBuilder()
                            .setSigningKey(hmacKey)
                            .build()
                            .parseClaimsJws(token);
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
            Jws<Claims> claims = this.getAllClaimsFromToken(token);
            try {
                return claims.getBody().get("code").toString();
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
