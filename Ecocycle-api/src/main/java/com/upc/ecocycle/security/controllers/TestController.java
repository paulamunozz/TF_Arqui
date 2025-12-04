package com.upc.ecocycle.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/prueba")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "This is the admin endpoint, accessible only to users with ADMIN role.";
    }

    @GetMapping("/vecino")
    @PreAuthorize("hasRole('VECINO')")
    public String vecinoEndpoint() {
        return "This is the vecino endpoint, accessible only to users with VECINO role.";
    }

    @GetMapping("/municipalidad")
    @PreAuthorize("hasRole('MUNICIPALIDAD')")
    public String municipalidadEndpoint() {
        return "This is the municipalidad endpoint, accessible only to users with MUNICIAPLIDAD role.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'MUNICIPALIDAD', 'VECINO')")
    public String userEndpoint() {
        return "This is the user endpoint, accessible to users with ADMIN, VECINO or MUNICIPALIDAD role.";
    }
}
