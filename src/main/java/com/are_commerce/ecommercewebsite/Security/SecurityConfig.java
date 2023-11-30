package com.are_commerce.ecommercewebsite.Security;

import com.are_commerce.ecommercewebsite.Controller.CustomLoginSuccessHandler;
import com.are_commerce.ecommercewebsite.Service.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    //private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    public SecurityConfig(JwtUserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("Creating SecurityFilterChain bean: Execution started.");
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                // don't authenticate this particular request
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/signup","/authenticate", "/hello").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/login").hasRole("ADMIN")

                        .requestMatchers("/home").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/dashboard").hasRole("ADMIN")
                        //.requestMatchers("/authenticate").permitAll()
                        // all other requests need to be authenticated
                        .anyRequest().authenticated())
                // make sure we use stateless session; session won't be used to
                // store user's state.
                //.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                //.formLogin(form -> form.successHandler(customLoginSuccessHandler).permitAll())
                        //.defaultSuccessUrl()
                //.formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add a filter to validate the tokens with every request
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    // Configures the security filter chain, defining how requests should be handled based on their URLs
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LOGGER.info("Entered myMethod");
        http.formLogin(Customizer.withDefaults());
        http.exceptionHandling(ex->
                ex.accessDeniedPage("/AccessDeniedPage"));
        http.authorizeHttpRequests(auth ->{

            auth.requestMatchers("/admin/**").hasRole("ADMIN");
            auth.requestMatchers("/user/**").hasAnyRole("ADMIN","USER");
            auth.requestMatchers("/login").permitAll();

            //auth.requestMatchers("/products").hasRole("ADMIN");
            auth.anyRequest().authenticated();
                }
                );

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(ses->ses
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // ALWAYS, IF_REQUIRED, NEVER
                //.maximumSessions(1).expiredUrl("/login?expired").maxSessionsPreventsLogin(true)
        http.oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                );
        LOGGER.info("Exiting myMethod");

        //.httpBasic(Customizer.withDefaults())
                //.build();
        return http.build();
    }*/
    /*@Bean
    public UserDetailsService users(){
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user")
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
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    /*@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
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
