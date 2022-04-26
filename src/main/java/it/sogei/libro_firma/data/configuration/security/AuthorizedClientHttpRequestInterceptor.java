package it.sogei.libro_firma.data.configuration.security;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizedClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	
	public AuthorizedClientHttpRequestInterceptor() {
		super();
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			JwtUser user = (JwtUser) authentication.getPrincipal();
			request.getHeaders().set("Authorization", "Bearer " + user.getToken());
		}
		return execution.execute(request, body);
	}
	
}