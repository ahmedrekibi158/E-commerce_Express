package com.are_commerce.ecommercewebsite.Security;

import com.are_commerce.ecommercewebsite.Controller.GlobalExceptionHandler;
import com.are_commerce.ecommercewebsite.Service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private final JwtUserDetailsService jwtUserDetailsService;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil){
		this.jwtUserDetailsService=jwtUserDetailsService;
		this.jwtTokenUtil=jwtTokenUtil;
	}
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
			throws ServletException, IOException {

		String jwtToken = jwtTokenUtil.getToken(request);
		System.out.println("filter, jwt: "+jwtToken);

		if (jwtToken != null) {
			try {
				System.out.println("filter, after try");
				String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				System.out.println("filter, after get username from token");

				UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
				System.out.println("filter, after user detail");

				if (jwtTokenUtil.validateToken(jwtToken, username)) {
					System.out.println("filter, validate");

					authenticate(userDetails, request);
				}
			} catch (BadJwtException e) {

				logger.warn("JWT Token mal formed: {}");
				logger.info(e.getMessage());


			} catch (IllegalArgumentException e) {
				logger.error("Unable to get JWT Token: {}");
			}
		} else {
			logger.warn("JWT Token is missing");
		}
		chain.doFilter(request,response);
	}


	public void authenticate(UserDetails userDetails, HttpServletRequest request){
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}
}
