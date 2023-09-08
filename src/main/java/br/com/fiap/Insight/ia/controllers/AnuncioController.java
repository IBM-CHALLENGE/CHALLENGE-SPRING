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

import br.com.fiap.Insight.ia.exception.RestNotFoundException;
import br.com.fiap.Insight.ia.models.Anuncio;
import br.com.fiap.Insight.ia.models.Status;
import br.com.fiap.Insight.ia.repository.AnuncioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/anuncio")
public class AnuncioController {

    Logger log = LoggerFactory.getLogger(AnuncioController.class);

    @Autowired
    AnuncioRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;


    @PostMapping
        public ResponseEntity<EntityModel<Anuncio>> create(@RequestBody @Valid Anuncio anuncio){
        log.info("Cadastrando Anuncio" + anuncio);

        repository.save(anuncio);

        return ResponseEntity
            .created(anuncio.toEntityModel().getRequiredLink("self").toUri())
            .body(anuncio.toEntityModel());
        }


    @GetMapping("{id}")
    public EntityModel<Anuncio> show(@PathVariable Integer id){
        log.info("Buscando Anuncio com id " + id);

        return getAnuncio(id).toEntityModel();

    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Anuncio>> listByUsuario(@PathVariable Integer idUsuario){
        log.info("Buscando Anuncios do usuario com id " + idUsuario);

        return ResponseEntity.ok(
                repository
                .findByUsuarioIdOrderByIdDesc(idUsuario)
                .stream()
                .filter(anuncio -> anuncio.getStatus().equals(Status.ATIVO))
                .toList()
            );

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Anuncio> destroy(@PathVariable Integer id){
        log.info("Apagando Anuncio com id " + id);
        var anuncio = getAnuncio(id);
        anuncio.setStatus(Status.INATIVO);

        repository.save(anuncio);

        return ResponseEntity.noContent().build();

    }

    private Anuncio getAnuncio(Integer id){
        return repository
                .findById(id)
                .filter(anuncio -> anuncio.getStatus().equals(Status.ATIVO))
                .orElseThrow(() -> new RestNotFoundException("Anuncio não encontrado"));
    }
}
