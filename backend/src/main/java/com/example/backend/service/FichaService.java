package com.example.backend.service;

import com.example.backend.model.entity.Ficha;

import java.util.List;

public interface FichaService {

    Ficha emitir(String tipo);

    List<Ficha> listar(String status);
}
