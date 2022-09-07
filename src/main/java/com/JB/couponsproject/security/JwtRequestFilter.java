package com.JB.couponsproject.security;

import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import com.JB.couponsproject.services.ProjUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String BEARER_AUTH_SCHEME_START = "Bearer ";
    private static final int TOKEN_START_INDEX = 7;

    private static final String COMPANY_URI = "company";
    private static final String CUSTOMER_URI = "customer";

    private final ProjUserDetailsService projUserDetailsService;
    private final CustomerService customerService;
    private final CompanyService companyService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
         final String authorizationHeader = request.getHeader("Authorization");

         if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_AUTH_SCHEME_START)) {
             final String jwt = authorizationHeader.substring(TOKEN_START_INDEX);
             final String email = JwtUtil.extractEmail(jwt);

             if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                 final UserDetails userDetails = this.projUserDetailsService.loadUserByUsername(email);

                 if (JwtUtil.validateToken(jwt, userDetails)) {
                     UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(
                             userDetails, null, userDetails.getAuthorities()
                     );

                     upaToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                     SecurityContextHolder.getContext().setAuthentication(upaToken);
                 }
             }
         }

        filterChain.doFilter(request, response);
    }
}
