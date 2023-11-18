package com.are_commerce.ecommercewebsite.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Configures the security filter chain, defining how requests should be handled based on their URLs
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->{

            auth.requestMatchers("/login").permitAll();
            auth.requestMatchers("/admin").hasRole("ADMIN");
            auth.requestMatchers("/user").hasRole("USER");
            auth.requestMatchers("/products").hasRole("ADMIN");
            auth.anyRequest().authenticated();
                }
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(ses->ses
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ALWAYS, IF_REQUIRED, NEVER
                        //.maximumSessions(1).expiredUrl("/login?expired").maxSessionsPreventsLogin(true)
                .oauth2ResourceServer((oauth2) -> oauth2
                .jwt(Customizer.withDefaults())
        )

        //.httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }
    /*@Bean
    public UserDetailsService users(){
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER").build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER","ADMIN").build();
        return new InMemoryUserDetailsManager(user,admin);
    }*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
    //This approach is often used when you need to access shared objects, such as AuthenticationManagerBuilder,
    // during the configuration of Spring Security. The shared objects provide a way to share information or components
    // across different parts of the security configuration. In this specific context, it's likely that some
    // configuration elsewhere in your application or security setup has already contributed to the AuthenticationManagerBuilder,
    // and this line retrieves it for further customization or extension within the current configuration.

//------------------------------------------------------------------------------------------------------------------------------------------
    //Commands using OpenSSL generate a key pair (public and private keys) for JWT using RSA algorithm encryption.
    // 1-- openssl genrsa -out keypair.pem 2048
    //Generates an RSA private key with a bit length of 2048 and saves it to a file named keypair.pem.

    // 2-- openssl rsa -in keypair.pem -pubout -out public.pem
    // Takes the private key (keypair.pem) and generates the corresponding public key, saving it to a file named public.pem

    //openssl pkcs8 -topk8 -inform PEM -nocrypt -in keypair.pem  -out private.pem
    // Converts the private key (keypair.pem) to PKCS#8 format, which is a standard syntax for representing private key information.
    // The resulting private key is then saved to a file named private.pem.
}
