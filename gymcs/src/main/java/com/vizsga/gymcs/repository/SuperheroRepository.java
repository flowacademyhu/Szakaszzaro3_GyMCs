package com.vizsga.gymcs.repository;

import com.vizsga.gymcs.entity.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, String> {
}
