package com.example.backend.controller;

import com.example.backend.model.dto.Ficha.NovaFichaDTO;
import com.example.backend.model.entity.Ficha;
import com.example.backend.service.FichaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ficha")
public class FichaController {

    @Autowired
    private FichaService fichaService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static String webSocketDestino = "/topic/alterar-status";

    @PostMapping("/emitir")
    public ResponseEntity emitir(@RequestBody NovaFichaDTO dto){
        Ficha novaFicha = fichaService.emitir(dto);
        messagingTemplate.convertAndSend(webSocketDestino, novaFicha);
        return ResponseEntity.ok("Ficha gerada");
    }

    @GetMapping("/listar/{status}")
    public ResponseEntity listar(@PathVariable String status){
        List<Ficha> lista = fichaService.listar(status);
        return ResponseEntity.ok(lista);
    }
}
