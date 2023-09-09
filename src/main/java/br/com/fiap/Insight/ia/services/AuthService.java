package br.com.fiap.Insight.ia.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.fiap.Insight.ia.exception.RestNotAuthorizedException;
import br.com.fiap.Insight.ia.models.Status;
import br.com.fiap.Insight.ia.models.Usuario;
import br.com.fiap.Insight.ia.repository.UsuarioRepository;

public class AuthService {
    
    public Usuario getUsuarioLogado(UsuarioRepository usuarioRepository){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        return usuarioRepository
            .findByEmail(email)
            .filter(usuario -> usuario.getStatus().equals(Status.ATIVO))
            .orElseThrow(() -> new RestNotAuthorizedException("Usuario não encontrada"));
    }
}
