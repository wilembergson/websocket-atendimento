package com.example.backend.service;

import com.example.backend.model.entity.Guiche;

import java.util.List;

public interface GuicheService {

    void criar(Integer numero);

    List<Guiche> listar();
}
