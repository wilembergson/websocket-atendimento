package com.example.backend.repository;

import com.example.backend.model.entity.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Long> {

}
