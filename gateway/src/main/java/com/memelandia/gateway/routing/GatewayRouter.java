package com.memelandia.gateway.routing;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouter {

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
		.route("user-service", route -> route.path("/users/**").uri("lb://user-service"))
		.route("category-service", route -> route.path("/categories/**").uri("lb://category-service"))
		.route("meme-service", route -> route.path("/memes/**").uri("lb://meme-service"))
		// ##### swagger
        .route("user-service-docs", r -> r
            .path("/v3/api-docs/user/**")
            .filters(f -> f.rewritePath(
                "/v3/api-docs/user(?<segment>/?.*)",
                "/v3/api-docs${segment}"
            ))
            .uri("lb://user-service")
        )
        .route("category-service-docs", r -> r
            .path("/v3/api-docs/category/**")
            .filters(f -> f.rewritePath(
                "/v3/api-docs/category(?<segment>/?.*)",
                "/v3/api-docs${segment}"
            ))
            .uri("lb://category-service")
        )
        .route("meme-service-docs", r -> r
            .path("/v3/api-docs/meme/**")
            .filters(f -> f.rewritePath(
                "/v3/api-docs/meme(?<segment>/?.*)",
                "/v3/api-docs${segment}"
            ))
            .uri("lb://meme-service")
        )
        .build();
    }

}
