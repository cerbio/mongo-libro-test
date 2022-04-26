package it.sogei.libro_firma.data.configuration.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	
	private Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	private static final String[] SWAGGER_URLS = {
			"/v3/api-docs", 
			"/v3/api-docs/**", 
			"/swagger-resources/**", 
			"/swagger-ui/**", 
			"/swagger-ui.html", 
			"/webjars/**", 
			"/favicon.ico",
			"/openapi/ui**",
			"/openapi/ui/**",
			"/openapi/ui.*"};
	
	private static final String ACTUATOR_URL = "/actuator/**";
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedAuthenticationEntryPoint;
	
	public WebSecurityConfig() {
		super();
	}
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws DataAuthenticationException {
		try {
			httpSecurity
			.csrf().disable()
			.formLogin().disable()
			.logout().disable()
			.httpBasic().disable()
			.cors()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(unauthorizedAuthenticationEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, SWAGGER_URLS).permitAll()
			.antMatchers(ACTUATOR_URL).permitAll()
			.anyRequest().permitAll();
	
//			--httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);--
			httpSecurity.addFilterBefore(getAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	        httpSecurity.headers().cacheControl();
		}
		catch(Exception e) {
			log.error("WebSecurityConfig.configure: ERROR", e);
			throw new DataAuthenticationException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
    }
	
	private Filter getAuthFilter() throws ClassNotFoundException {
		return new JwtAuthenticationFilter(requestMatcher(), jwtTokenUtil);
	}

	@Override
    public void configure(WebSecurity web) {
			web.ignoring().antMatchers(SWAGGER_URLS);
    }
	
	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	private RequestMatcher requestMatcher() throws ClassNotFoundException {
		Map<String, RequestMatcher> requestMatchers = new HashMap<>();
		Map<String, BasicMatcher> beansOfType = applicationContext.getBeansOfType(BasicMatcher.class);
		for (BasicMatcher matcher : beansOfType.values()) {
			Map<String, RequestMatcher> matchers = matcher.getMatchers();
			matchers.forEach((key, requestMatcher) -> {
				requestMatchers.put(key, requestMatcher);
			});
		}

		if (requestMatchers.values().isEmpty()) {
			log.info("PATH SICURO=NESSUNO");
			return new EmptyRequestMatcher();
		}
		
		for(String path : requestMatchers.keySet()) {
			log.info("PATH SICURO={}", path);
		}
		
		return new OrRequestMatcher(requestMatchers.values().toArray(new RequestMatcher[requestMatchers.size()]));
	}
	
}
