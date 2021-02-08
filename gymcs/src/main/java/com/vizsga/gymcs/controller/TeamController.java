package com.vizsga.gymcs.controller;

import com.vizsga.gymcs.entity.Superhero;
import com.vizsga.gymcs.entity.Team;
import com.vizsga.gymcs.exception.ValidationException;
import com.vizsga.gymcs.service.SuperheroService;
import com.vizsga.gymcs.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class TeamController {

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @PostMapping("/teams/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Team createTeam(@RequestBody Team team) throws ValidationException {
        log.info("Creating Team: {}", team);
        try {
            Team result = teamService.createTeam(team);
            log.debug("Result is: {}", result);
            return result;
        } catch (ValidationException ve) {
            log.error("Error when creating Team: " + ve.getMessage());
            throw new ResponseStatusException(ve.getHttpStatus(), ve.getMessage());
        }
    }

    @GetMapping("/teams/getteams")
    public List<Team> findAllTeams() {
        log.info("Receiving get teams");
        return teamService.getTeams();
    }

    @GetMapping("/teams/getteamsbyid/{id}")
    public Team findTeamById(@PathVariable(name = "id") String id) {
        log.info("Receiving team id: {}", id);

        Optional<Team> team = teamService.getTeamsById(id);

        if (team.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No team this id");
        }
        return team.get();
    }

    @PutMapping("/teams/updateteam/{id}")
    public Team updateTeam(@PathVariable("id") String id, @RequestBody Team team) {
        try {
            Team updatedTeam = teamService.updateTeam(id, team);
            return updatedTeam;
        } catch (ValidationException ve) {
            throw new ResponseStatusException(ve.getHttpStatus(), "Registration with given id not found");
        }
    }

}
