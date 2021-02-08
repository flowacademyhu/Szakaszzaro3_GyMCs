package com.vizsga.gymcs.service;

import com.vizsga.gymcs.entity.Superhero;
import com.vizsga.gymcs.exception.ValidationException;
import com.vizsga.gymcs.repository.SuperheroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class SuperheroService {

    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final SuperheroRepository superheroRepository;

    @Autowired
    public SuperheroService(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    public Superhero createSuperhero(Superhero superhero) throws ValidationException {
        log.info("Creating adding superhero: {}", superhero);
        //Itt kellene a további megszorításokat hozzáadni a Superhero létrehozásakor
        if (!(StringUtils.hasText(superhero.getName()))) {
            throw new ValidationException("Superhero has noName", HttpStatus.BAD_REQUEST);
        } else {
            Superhero created = superheroRepository.save(superhero);
            log.info("Created Superhero: {}", superhero);
            return created;
        }

    }

    public List<Superhero> getSuperheroes() {
        log.info("Superheroes from db...");
        List<Superhero> superheroList = superheroRepository.findAll();
        return superheroList;
    }

    public Optional<Superhero> getSuperheroesById(String id) {
        log.info("Superhero id: {}", id);
        Optional<Superhero> superheroById = superheroRepository.findById(id);
        return superheroById;
    }

    public Superhero updateSuperhero(String id, Superhero superhero) throws ValidationException {
        log.info("Existing id: {}", id);

        Optional<Superhero> idNumber = superheroRepository.findById(id);
        if (idNumber.isEmpty()) {
            throw new ValidationException("This id is: " + id + " not found.");
        }

        if (!(StringUtils.hasText(superhero.getName()))) {
            throw new ValidationException("Price is negative or no Food Name", HttpStatus.BAD_REQUEST);
        } else {
            Superhero actualSuperhero = idNumber.get();
            log.debug("Original Superhero was: {}", idNumber.get());
            actualSuperhero.setName(superhero.getName());
            actualSuperhero.setTeam(superhero.getTeam());
            actualSuperhero.setUniverse(superhero.getUniverse());

            return superheroRepository.save(actualSuperhero);

        }
    }
}
