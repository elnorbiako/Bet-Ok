package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Team;
import pl.coderslab.betok.repository.TeamRepository;
import pl.coderslab.betok.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }
}
