package com.datastax.dmbe.astra.investment.frontend.admin;

import java.util.Arrays;

import com.datastax.dmbe.astra.investment.frontend.security.DatabaseUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

    private final DatabaseUserDetailsService databaseUserDetailsService;

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.databaseUserDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/home", "/registration", "/login.html", "/css/**", "/js/**", "/images/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login.html")
                .failureUrl("/login-error.html").permitAll().and().logout().invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        // http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll()
        // .antMatchers("/", "/index.html", "/redirectPage").permitAll()
        // .antMatchers("/webjars/**", "/css/**", "/js/**",
        // "/images/**").permitAll().antMatchers("/api/**")
        // .permitAll() // review
        // // API
        // // security
        // // and
        // // ajax
        // // calls.
        // // e.g.
        // // appRank
        // .antMatchers("/stock", "/docs/**")
        // .permitAll().antMatchers("/workspaces/foundation/*").permitAll()
        // .antMatchers("/management/status", "/docs/*")
        // .permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login.html")
        // .failureUrl("/login-error.html").permitAll().and().logout().invalidateHttpSession(true)
        // .deleteCookies("JSESSIONID");
    }

    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.setAllowedOrigins(Arrays.asList("*"));
    //     configuration.setAllowedMethods(Arrays.asList("*"));
    //     configuration.setAllowedHeaders(Arrays.asList("*"));
    //     configuration.setAllowCredentials(true);
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
    // }

}
