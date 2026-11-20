package com.plataforma.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.plataforma.eventos.dtos.CategoriaDoEventoDTO;
import com.plataforma.eventos.dtos.CategoriaDoEventoResponseDTO;
import com.plataforma.eventos.exception.APIException;
import com.plataforma.eventos.exception.ResourceNotFoundException;
import com.plataforma.eventos.model.CategoriaDoEvento;
import com.plataforma.eventos.repositories.CategoriaDoEventoRepository;

@Service
public class CategoriaDoEventoService {

    @Autowired
    private CategoriaDoEventoRepository categoriaDoEventoRepository;

    public CategoriaDoEventoResponseDTO getAllCategorias(Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        Sort sortByAndOrder = "asc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<CategoriaDoEvento> categoryPage = categoriaDoEventoRepository.findAll(pageDetails);

        List<CategoriaDoEvento> categorias = categoryPage.getContent();
        if (categorias.isEmpty()) {
            throw new APIException("No category created yet!");
        }

        List<CategoriaDoEventoDTO> categoriaDoEventoDTOs = categorias.stream()
                .map(categoria -> new CategoriaDoEventoDTO(categoria.getId(), categoria.getNome()))
                .toList();

        CategoriaDoEventoResponseDTO categoryResponse = new CategoriaDoEventoResponseDTO();
        categoryResponse.setContent(categoriaDoEventoDTOs);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;

    }

    public CategoriaDoEventoDTO createCategoria(CategoriaDoEventoDTO categoriaDoEventoDTO) {
        if (categoriaDoEventoRepository.existsByNomeIgnoreCase(categoriaDoEventoDTO.getCategoriaNome())) {
            throw new APIException("Categoria já existe");
        }
        CategoriaDoEvento novaCategoriaDoEvento = new CategoriaDoEvento();
        novaCategoriaDoEvento.setNome(categoriaDoEventoDTO.getCategoriaNome());
        novaCategoriaDoEvento = categoriaDoEventoRepository.save(novaCategoriaDoEvento);
        return new CategoriaDoEventoDTO(novaCategoriaDoEvento.getId(), novaCategoriaDoEvento.getNome());
    }

    public CategoriaDoEventoDTO updateCategoria(Long categoriaId, CategoriaDoEventoDTO categoriaDoEventoDTO) {
        CategoriaDoEvento existeCategoriaDoEvento = categoriaDoEventoRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada " + categoriaId));

        if (!existeCategoriaDoEvento.getNome().equalsIgnoreCase(categoriaDoEventoDTO.getCategoriaNome())
                && categoriaDoEventoRepository.existsByNomeIgnoreCase(categoriaDoEventoDTO.getCategoriaNome())) {
            throw new APIException("Já existe uma categoria com esse nome!");
        }

        existeCategoriaDoEvento.setNome(categoriaDoEventoDTO.getCategoriaNome());
        existeCategoriaDoEvento = categoriaDoEventoRepository.save(existeCategoriaDoEvento);
        return new CategoriaDoEventoDTO(existeCategoriaDoEvento.getId(), existeCategoriaDoEvento.getNome());
    }

    public CategoriaDoEventoDTO deleteCategoria(Long categoriaId) {
        CategoriaDoEvento categoriaDoEvento = categoriaDoEventoRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("categoria não encontrada" + categoriaId));
        categoriaDoEventoRepository.delete(categoriaDoEvento);
        return new CategoriaDoEventoDTO(categoriaDoEvento.getId(), categoriaDoEvento.getNome());
    }
}
