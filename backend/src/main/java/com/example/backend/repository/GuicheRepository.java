package com.example.backend.repository;

import com.example.backend.model.entity.Ficha;
import com.example.backend.model.entity.Guiche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuicheRepository extends JpaRepository<Guiche, Long> {

    Optional<Guiche> findByNumero(Integer numero);
}
