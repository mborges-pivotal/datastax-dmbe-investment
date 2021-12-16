package com.datastax.dmbe.astra.investment.frontend.admin;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {
    ///////////////////////////
    // Security Configuration
    ///////////////////////////

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication().withUser("user@datastax.com").password(encoder.encode("user")).roles("USER").and()
                .withUser("admin@datastax.com").password(encoder.encode("admin")).roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll()
                .antMatchers("/", "/index.html", "/redirectPage").permitAll()
                .antMatchers("/webjars/**", "/css/**", "/js/**", "/images/**").permitAll().antMatchers("/api/**")
                .permitAll() // review
                             // API
                             // security
                             // and
                             // ajax
                             // calls.
                             // e.g.
                             // appRank
                .antMatchers("/stock", "/docs/**")
                .permitAll().antMatchers("/workspaces/foundation/*").permitAll()
                .antMatchers("/management/status", "/docs/*")
                .permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login.html")
                .failureUrl("/login-error.html").permitAll().and().logout().invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
