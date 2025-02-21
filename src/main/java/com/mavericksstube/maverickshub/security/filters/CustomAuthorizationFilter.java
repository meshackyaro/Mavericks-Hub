package com.mavericksstube.maverickshub.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.mavericksstube.maverickshub.security.utils.SecurityUtils.JWT_PREFIX;
import static com.mavericksstube.maverickshub.security.utils.SecurityUtils.PUBLIC_ENDPOINTS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //retrieve request path
        String requestPath = request.getServletPath(); // "/api/vi/..."
        boolean isRequestPathPublic = PUBLIC_ENDPOINTS.contains(requestPath);
        //if request path is public, call the nxt filter & terminate dis filter

        if(isRequestPathPublic) filterChain.doFilter(request, response);

        //retrieve access token from te request header
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null) {
            String token = authorizationHeader.substring(JWT_PREFIX.length()).strip();

            //decode access token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret".getBytes())).withIssuer("mavericks_hub").withClaimPresence("roles").build();
            DecodedJWT decodedJWT = verifier.verify(token);

            //extract token permission
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles").asList(SimpleGrantedAuthority.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);

            //add token permission to security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        //call the next filter
        filterChain.doFilter(request, response);



    }
}
