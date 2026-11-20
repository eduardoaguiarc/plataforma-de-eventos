package com.plataforma.eventos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.plataforma.eventos.service.CategoriaDoEventoService;

@RestController
@RequestMapping("/api")
public class CategoriaDoEventoController {

    private final CategoriaDoEventoService categoriaDoEventoService;

    CategoriaDoEventoController(CategoriaDoEventoService categoriaDoEventoService) {
        this.categoriaDoEventoService = categoriaDoEventoService;
    }

}
