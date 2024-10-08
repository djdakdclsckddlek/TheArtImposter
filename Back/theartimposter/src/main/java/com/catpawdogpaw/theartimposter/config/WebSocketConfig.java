package com.catpawdogpaw.theartimposter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.catpawdogpaw.theartimposter.match.MatchHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final MatchHandler matchHandler;
    private final UserInterceptor userInterceptor;
    
    // 클라이언트가 WebSocket 서버에 연결하기 위한 엔드포인트
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wait-service/wait-websocket")
                .setAllowedOrigins("http://localhost:9080")
                .addInterceptors(new WebSocketSessionInterceptor(matchHandler))
                .withSockJS();
    }
    
    // 메세지 브로거 옵션 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/wait-service/waitroom/sub", "/queue");
        registry.setApplicationDestinationPrefixes("/wait-service");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(userInterceptor);
    }
    
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketSessionHandlerDecoratorFactory(matchHandler));
    }
    
    
}