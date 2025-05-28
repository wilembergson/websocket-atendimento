package com.example.backend.service;

import com.example.backend.model.dto.Ficha.NovaFichaDTO;
import com.example.backend.model.entity.Ficha;

import java.util.List;

public interface FichaService {

    Ficha emitir(NovaFichaDTO dto);

    List<Ficha> listar(String status);
}
