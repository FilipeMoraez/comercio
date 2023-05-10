package br.com.comercio.gateway.security;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

        public static final List<String> openApiEndpoints = List.of(
                "/saldo/api/balance",
                "/saldo/api/debito",
                "/saldo/api/credito"
        );

        public static final List<String> openURLS = List.of(
                "/admin/api/login",
                "/admin/v3/api-docs",
                "/admin/swagger-ui/index.html",
                "admin/swagger-ui"
        );

        public Predicate<ServerHttpRequest> isOpenURL = request -> openURLS
                .stream()
                .noneMatch(uri ->{
                                System.out.print(request.getURI().getPath());
                        return request.getURI().getPath().contains(uri);});

        public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
