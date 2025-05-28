package com.example.backend.service.impl;

import com.example.backend.model.entity.Local;
import com.example.backend.repository.LocalRepository;
import com.example.backend.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalServiceImpl implements LocalService {

    @Autowired
    private LocalRepository localRepository;

    @Override
    public void adicionar(String nome) {
        Local local = new Local();
        local.setNome(nome);
        localRepository.save(local);
    }

    @Override
    public Local buscarProId(Long id) {
        return localRepository.findById(id)
                .orElseThrow();
    }
}
