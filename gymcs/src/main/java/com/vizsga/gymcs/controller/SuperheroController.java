package com.vizsga.gymcs.controller;


import com.vizsga.gymcs.entity.Superhero;
import com.vizsga.gymcs.exception.ValidationException;
import com.vizsga.gymcs.service.SuperheroService;
import com.vizsga.gymcs.service.TeamService;
import net.bytebuddy.implementation.bind.annotation.Super;
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
public class SuperheroController {

    private static final Logger log = LoggerFactory.getLogger(SuperheroController.class);

    private SuperheroService superheroService;

    @Autowired
    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @PostMapping("/super-hero/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Superhero createSuperhero(@RequestBody Superhero superhero) throws ValidationException {
        log.info("Creating Superhero: {}", superhero);
        try {
            Superhero result = superheroService.createSuperhero(superhero);
            log.debug("Result is: {}", result);
            return result;
        } catch (ValidationException ve) {
            log.error("Error when creating Superhero: " + ve.getMessage());
            throw new ResponseStatusException(ve.getHttpStatus(), ve.getMessage());
        }

    }

    @GetMapping("/super-hero/getsuperheroes")
    public List<Superhero> findAllSuperheroes() {
        log.info("Receiving get superheroes");
        return superheroService.getSuperheroes();
    }

    @GetMapping("/super-hero/getsuperheroesbyid/{id}")
    public Superhero findSuperHeroById(@PathVariable(name = "id") String id) {
        log.info("Receiving superhero id: {}", id);

        Optional<Superhero> superhero = superheroService.getSuperheroesById(id);

        if (superhero.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No superhero this id");
        }
        return superhero.get();
    }

    @PutMapping("/super-hero/updatesuperhero/{id}")
    public Superhero updateSuperhero(@PathVariable("id") String id, @RequestBody Superhero superhero) {
        try {
            Superhero updatedSuperhero = superheroService.updateSuperhero(id, superhero);
            return updatedSuperhero;
        } catch (ValidationException ve) {
            throw new ResponseStatusException(ve.getHttpStatus(), "Registration with given id not found");
        }
    }
}

