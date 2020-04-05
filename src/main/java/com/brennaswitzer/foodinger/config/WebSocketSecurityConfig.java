package com.brennaswitzer.foodinger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        // @formatter:off
        messages
            .anyMessage().authenticated();
        // @formatter:on
    }

}
