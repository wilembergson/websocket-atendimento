package com.example.backend.controller;

import com.example.backend.model.dto.Atendimento.AlterarStatusDTO;
import com.example.backend.model.dto.Atendimento.AtendimentoItemListaDTO;
import com.example.backend.model.dto.Atendimento.ExibirPainelDTO;
import com.example.backend.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static String webSocketDestino = "/topic/alterar-status";
    private static String webSocketChamarNoPainel = "/topic/exibir-painel-";


    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam String status, @RequestParam Long idLocal){
        List<AtendimentoItemListaDTO> atendimentos = atendimentoService.listar(status, idLocal);
        return ResponseEntity.ok(atendimentos);
    }

    @GetMapping("/listar-painel")
    public ResponseEntity listarPainel(@RequestParam Long idLocal){
        ExibirPainelDTO atendimentos = atendimentoService.listarFichasChamadas(idLocal);
        return ResponseEntity.ok(atendimentos);
    }

    @PostMapping("/alterar/{idFicha}")
    public ResponseEntity alterarStatus(@PathVariable Long idFicha, @RequestBody AlterarStatusDTO dto){
        atendimentoService.alterarStatus(idFicha, dto.status());
        messagingTemplate.convertAndSend(webSocketDestino, Map.of("mensagem", ""));
        if(dto.status().equals("EM_ATENDIMENTO")){
            messagingTemplate.convertAndSend(webSocketChamarNoPainel+dto.idLocal(), Map.of("mensagem", ""));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Status do atendimento alterado"));
    }
}
