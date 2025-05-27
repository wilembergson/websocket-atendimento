package com.example.backend.service;

import com.example.backend.model.dto.Atendimento.ExibirPainelDTO;
import com.example.backend.model.entity.Atendimento;

import java.util.List;

public interface AtendimentoService {

    List<Atendimento> listar(String status);

    ExibirPainelDTO listarFichasChamadas();

    void alterarStatus(Long idFicha, String status);
}
