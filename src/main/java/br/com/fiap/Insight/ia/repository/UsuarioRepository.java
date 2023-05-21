package br.com.fiap.Insight.ia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Insight.ia.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{ 

}