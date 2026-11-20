package com.plataforma.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plataforma.eventos.model.CategoriaDoEvento;

public interface CategoriaDoEventoRepository extends JpaRepository<CategoriaDoEvento, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    CategoriaDoEvento findByNome(String nome);

}
