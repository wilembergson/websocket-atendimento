package com.example.backend.model.dto.Atendimento;

import com.example.backend.model.entity.Atendimento;

import java.util.List;

public record ExibirPainelDTO(Atendimento chamada, List<Atendimento> ultimasChamadas) {
}
