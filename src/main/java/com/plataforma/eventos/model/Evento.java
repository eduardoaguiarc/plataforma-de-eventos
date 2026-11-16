package com.plataforma.eventos.model;

import com.plataforma.eventos.enums.TipoEvento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String descricao;

    private String imageUrl;

    private LocalDateTime data;

    //private Localizacao local;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;


    @ManyToOne
    @JoinColumn(name = "categoria_do_evento_id")
    private CategoriaDoEvento categoriaDoEvento;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NivelIngresso> nivelIngressos = new ArrayList<>();

}
