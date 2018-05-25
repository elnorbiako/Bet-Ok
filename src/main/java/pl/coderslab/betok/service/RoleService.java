package pl.coderslab.betok.service;


import pl.coderslab.betok.entity.Role;

public interface RoleService {

    Role getOrCreate(String roleName);
}
