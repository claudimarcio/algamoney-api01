package com.example.algamoney.api01.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;



@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	 private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	@Value("${jwt.public.key}")
	public RSAPublicKey key;

	@Value("${jwt.private.key}")
	private RSAPrivateKey priv;
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("Configuração de segurança sendo aplicada.");
				http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/authenticate").permitAll()
				.requestMatchers("/auth/refresh-token").permitAll()
				.anyRequest().authenticated()
				).cors(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults()).oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()))
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
				
		return http.build();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		logger.info("Criando JwtDecoder.");
		return NimbusJwtDecoder.withPublicKey(key).build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		 logger.info("Criando JwtEncoder.");
		var jwk = new RSAKey.Builder(key).privateKey(priv).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		 logger.info("Criando PasswordEncoder.");
		return new BCryptPasswordEncoder();

	}

	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8000"));
		configuration.setAllowedMethods(Arrays.asList("POST, GET, DELETE, PUT, OPTIONS"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	

}

/**
 * 
 * @Bean AuthenticationManager authenticationManager( UserDetailsService
 *       userDetailsService, PasswordEncoder passwordEncoder) {
 *       DaoAuthenticationProvider authenticationProvider = new
 *       DaoAuthenticationProvider();
 *       authenticationProvider.setUserDetailsService(userDetailsService);
 *       authenticationProvider.setPasswordEncoder(passwordEncoder);
 * 
 *       return new ProviderManager(authenticationProvider); }
 * 
 * 
 * @Bean public UserDetailsService userDetailsService()
 *       { @SuppressWarnings("deprecation") UserDetails userDetails =
 *       User.withDefaultPasswordEncoder() .username("user") .password("user")
 *       .roles("USER") .build();
 * 
 *       return new InMemoryUserDetailsManager(userDetails); }
 * 
 * @Bean PasswordEncoder passwordEncoder() { return
 *       PasswordEncoderFactories.createDelegatingPasswordEncoder(); }
 * 
 **/
