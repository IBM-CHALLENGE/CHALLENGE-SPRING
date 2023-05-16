package br.com.fiap.Insight.ia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Insight.ia.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer>{ 

}