package br.com.fiap.Insight.ia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Insight.ia.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer>{ 

    List<Transacao> findByUsuarioId(Integer id);

}