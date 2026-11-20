package com.plataforma.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plataforma.eventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
