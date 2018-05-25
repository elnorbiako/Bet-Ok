package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
