package org.example.chatws.config;

/*/mport org.example.chatws.utility.Constants;*/
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Prefijo para los destinos de tipo topic (suscripciones)
    public static final String TOPIC = "/topic";
    // Prefijo para los destinos de la aplicación (mensajes enviados por clientes)
    public static final String APP = "/app";

   
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC, "/queue");
        config.setApplicationDestinationPrefixes(APP);
    }

    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("chat").withSockJS();
    }
}
