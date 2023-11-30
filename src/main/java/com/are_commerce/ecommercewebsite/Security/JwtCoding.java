package com.are_commerce.ecommercewebsite.Security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtCoding {

    private static final Logger logger = LoggerFactory.getLogger(JwtCoding.class);
    RsaKeysConfig rsaKeysConfig;

    @Autowired
    public JwtCoding(RsaKeysConfig rsaKeysConfig){
        logger.info("JwtCoding: Initializing with injected beans.");
        this.rsaKeysConfig=rsaKeysConfig;
        logger.info("JwtCoding: Beans successfully injected and initialized.");

    }
    @Bean
    JwtDecoder jwtDecoder() {
        logger.info("Creating JwtDecoder bean: Initializing with public key.");
        return NimbusJwtDecoder.withPublicKey(rsaKeysConfig.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        logger.info("Creating JwtEncoder bean: Initializing with public and private keys.");
        JWK jwk = new RSAKey.Builder(rsaKeysConfig.publicKey()).privateKey(rsaKeysConfig.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

}
