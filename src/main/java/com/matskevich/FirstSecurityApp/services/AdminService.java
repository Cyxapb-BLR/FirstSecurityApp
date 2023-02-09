package com.matskevich.FirstSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SOME_ROLE')")
    //@PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_SOME_ROLE')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
