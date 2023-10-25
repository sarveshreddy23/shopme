package com.shopme.admin.config;

import com.shopme.admin.auth.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
            repository.setDataSource(dataSource);
            return repository;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(userDetailsService());
        return auth;
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new ShopmeAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.cors(corsConfigurer -> corsConfigurer.disable())
               .csrf(configurer -> configurer.disable())
               .authorizeHttpRequests(auth-> {
                   auth.requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll()
                           .requestMatchers("/webjars/**","/fontAwesome/**", "/webfonts/**", "/images/**", "/getemail", "css/**").permitAll()
                   .anyRequest().authenticated();
               }).authenticationProvider(daoAuthenticationProvider())
                 .formLogin(formLoginConfigurer->{
                   formLoginConfigurer.loginPage("/login")
                           .successHandler(authenticationSuccessHandler())
                           .usernameParameter("email");
               })
               .rememberMe(rem->{
                   rem.tokenRepository(tokenRepository());
               })
               .build();
    }

}
