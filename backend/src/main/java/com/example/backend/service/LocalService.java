package com.example.backend.service;

import com.example.backend.model.dto.Local.ObterNomeLocalDTO;
import com.example.backend.model.entity.Local;

import java.util.List;

public interface LocalService{

    void adicionar(String nome);

    ObterNomeLocalDTO buscarProId(Long id);

    List<Local> listar();
}
