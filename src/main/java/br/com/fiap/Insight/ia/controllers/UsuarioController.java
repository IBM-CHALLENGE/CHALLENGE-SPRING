package br.com.fiap.Insight.ia.controllers;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.Insight.ia.exception.RestNotFoundException;
import br.com.fiap.Insight.ia.models.Usuario;
import br.com.fiap.Insight.ia.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    UsuarioRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @PostMapping()
    public ResponseEntity<EntityModel<Usuario>> create(@RequestBody @Valid Usuario usuario) {
        log.info("Cadastrando usuario" + usuario);
        repository.save(usuario);

        return ResponseEntity
                .created(usuario.toEntityModel().getRequiredLink("self").toUri())
                .body(usuario.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Usuario> show(@PathVariable Integer id) {
        log.info("Buscando tarefa com id " + id);
        return getUsuario(id).toEntityModel();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Usuario> destroy(@PathVariable Integer id) {
        log.info("Apagando usuario com id " + id);
        var Usuario = getUsuario(id);

        repository.delete(Usuario);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public EntityModel<Usuario> update(@PathVariable Integer id, @RequestBody @Valid Usuario usuario) {
        log.info("Atualizando usuario com id " + id);
        getUsuario(id);
        usuario.setId(id);
        repository.save(usuario);

        return usuario.toEntityModel();

    }

    private Usuario getUsuario(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RestNotFoundException("Usuario não encontrada"));
    }

}// class
