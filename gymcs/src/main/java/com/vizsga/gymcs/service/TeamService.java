package com.vizsga.gymcs.service;

import com.vizsga.gymcs.entity.Superhero;
import com.vizsga.gymcs.entity.Team;
import com.vizsga.gymcs.exception.ValidationException;
import com.vizsga.gymcs.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team team) throws ValidationException {
        log.info("Creating adding team: {}", team);
        //Itt kellene a további megszorításokat hozzáadni a Team létrehozásakor
        if (!(StringUtils.hasText(team.getName()))) {
            throw new ValidationException("Team has noName", HttpStatus.BAD_REQUEST);
        } else {
            Team created = teamRepository.save(team);
            log.info("Created team: {}", team);
            return created;
        }

    }

    public List<Team> getTeams() {
        log.info("Teams from db...");
        List<Team> teamList = teamRepository.findAll();
        return teamList;
    }

    public Optional<Team> getTeamsById(String id) {
        log.info("Team id: {}", id);
        Optional<Team> teamById = teamRepository.findById(id);
        return teamById;
    }

    public Team updateTeam(String id, Team team) throws ValidationException {
        log.info("Existing id: {}", id);

        Optional<Team> idNumber = teamRepository.findById(id);
        if (idNumber.isEmpty()) {
            throw new ValidationException("This id is: " + id + " not found.");
        }

        if (!(StringUtils.hasText(team.getName()))) {
            throw new ValidationException("Team has noName", HttpStatus.BAD_REQUEST);
        } else {
            Team actualTeam = idNumber.get();
            log.debug("Original Team was: {}", idNumber.get());
            actualTeam.setKind(team.getKind());
            actualTeam.setName(team.getName());
            actualTeam.setUniverse(team.getUniverse());

            return teamRepository.save(actualTeam);
        }


    }
}
