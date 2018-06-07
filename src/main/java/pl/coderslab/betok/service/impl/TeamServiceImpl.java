package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Team;
import pl.coderslab.betok.repository.TeamRepository;
import pl.coderslab.betok.service.TeamService;

/**
 * Service responsible for handling Teams {@link Team}
 */
@Service
public class TeamServiceImpl implements TeamService {

    final private TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }
}
