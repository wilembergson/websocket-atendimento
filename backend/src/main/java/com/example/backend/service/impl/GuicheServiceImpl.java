package com.example.backend.service.impl;

import com.example.backend.model.entity.Guiche;
import com.example.backend.repository.GuicheRepository;
import com.example.backend.service.GuicheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuicheServiceImpl implements GuicheService {

    @Autowired
    public GuicheRepository guicheRepository;

    @Override
    public void criar(Integer numero) {
        Optional<Guiche> guicheOpt = guicheRepository.findByNumero(numero);
        if(guicheOpt.isPresent()) throw new RuntimeException("Já existe guichê com esse número");
        Guiche novoGuiche = new Guiche();
        novoGuiche.setNumero(numero);
        guicheRepository.save(novoGuiche);
    }

    @Override
    public List<Guiche> listar() {
        return guicheRepository.findAll();
    }
}
