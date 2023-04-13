package io.github.srcimon.issue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(CustomAuthenticationProvider authProvider) {
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(final HttpSecurity http, UserChangeFilter userChangeFilter)
            throws Exception {
        return http
                .httpBasic()
                .and()
                .addFilterBefore(userChangeFilter, AbstractPreAuthenticatedProcessingFilter.class)

                // not setting sessionAuthenticationStrategy -> test case twoUsers_multipleRequests_doesntReuseSession fails
                // setting sessionAuthenticationStrategy -> test case oneUser_multipleRequests_reusesSession fails
                .sessionManagement().sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy()).and()

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/**").denyAll())
                .build();
    }
}