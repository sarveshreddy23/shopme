package com.shopme.admin.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class ShopmeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("::::::::  onAuthenticationSuccess  :::::::::::::::::");
        System.out.println(authentication.getDetails());
        System.out.println(authentication.toString());
        if(authentication.isAuthenticated()){
            System.out.println("::::::::  isAuthenticationSuccess  :::::::::::::::::");
            authentication.getAuthorities().stream().forEach(System.out::println);
            System.out.println(authentication.getName());
                redirectStrategy.sendRedirect(request, response, "/");

        }
    }
}
