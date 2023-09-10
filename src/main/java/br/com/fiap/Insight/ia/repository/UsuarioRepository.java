package br.com.fiap.Insight.ia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Insight.ia.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{ 

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}