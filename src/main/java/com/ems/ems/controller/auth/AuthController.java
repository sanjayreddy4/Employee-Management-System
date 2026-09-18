package com.ems.ems.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class AuthController {

    // Login Page (Common for Admin + Employee)
    @GetMapping("/login")
    public String loginPage() {
        return "login";   // templates/login.html
    }

    // Optional: After login redirect automatically based on role
    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (roles.contains("ROLE_EMPLOYEE")) {
            return "redirect:/employee/dashboard";
        }
        return "redirect:/login?error";
    }

    // Optional logout (if you want controller based logout)
    @GetMapping("/logout-custom")
    public String logout(Authentication auth, HttpServletRequest request) {

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
        return "redirect:/login?logout";
    }
}
