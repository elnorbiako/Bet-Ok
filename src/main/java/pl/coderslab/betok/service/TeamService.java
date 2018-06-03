package pl.coderslab.betok.service;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Team;

@Service
public interface TeamService {

    Team findByName(String name);
}
