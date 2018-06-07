package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Role;
import pl.coderslab.betok.repository.RoleRepository;
import pl.coderslab.betok.service.RoleService;

/**
 * Service that handles Roles {@link Role}. Its used in a overwriting Spring Security basic roles.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Supprot method for getting a role from DB. If that role isn't found - will be created.
     * @param name rolename - only defined roles should be used, only exception is to create a new role on purpose.
     * @return rolename from DB
     */
    @Override
    public Role getOrCreate(String name) {

        Role role = roleRepository.findByName(name);

        if (role == null) {

            role = new Role();
            role.setName(name);

            role = roleRepository.save(role);
        }

        return role;
    }
}
