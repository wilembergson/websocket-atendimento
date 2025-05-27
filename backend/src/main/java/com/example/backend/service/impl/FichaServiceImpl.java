package com.example.backend.service.impl;

import com.example.backend.model.entity.Atendimento;
import com.example.backend.model.entity.Ficha;
import com.example.backend.repository.AtendimentoRepository;
import com.example.backend.repository.FichaRepository;
import com.example.backend.service.FichaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FichaServiceImpl implements FichaService {

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Override
    public Ficha emitir(String tipo) {
        Integer numero = fichaRepository.findAll().size();
        Ficha ficha = new Ficha();
        ficha.setNumero(numero+1);
        ficha.setTipo(tipo);
        Ficha fichaCriada = fichaRepository.save(ficha);

        Atendimento atendimento = new Atendimento();
        atendimento.setStatus("AGUARDANDO");
        atendimento.setData(LocalDateTime.now());
        atendimento.setFicha(fichaCriada);
        atendimento.setSinalSonoro(0);
        atendimentoRepository.save(atendimento);
        return fichaCriada;
    }

    @Override
    public List<Ficha> listar(String status) {
        return fichaRepository.findAll()
                .stream()
                .filter(item -> item.getTipo().equals(status))
                .toList();
    }
}
