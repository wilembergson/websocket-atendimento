package com.example.backend.service;

import com.example.backend.model.dto.Atendimento.AtendimentoItemListaDTO;
import com.example.backend.model.dto.Atendimento.ExibirPainelDTO;
import com.example.backend.model.dto.Atendimento.ListarAtendimentosDTO;
import com.example.backend.model.entity.Atendimento;

import java.util.List;

public interface AtendimentoService {

    List<AtendimentoItemListaDTO> listar(String status, Long idLocal);

    ExibirPainelDTO listarFichasChamadas();

    void alterarStatus(Long idFicha, String status);
}
