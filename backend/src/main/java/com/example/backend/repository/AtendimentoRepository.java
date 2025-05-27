package com.example.backend.repository;

import com.example.backend.model.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByStatusOrderByDataDesc(String status);

    List<Atendimento> findByStatusOrderByData(String status);

    List<Atendimento> findBySinalSonoroOrderByDataDesc(Integer sinalSonoro);

    Optional<Atendimento> findByFichaId(Long idFicha);
}
