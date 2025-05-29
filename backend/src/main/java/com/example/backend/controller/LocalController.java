package com.example.backend.controller;

import com.example.backend.model.dto.Local.NovoLocalDTO;
import com.example.backend.model.dto.Local.ObterNomeLocalDTO;
import com.example.backend.model.entity.Local;
import com.example.backend.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/local")
public class LocalController {

    @Autowired
    private LocalService localService;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestBody NovoLocalDTO dto){
        localService.adicionar(dto.nome());
        return ResponseEntity.ok(Map.of("mensagem", "Local criado"));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarLocal(@PathVariable Long id){
        ObterNomeLocalDTO nomeLocalDTO= localService.buscarProId(id);
        return ResponseEntity.ok(nomeLocalDTO);
    }

    @GetMapping("/listar")
    public ResponseEntity listar(){
        List<Local> locais = localService.listar();
        return ResponseEntity.ok(locais);
    }
}
