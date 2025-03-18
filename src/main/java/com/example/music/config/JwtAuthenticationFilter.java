package com.example.music.config;

import com.example.music.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//
//        if(authHeader == null || !authHeader.startsWith("Bearer")) {
//            filterChain.doFilter(request,response);
//            return;
//        }
//
//        final String jwt = authHeader.substring(7);
//        final String userName = jwtService.extractUserName(jwt);
//
//        Authentication authentication
//                = SecurityContextHolder.getContext().getAuthentication();
//
//        if(userName !=null  && authentication == null) {
//            //Authenticate
//            UserDetails userDetails
//                    = userDetailsService.loadUserByUsername(userName);
//
//            if(jwtService.isTokenValid(jwt,userDetails)) {
//                UsernamePasswordAuthenticationToken authenticationToken
//                        = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authenticationToken.setDetails(
//                        new WebAuthenticationDetailsSource()
//                                .buildDetails(request)
//                );
//                SecurityContextHolder.getContext()
//                        .setAuthentication(authenticationToken);
//            }
//            System.out.println("User Authorities: " + userDetails.getAuthorities());
//        }
//
//        filterChain.doFilter(request,response);
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userName = jwtService.extractUserName(jwt);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            System.out.println("User Authorities: " + userDetails.getAuthorities());

        }

        filterChain.doFilter(request, response);
    }

}
