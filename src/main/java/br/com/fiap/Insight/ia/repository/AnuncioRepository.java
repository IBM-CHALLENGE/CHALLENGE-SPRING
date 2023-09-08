package br.com.fiap.Insight.ia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Insight.ia.models.Anuncio;

public interface AnuncioRepository extends JpaRepository<Anuncio, Integer>{ 

    List<Anuncio> findByUsuarioIdOrderByIdDesc(Integer idUsuario);
}