package com.are_commerce.ecommercewebsite.Security;

import com.are_commerce.ecommercewebsite.Model.DBUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;


    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    @Autowired
    public JwtTokenUtil(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        logger.info("JwtTokenUtil: Initializing with injected beans.");
        this.jwtDecoder=jwtDecoder;
        this.jwtEncoder=jwtEncoder;
        logger.info("JwtTokenUtil: Beans successfully injected and initialized.");

    }

    public String getUsernameFromToken(String token) {
        System.out.println("    public String getUsernameFromToken(String token) { ");
        logger.debug("Getting username from token: {}", token);
        return getClaimFromToken(token, claims -> claims.get("sub")).toString();
    }

    public Instant getIssuedAtDateFromToken(String token) {
        logger.debug("Getting issued-at date from token: {}", token);
        return getClaimFromToken(token, claims -> (Instant) claims.get("iat"));
    }

    public Instant getExpirationDateFromToken(String token) {
        logger.debug("Getting expiration date from token: {}", token);
        return getClaimFromToken(token, claims -> (Instant) claims.get("exp"));
    }

    public <T> T getClaimFromToken(String token, Function<Map<String, Object>, T> claimsResolver) {
        System.out.println("public <T> T getClaimFromToken(String token, Function<Map<String, Object>, T> claimsResolver) {\n { ");

        final Map<String, Object> claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Map<String, Object> getAllClaimsFromToken(String token) {
        System.out.println("public Map<String, Object> getAllClaimsFromToken(String token) {\n");
        Jwt jwt = jwtDecoder.decode(token);
        System.out.println("after decode");

        logger.debug("Decoding JWT to retrieve claims.");
        return jwt.getClaims();
    }

    private Boolean isTokenExpired(String token) {
        final Instant expiration = getExpirationDateFromToken(token);
        boolean isExpired = expiration.isBefore(Instant.now());
        logger.debug("Is token expired: {}", isExpired);
        return isExpired;
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for which the expiration is ignored
        logger.debug("Ignoring token expiration.");
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        return doGenerateToken(userDetails.getUsername(), userDetails.getAuthorities());
    }

    private String doGenerateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(15, ChronoUnit.SECONDS))
                .issuer("security-service")
                .claim("scope", authorities)
                .build();

        logger.debug("Generating JWT token for subject: {}", jwtClaimsSet.getSubject());
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    public Boolean canTokenBeRefreshed(String token) {
        boolean canRefresh = !isTokenExpired(token) || ignoreTokenExpiration(token);
        logger.debug("Can token be refreshed: {}", canRefresh);
        return canRefresh;
    }

    public Boolean validateToken(String token, String username) {
        boolean isValid = username.equals(getUsernameFromToken(token)) && !isTokenExpired(token);
        logger.debug("Is token valid: {}", isValid);
        return isValid;
    }
    public String getToken(HttpServletRequest httpServletRequest) {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(requestTokenHeader) && requestTokenHeader.startsWith("Bearer "))
            return requestTokenHeader.substring(7);  // The part after "Bearer "
        return null;
    }


}
