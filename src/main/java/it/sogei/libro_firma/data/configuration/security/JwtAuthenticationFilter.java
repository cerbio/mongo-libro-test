package it.sogei.libro_firma.data.configuration.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
    private static final String tokenHeader = "Authorization";
	
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(RequestMatcher requestMatcher, JwtTokenUtil jwtTokenUtil) {
        super(requestMatcher);
        this.jwtTokenUtil = jwtTokenUtil;
    }
    
    /**
     * @see AbstractAuthenticationProcessingFilter
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	
    	String authToken = request.getHeader(tokenHeader);
        
        if(StringUtils.isBlank(authToken)) {
        	throw new DataAuthenticationException("TOKEN JWT NON TROVATO");
        }
        authToken = authToken.replace("Bearer ", "");
        try {
	        UserDetails userDetails = null;
	        if(authToken != null){
	            userDetails = jwtTokenUtil.getUserDetails(authToken);
	        }
	        if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	                return authentication;
	            }
	        }
        }
        catch(Exception e) {
        	log.error("JwtAuthenticationTokenFilter.doFilterInternal: ERROR", e);
        	throw new DataAuthenticationException("Token JWT non valido");
        }
        throw new DataAuthenticationException("Token JWT non valido");
    }

    /**
     * 
     * @param authResult
     */
    private void setSecurityContext(Authentication authResult) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
    }

    /**
     * @ee AbstractAuthenticationProcessingFilter
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.debug("Successful authentication for user "+ authResult.getName());
        setSecurityContext(authResult);
        chain.doFilter(request, response);
    }

    /**
     * @see AbstractAuthenticationProcessingFilter
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        SecurityContextHolder.clearContext();
        log.error("Unsuccessful Authenticaion reason: {}.", failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }
    
}
