package com.rocs.infirmary.application.utils.security.jwt.token.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rocs.infirmary.application.domain.user.principal.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.rocs.infirmary.application.utils.security.constants.SecurityConstant.*;
import static java.util.Arrays.stream;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;
/**
 * {@code JwtTokenProvider} Provider is responsible for generating, validating, and extracting details from the JSON Web Token Tokens.
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;
    /**
     * Generates JWT token
     * @param userPrincipal whenever a user logs in, details are extracted from the user principal
     * @return the jwt token
     * */
    public String generateJwtToken(UserPrincipal userPrincipal){
        String[] claims = getClaimFromUser(userPrincipal);
        return JWT.create().withIssuer(ROCS).withAudience(ROCS_ADMINISTRATION)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES,claims).withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }
    /**
     * Get authorities from the token.
     * @param token the jwt token
     * @return the list of authorities from the token
     * */
    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * Gets the authentication of the user. If the token is correct, then security can get the authentication of the user
     * and set that authentication in the spring security context.
     *
     * @param username the username of the user.
     * @param authorities list of authorities of the user.
     * @param request the user request
     * @return the authentication of the user.
     * */
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
        userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPassAuthToken;
    }
    /**
     * Gets the subject from the token.
     * @param token the jwt token
     * @return the subject from the token.
     * */
    public String getSubject(String token){
        JWTVerifier jwtVerifier = getVerifier();
        return jwtVerifier.verify(token).getSubject();
    }
    /**
     * Checks if the token is valid.
     * @param username the username of the user.
     * @param token the jwt token
     * @return true if the token is valid.
     * */
    public boolean isTokenValid(String username, String token){
        JWTVerifier jwtVerifier = getVerifier();
        return isNotEmpty(username) && !isTokenExpire(jwtVerifier,token);
    }
    private String[] getClaimFromUser(UserPrincipal user){
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority grantedAuthority:user.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    private String[] getClaimsFromToken(String token){
        JWTVerifier jwtVerifier = getVerifier();
        return jwtVerifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getVerifier(){
        JWTVerifier jwtVerifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            jwtVerifier = JWT.require(algorithm).withIssuer(ROCS).build();
        }catch (JWTVerificationException e){
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return jwtVerifier;
    }
    private boolean isTokenExpire(JWTVerifier verifier, String token){
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }
}
