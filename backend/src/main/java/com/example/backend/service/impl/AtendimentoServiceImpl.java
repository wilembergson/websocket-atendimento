package com.example.backend.service.impl;

import com.example.backend.model.dto.Atendimento.AtendimentoItemListaDTO;
import com.example.backend.model.dto.Atendimento.ExibirPainelDTO;
import com.example.backend.model.dto.Atendimento.ExibirPainelItemDTO;
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
    public ExibirPainelDTO listarFichasChamadas(Long idLocal) {
        List<Atendimento> lista = atendimentoRepository.findBySinalSonoroOrderByDataDesc(idLocal, 1);
        Atendimento primeiraFicha = lista.get(0);
        List<Atendimento> ultimasChamadas = lista.subList(1, Math.min(6, lista.size()));
        ExibirPainelItemDTO primeiraFichaDTO = converterAtendimentoParaPainel(primeiraFicha);
        List<ExibirPainelItemDTO> ultimasChamadasDTO = listarAtendimentosParaPainel(ultimasChamadas);
        return new ExibirPainelDTO(primeiraFichaDTO, ultimasChamadasDTO);
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
                atendimento.getLocal().getId(),
                atendimento.getLocal().getNome()
        );
    }

    private List<ExibirPainelItemDTO> listarAtendimentosParaPainel(List<Atendimento> lista){
        return lista.stream()
                .map(item -> converterAtendimentoParaPainel(item))
                .collect(Collectors.toUnmodifiableList());
    }

    private ExibirPainelItemDTO converterAtendimentoParaPainel(Atendimento atendimento){
        return new ExibirPainelItemDTO(
                atendimento.getId(),
                atendimento.getFicha().getIdentificacao(),
                atendimento.getFicha().getTipo(),
                atendimento.getData()
        );
    }
}
