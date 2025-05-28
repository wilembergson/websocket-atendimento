package com.example.backend.model.dto.Atendimento;

public record AtendimentoItemListaDTO(
        Long id,
        String status,
        String data,
        Long idFicha,
        String tipoFicha,
        String identificacaoFicha,
        Long idLocal,
        String nomeLocal
) {
}
