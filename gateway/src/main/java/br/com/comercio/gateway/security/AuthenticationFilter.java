package br.com.comercio.gateway.security;

import br.com.comercio.gateway.util.JwtUtil;
import com.auth0.jwt.interfaces.Claim;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements WebFilter {

        @Autowired
        private RouterValidator routerValidator;
        @Autowired
        private JwtUtil jwtUtil;

        private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(httpStatus);
                return response.setComplete();
        }

        private String getAuthHeader(ServerHttpRequest request) {
                return request.getHeaders().getOrEmpty("Authorization").get(0);
        }

        private boolean isAuthMissing(ServerHttpRequest request) {
                return !request.getHeaders().containsKey("Authorization");
        }

        private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
            try{
                Jws<Claims> claims = jwtUtil.getAllClaimsFromToken(token);
                exchange.getRequest().mutate()
                        .header("login", String.valueOf(claims.getBody().get("code")))
                        .build();
            }catch(Exception e) {
                System.out.println(">>>>> Erro: populateRequestWithHeaders: " + e.getMessage());
            }
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();

                if(!routerValidator.isOpenURL.test(request)){
                    return chain.filter(exchange);
                }else {

                    if (routerValidator.isSecured.test(request)) {
                        if (this.isAuthMissing(request)) {
                            System.out.println(">>>>> cabeçalho de autorização está ausente na solicitação " + HttpStatus.UNAUTHORIZED);
                            System.out.println(request.getURI().getPath());
                            return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
                        }

                        final String token = this.getAuthHeader(request);
                        System.out.println("Token: " + token);

                        String user = jwtUtil.getUsuarioKey(token);
                        if (user != null) {
                            System.out.println("Usuário autenticado: " + user + " < << <<<");
                        }

                        if (user == null) {
                            System.out.println(">>>>> Usuario nao autenticado: " + HttpStatus.UNAUTHORIZED);
                            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                        }

                        if (jwtUtil.isInvalid(token)) {
                            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                        }

                        this.populateRequestWithHeaders(exchange, token);
                    }
                    return chain.filter(exchange);
                }
        }
}
