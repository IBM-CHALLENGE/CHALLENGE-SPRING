package br.com.fiap.Insight.ia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.Insight.ia.exception.RestConflictException;
import br.com.fiap.Insight.ia.exception.RestNotFoundException;
import br.com.fiap.Insight.ia.models.Credencial;
import br.com.fiap.Insight.ia.models.Status;
import br.com.fiap.Insight.ia.models.Token;
import br.com.fiap.Insight.ia.models.Usuario;
import br.com.fiap.Insight.ia.repository.UsuarioRepository;
import br.com.fiap.Insight.ia.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    UsuarioRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Usuario>> create(@RequestBody @Valid Usuario usuario) {
        try {
            log.info("Cadastrando usuario" + usuario);

            usuario.setSenha(encoder.encode(usuario.getSenha()));
            repository.save(usuario);

            return ResponseEntity
                    .created(usuario.toEntityModel().getRequiredLink("self").toUri())
                    .body(usuario.toEntityModel());

        } catch (DataIntegrityViolationException e) {
            throw new RestConflictException("Já existe um usuario com este email");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid Credencial credencial) {
        try {
            if (repository.findByEmail(credencial.email()).isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            manager.authenticate(credencial.toAuthentication());

            var token = tokenService.generateToken(credencial);
            
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("{id}")
    public EntityModel<Usuario> show(@PathVariable Integer id) {
        log.info("Buscando tarefa com id " + id);
        return getUsuario(id).toEntityModel();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Usuario> destroy(@PathVariable Integer id) {
        log.info("Apagando usuario com id " + id);
        Usuario usuario = getUsuario(id);
        usuario.setStatus(Status.INATIVO);

        repository.save(usuario);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public EntityModel<Usuario> update(@PathVariable Integer id, @RequestBody @Valid Usuario usuario) {
        try {
            log.info("Atualizando usuario com id " + id);
            getUsuario(id);
            usuario.setId(id);
            repository.save(usuario);

            return usuario.toEntityModel();

        } catch (DataIntegrityViolationException e) {
            throw new RestConflictException("Já existe um usuario com este email");
        }

    }

    private Usuario getUsuario(Integer id) {
        return repository
                .findById(id)
                .filter(usuario -> usuario.getStatus().equals(Status.ATIVO))
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrada"));
    }

}
