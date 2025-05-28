package com.example.backend.service;

import com.example.backend.model.entity.Local;

public interface LocalService{

    void adicionar(String nome);

    Local buscarProId(Long id);
}
