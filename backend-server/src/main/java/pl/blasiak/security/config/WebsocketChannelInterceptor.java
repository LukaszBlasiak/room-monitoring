package pl.blasiak.security.config;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import pl.blasiak.security.service.JwtServiceImpl;

import java.util.List;

@Component
@AllArgsConstructor
public class WebsocketChannelInterceptor implements ChannelInterceptor {

    private final JwtServiceImpl jwtService;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        final StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || !StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }
        final List<String> authorizations = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
        final var authorisationHeader = this.getAuthorisationHeaderFromList(authorizations);
        if (StringUtils.startsWith(authorisationHeader, JwtConstants.BEARER)) {
            final var token = authorisationHeader.replaceFirst(JwtConstants.BEARER, StringUtils.EMPTY).trim();
            final var authentication = this.jwtService.getAuthentication(token);
            accessor.setUser(authentication);
        }
        return message;
    }

    private String getAuthorisationHeaderFromList(final List<String> authorization) {
        return authorization == null || authorization.size() == 0 ? Strings.EMPTY : authorization.get(0);
    }
}
