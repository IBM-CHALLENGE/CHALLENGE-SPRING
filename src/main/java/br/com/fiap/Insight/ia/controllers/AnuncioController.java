package br.com.fiap.Insight.ia.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.Insight.ia.exception.RestNotAuthorizedException;
import br.com.fiap.Insight.ia.exception.RestNotFoundException;
import br.com.fiap.Insight.ia.models.Anuncio;
import br.com.fiap.Insight.ia.models.Status;
import br.com.fiap.Insight.ia.models.Usuario;
import br.com.fiap.Insight.ia.repository.AnuncioRepository;
import br.com.fiap.Insight.ia.repository.UsuarioRepository;
import br.com.fiap.Insight.ia.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/anuncio")
public class AnuncioController {

    Logger log = LoggerFactory.getLogger(AnuncioController.class);

    @Autowired
    AnuncioRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    AuthService authService = new AuthService();

    @PostMapping
    public ResponseEntity<EntityModel<Anuncio>> create(@RequestBody @Valid Anuncio anuncio) {
        log.info("Cadastrando Anuncio" + anuncio);

        if (anuncio.getUsuario().getId() != authService.getUsuarioLogado(usuarioRepository).getId()) {
            throw new RestNotAuthorizedException("Não é possivel cadastrar anuncio para outro usuario");
        }

        repository.save(anuncio);

        return ResponseEntity
                .created(anuncio.toEntityModel().getRequiredLink("self").toUri())
                .body(anuncio.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Anuncio> show(@PathVariable Integer id) {

        Anuncio anuncio = getAnuncio(id);
        Usuario usuarioLogado = authService.getUsuarioLogado(usuarioRepository);

        if (anuncio.getUsuario().getId() != usuarioLogado.getId()) {
            throw new RestNotAuthorizedException("Não é possivel visualizar anuncio de outro usuario");
        }

        return anuncio.toEntityModel();

    }

    @GetMapping("/usuario")
    public ResponseEntity<List<Anuncio>> listByUsuario() {

        Usuario usuario = authService.getUsuarioLogado(usuarioRepository);

        return ResponseEntity.ok(
                repository
                        .findByUsuarioIdOrderByIdDesc(usuario.getId())
                        .stream()
                        .filter(anuncio -> anuncio.getStatus().equals(Status.ATIVO))
                        .toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Anuncio> destroy(@PathVariable Integer id) {

        Usuario usuarioLogado = authService.getUsuarioLogado(usuarioRepository);
        Anuncio anuncio = getAnuncio(id);

        if (anuncio.getUsuario().getId() != usuarioLogado.getId()) {
            throw new RestNotAuthorizedException("Não é possivel excluir anuncio de outro usuario");
        }

        anuncio.setStatus(Status.INATIVO);
        repository.save(anuncio);

        return ResponseEntity.noContent().build();
    }

    private Anuncio getAnuncio(Integer id) {
        return repository
                .findById(id)
                .filter(anuncio -> anuncio.getStatus().equals(Status.ATIVO))
                .orElseThrow(() -> new RestNotFoundException("Anuncio não encontrado"));
    }
}
