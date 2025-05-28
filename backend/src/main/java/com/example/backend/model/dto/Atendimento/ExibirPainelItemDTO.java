package com.example.backend.model.dto.Atendimento;

public record ExibirPainelItemDTO(
        Long id,
        String identificacao,
        String tipo,
        String data
) {
}
