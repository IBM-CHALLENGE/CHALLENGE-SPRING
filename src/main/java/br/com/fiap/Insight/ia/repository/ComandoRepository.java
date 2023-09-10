package br.com.fiap.Insight.ia.repository;

import br.com.fiap.Insight.ia.models.Comando;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComandoRepository extends JpaRepository<Comando, Integer>{
   
    List<Comando> findByAnuncioIdOrderById(Integer idAnuncio);
}
