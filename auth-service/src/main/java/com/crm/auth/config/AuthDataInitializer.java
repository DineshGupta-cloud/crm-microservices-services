package com.crm.auth.config;

import com.crm.auth.entity.Permission;
import com.crm.auth.entity.Role;
import com.crm.auth.repository.PermissionRepository;
import com.crm.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthDataInitializer {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Bean
    CommandLineRunner seedAuthData() {
        return args -> {
            List<String> permissionNames = List.of(
                    "COMPANY_READ", "COMPANY_WRITE",
                    "EMPLOYEE_READ", "EMPLOYEE_WRITE",
                    "CUSTOMER_READ", "CUSTOMER_WRITE",
                    "LEAD_READ", "LEAD_WRITE",
                    "USER_READ", "USER_WRITE"
            );

            for (String name : permissionNames) {
                if (permissionRepository.findByName(name).isEmpty()) {
                    Permission permission = new Permission();
                    permission.setName(name);
                    permission.setDescription(name.replace('_', ' '));
                    permissionRepository.save(permission);
                }
            }

            ensureRole("USER");
            Role admin = ensureRole("ADMIN");
            admin.getPermissions().addAll(permissionRepository.findAll());
            roleRepository.save(admin);
        };
    }

    private Role ensureRole(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        });
    }
}
