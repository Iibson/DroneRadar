package com.droneradar.droneradarbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/client"); // clients subscribe on ws://localhost:8080/client/...
        config.setApplicationDestinationPrefixes("/server"); // clients send requests to ws://localhost:8080/server/...
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //TODO adjust setAllowedOrigins to work in production
        registry
                .addEndpoint("/")  // endpoint prefix (in this case "/" - no prefix)
                .setAllowedOrigins("http://localhost:4200");
    }
}