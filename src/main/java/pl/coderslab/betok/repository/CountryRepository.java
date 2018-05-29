package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Country;


public interface CountryRepository extends JpaRepository<Country, Long> {



}
