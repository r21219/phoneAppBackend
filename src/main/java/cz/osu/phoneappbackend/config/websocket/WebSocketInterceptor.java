package cz.osu.phoneappbackend.config.websocket;

import cz.osu.phoneappbackend.config.jwt.JwtService;
import cz.osu.phoneappbackend.model.customer.Customer;
import cz.osu.phoneappbackend.model.exception.NotFoundException;
import cz.osu.phoneappbackend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class WebSocketInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String query = request.getURI().getQuery();
        if (query == null) {
            return false;
        }
        boolean isTokenValid = false;
        String[] params = query.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");

            if ("token".equals(keyValue[0]) && keyValue.length > 1) {
                String jwtToken = keyValue[1];
                String username = jwtService.extractUserName(jwtToken);
                Customer userDetails = customerRepository.findByUserName(username).orElseThrow(() -> new NotFoundException("User not found."));
                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    isTokenValid = true;
                }
            }
        }
        return isTokenValid;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
