package com.example.backend.repository;

import com.example.backend.model.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    @Query(value = "SELECT * FROM atendimento WHERE status = :status AND local_id = :idLocal ORDER BY data DESC", nativeQuery = true)
    List<Atendimento> listarPorStatusELocalDesc(@Param("status") String status, @Param("idLocal") Long idLocal);

    @Query(value = "SELECT * FROM atendimento WHERE status = :status AND local_id = :idLocal", nativeQuery = true)
    List<Atendimento> listarPorStatusELocal(@Param("status") String status, @Param("idLocal") Long idLocal);

    @Query(value = "SELECT * FROM atendimento WHERE local_id = :idLocal AND sinal_sonoro = :sinalSonoro", nativeQuery = true)
    List<Atendimento> findBySinalSonoroOrderByDataDesc(@Param("idLocal") Long idLocal, @Param("sinalSonoro") Integer sinalSonoro);

    Optional<Atendimento> findByFichaId(Long idFicha);
}
