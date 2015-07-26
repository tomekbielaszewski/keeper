package org.grizz.keeper.springconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Grizz on 2015-07-26.
 */
@Component
class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, AuthenticationFailureHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response(response);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response(response);
    }

    private void response(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        EntryEntity error = ErrorEntry.unauthorized();

        response.getOutputStream().println(new ObjectMapper().writeValueAsString(error));
    }
}