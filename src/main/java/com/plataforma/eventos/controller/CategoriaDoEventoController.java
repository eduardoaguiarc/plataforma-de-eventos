package com.plataforma.eventos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.eventos.config.AppConstantes;
import com.plataforma.eventos.dtos.CategoriaDoEventoDTO;
import com.plataforma.eventos.dtos.CategoriaDoEventoResponseDTO;
import com.plataforma.eventos.service.CategoriaDoEventoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class CategoriaDoEventoController {

    private final CategoriaDoEventoService categoriaDoEventoService;

    CategoriaDoEventoController(CategoriaDoEventoService categoriaDoEventoService) {
        this.categoriaDoEventoService = categoriaDoEventoService;
    }

    @GetMapping("/public/categorias")
    public ResponseEntity<CategoriaDoEventoResponseDTO> getAllCategorias(
            @RequestParam(name = "pageNumber", defaultValue = AppConstantes.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstantes.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstantes.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstantes.SORT_DIR, required = false) String sortOrder) {

        CategoriaDoEventoResponseDTO categoriaDoEventoResponseDTO = categoriaDoEventoService
                .getAllCategorias(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoriaDoEventoResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/public/categorias")
    public ResponseEntity<CategoriaDoEventoDTO> createCategoria(
            @Valid @RequestBody CategoriaDoEventoDTO categoriaDoEventoDTO) {
        CategoriaDoEventoDTO salvaCategoria = categoriaDoEventoService
                .createCategoria(categoriaDoEventoDTO);
        return new ResponseEntity<>(salvaCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/admin/categorias/{categoriaId}")
    public ResponseEntity<CategoriaDoEventoDTO> updateCategoria(
            @Valid @RequestBody CategoriaDoEventoDTO categoriaDoEventoDTO, @PathVariable Long categoriaId) {

        CategoriaDoEventoDTO salvaCategoria = categoriaDoEventoService.updateCategoria(categoriaId,
                categoriaDoEventoDTO);
        return new ResponseEntity<>(salvaCategoria, HttpStatus.OK);
    }

    @DeleteMapping("/admin/categorias/{categoriaId}")
    public ResponseEntity<CategoriaDoEventoDTO> deleteCategoria(@PathVariable Long categoriaId) {
        CategoriaDoEventoDTO status = categoriaDoEventoService.deleteCategoria(categoriaId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
