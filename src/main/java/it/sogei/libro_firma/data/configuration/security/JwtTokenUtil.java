package it.sogei.libro_firma.data.configuration.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultJwtParser;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "iat";
    static final String CLAIM_KEY_AUTHORITIES = "roles";
    static final String CLAIM_KEY_IS_ENABLED = "isEnabled";
    
    public JwtTokenUtil() {
    	super();
    }

    /**
     * 
     * @param token
     * @return
     * @throws DataAuthenticationException
     */
    public String getUsernameFromToken(String token) throws DataAuthenticationException {
    	final Claims claims = getClaimsFromToken(token);
    	return claims.getSubject();
    }
    
    /**
     * 
     * @param token
     * @return
     * @throws DataAuthenticationException
     */
    @SuppressWarnings("unchecked")
	public JwtUser getUserDetails(String token) throws DataAuthenticationException {
        if(token == null){
            return null;
        }
        final Claims claims = getClaimsFromToken(token);
        List<SimpleGrantedAuthority> authorities = null;
        if (claims.get(CLAIM_KEY_AUTHORITIES) != null) {
            authorities = ((List<String>) claims.get(CLAIM_KEY_AUTHORITIES)).stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        }
        return new JwtUser(
                claims.getSubject(),
                "",
                authorities,
                false,
                token
            );
    }
    
    /**
     * 
     * @param token
     * @return
     * @throws DataAuthenticationException
     */
    public Date getCreatedDateFromToken(String token) throws DataAuthenticationException {
        final Claims claims = getClaimsFromToken(token);
        return new Date((Long) claims.get(CLAIM_KEY_CREATED));
    }
    
    /**
     * 
     * @param token
     * @return
     * @throws DataAuthenticationException
     */
    public Date getExpirationDateFromToken(String token) throws DataAuthenticationException {
    	final Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
    
    /**
     * 
     * @param token
     * @return
     * @throws DataAuthenticationException
     */
    public String getAudienceFromToken(String token) throws DataAuthenticationException {
    	final Claims claims = getClaimsFromToken(token);
        return (String) claims.get(CLAIM_KEY_AUDIENCE);
    }
    
    /**
     * 
     * @param token
     * @return
     * @throws DataAuthenticationException
     */
    public Claims getClaimsFromToken(String token) throws DataAuthenticationException {
    	String[] splitToken = token.split("\\.");
    	String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
    	DefaultJwtParser parser = new DefaultJwtParser();
    	try {
    		Jwt<?, ?> jwt = parser.parse(unsignedToken);
            Claims claims = (Claims) jwt.getBody();
        	return claims;
    	}
    	catch(Exception e) {
    		log.error("JwtTokenUtil.getClaimsFromToken: ERROR", e);
    		throw new DataAuthenticationException("Token JW non valido o scaduto");
    	}
    }
    
    /**
     * 
     * @param token
     * @return
     * @throws DataAuthenticationException
     */
    private Boolean isTokenExpired(String token) throws DataAuthenticationException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
  
    /**
     * 
     * @param token
     * @param userDetails
     * @return
     * @throws DataAuthenticationException
     */
    public Boolean validateToken(String token, UserDetails userDetails) throws DataAuthenticationException {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}
