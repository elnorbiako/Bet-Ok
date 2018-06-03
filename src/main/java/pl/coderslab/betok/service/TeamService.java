package pl.coderslab.betok.service;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Team;


public interface TeamService {

    Team findByName(String name);
}
