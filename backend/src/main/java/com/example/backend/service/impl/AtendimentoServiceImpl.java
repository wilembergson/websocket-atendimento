package com.example.backend.service.impl;

import com.example.backend.model.dto.Atendimento.AtendimentoItemListaDTO;
import com.example.backend.model.dto.Atendimento.ExibirPainelDTO;
import com.example.backend.model.dto.Atendimento.ListarAtendimentosDTO;
import com.example.backend.model.entity.Atendimento;
import com.example.backend.repository.AtendimentoRepository;
import com.example.backend.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Override
    public List<AtendimentoItemListaDTO> listar(String status, Long idLocal) {
        List<Atendimento> atendimentos;
        if(status.equals("AGUARDANDO")){
            atendimentos = atendimentoRepository.listarPorStatusELocal(status, idLocal);
        } else {
            atendimentos = atendimentoRepository.listarPorStatusELocalDesc(status, idLocal);
        }
        return listarAtendimentosDTO(atendimentos);
    }

    @Override
    public ExibirPainelDTO listarFichasChamadas() {
        List<Atendimento> lista = atendimentoRepository.findBySinalSonoroOrderByDataDesc(1);
        Atendimento fichaChamada = lista.get(0);
        List<Atendimento> ultimasChamadas = lista.subList(1, Math.min(6, lista.size()));
        return new ExibirPainelDTO(fichaChamada, ultimasChamadas);
    }

    @Override
    public void alterarStatus(Long idFicha, String status) {
        Optional<Atendimento> atendimentoOpt = atendimentoRepository.findByFichaId(idFicha);
        if(atendimentoOpt.isEmpty()) throw new RuntimeException("ID da ficha não encontrado");
        LocalDateTime dataAlteracao = LocalDateTime.now();
        Atendimento atendimentoAtualizado = atendimentoOpt.get();
        atendimentoAtualizado.setStatus(status);
        if(status != "FINALIZADO"){
            atendimentoAtualizado.setData(dataAlteracao);
        }
        if(atendimentoAtualizado.getSinalSonoro().equals(0) || atendimentoAtualizado.getSinalSonoro() == null){
            atendimentoAtualizado.setSinalSonoro(1);
        }
        atendimentoRepository.save(atendimentoAtualizado);
    }

    private List<AtendimentoItemListaDTO> listarAtendimentosDTO(List<Atendimento> lista){
        return lista.stream()
                .map(item -> converterAtendimento(item))
                .collect(Collectors.toUnmodifiableList());
    }

    private AtendimentoItemListaDTO converterAtendimento(Atendimento atendimento){
        return new AtendimentoItemListaDTO(
                atendimento.getId(),
                atendimento.getStatus(),
                atendimento.getData(),
                atendimento.getFicha().getId(),
                atendimento.getFicha().getTipo(),
                atendimento.getFicha().getIdentificacao(),
                atendimento.getLocal().getNome()
        );
    }
}
