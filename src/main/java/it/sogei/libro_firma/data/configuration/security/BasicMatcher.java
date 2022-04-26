package it.sogei.libro_firma.data.configuration.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Map;

public interface BasicMatcher {

    Map<String, RequestMatcher> getMatchers() throws ClassNotFoundException;
    
}
