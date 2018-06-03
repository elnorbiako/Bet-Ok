package pl.coderslab.betok.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.betok.entity.Team;
import pl.coderslab.betok.repository.TeamRepository;

public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }
}
