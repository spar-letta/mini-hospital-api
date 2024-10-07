package auth.application.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import auth.application.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException {

        log.warn(authException.getMessage());

        //ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "oauth.authentication.required");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, authException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            objectMapper.writeValue(response.getOutputStream(), errorResponse);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
