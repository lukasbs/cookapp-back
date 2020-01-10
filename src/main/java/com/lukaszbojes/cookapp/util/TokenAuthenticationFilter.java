package com.lukaszbojes.cookapp.util;

import com.lukaszbojes.cookapp.data.entity.User;
import com.lukaszbojes.cookapp.data.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        final HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;

        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        httpResponse.setHeader("Access-Control-Allow-Headers","Content-Type, Authorization");

        final String accessToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        //Check for preflight request
        if(!httpRequest.getMethod().equals(Constants.OPTIONS)){
            //Every user should have access to /login or /register endpoint
            if(httpRequest.getRequestURI().contains(Constants.AUTH_URL))
                filterChain.doFilter(servletRequest, servletResponse);
                //If token is provided, try parsing it
            else if (accessToken != null) {
                try{
                    Jws<Claims> claims = Jwts.parser()
                            .setSigningKey(TextCodec.BASE64.decode(environment.getProperty(Constants.SECRET_KEY_PROPERTY)))
                            .parseClaimsJws(accessToken);
                    String name = (String)claims.getBody().get(Constants.FIELD_NAME);
                    String password = (String)claims.getBody().get(Constants.FIELD_PASSWORD);
                    //Find user in database if provided token contains name and password
                    if(name != null && password != null){
                        this.user = this.userRepository.findByNameAndPassword(name, password);
                        //Unauthorized if token does not contain name and password
                    } else
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    //If user was found in database
                    if(this.user != null) {
                        //If user requests access to admin resources
                        if(httpRequest.getRequestURI().contains(Constants.ADMIN_URL)) {
                            //Allow access if user role is ADMIN
                            if(this.user.getRole().equals(Constants.ROLE_ADMIN)){
                                filterChain.doFilter(servletRequest, servletResponse);
                                //Unauthorized if user role is USER
                            } else
                                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            //Allow access if user requests access to common resources
                        } else
                            filterChain.doFilter(servletRequest, servletResponse);
                        //Unauthorized if no user with provided name and password was found
                    } else
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    //Unauthorized if token is invalid (Exception during token parsing)
                } catch (Exception e) {
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                //Unauthorized if token is not provided
            } else
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else
            httpResponse.sendError(HttpServletResponse.SC_OK);
    }
}
