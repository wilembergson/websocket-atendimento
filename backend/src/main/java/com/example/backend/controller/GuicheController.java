package com.example.backend.controller;

import com.example.backend.model.dto.Guiche.NovoGuicheDTO;
import com.example.backend.model.entity.Guiche;
import com.example.backend.service.GuicheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guiche")
public class GuicheController {

    @Autowired
    private GuicheService guicheService;

    @PostMapping("/novo-guiche")
    public ResponseEntity cricar(@RequestBody NovoGuicheDTO dto){
        guicheService.criar(dto.numero());
        return ResponseEntity.ok("Guichê criado");
    }

    @GetMapping("/listar")
    public ResponseEntity listar(){
        List<Guiche> lista = guicheService.listar();
        return ResponseEntity.ok(lista);
    }
}
