package br.com.fiap.Insight.ia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Insight.ia.models.Insight;

public interface InsightRepository extends JpaRepository<Insight, Integer>{ 

    List<Insight> findByAnuncioIdOrderById(Integer idAnuncio);
}