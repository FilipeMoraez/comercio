package br.com.comercio.login.service;

import br.com.comercio.login.repository.UserRepository;
import br.com.comercio.login.model.User;
import br.com.comercio.login.validator.UserValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository repository;

    public String login(String username, String pass) {
        UserValidator.validEmptyData(username, pass);
        User u = UserValidator.validLoginAndPass(pass, repository.findById(username));
        try{
            final String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName());
            Instant now = Instant.now();
            String jwtToken = Jwts.builder()
                        .claim("code", u.getKeypass())
                        .setSubject("comercio")
                        .setId(UUID.randomUUID().toString())
                        .setIssuedAt(Date.from(now))
                        .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                        .signWith(hmacKey)
                        .compact();
            return jwtToken;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
