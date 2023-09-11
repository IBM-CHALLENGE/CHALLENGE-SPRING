package br.com.fiap.Insight.ia.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.Insight.ia.exception.RestNotAuthorizedException;
import br.com.fiap.Insight.ia.exception.RestNotFoundException;
import br.com.fiap.Insight.ia.exception.RestValueInvalidException;
import br.com.fiap.Insight.ia.models.Transacao;
import br.com.fiap.Insight.ia.models.Usuario;
import br.com.fiap.Insight.ia.repository.TransacaoRepository;
import br.com.fiap.Insight.ia.repository.UsuarioRepository;
import br.com.fiap.Insight.ia.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    Logger log = LoggerFactory.getLogger(TransacaoController.class);
    AuthService authService = new AuthService();

    @Autowired
    TransacaoRepository repository;
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Transacao>> create(@RequestBody @Valid Transacao transacao) {
       
        Usuario usuarioLogado = authService.getUsuarioLogado(usuarioRepository);

        if(transacao.getUsuario().getId() != usuarioLogado.getId()){
            throw new RestNotAuthorizedException("Não é possivel cadastrar transacao para outro usuario");
        }
        
        if(transacao.getValor() <= 0.0){
            throw new RestValueInvalidException("O valor da transacao deve ser maior que zero");
        }

        usuarioLogado.setSaldo(usuarioLogado.getSaldo() + transacao.getValor());

        repository.save(transacao);
        usuarioRepository.save(usuarioLogado);

        return ResponseEntity
                .created(transacao.toEntityModel().getRequiredLink("self").toUri())
                .body(transacao.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Transacao> show(@PathVariable Integer id) {
        
        Transacao transacao = getTransacao(id);
        Usuario usuario = authService.getUsuarioLogado(usuarioRepository);

        if(transacao.getUsuario().getId() != usuario.getId())
            throw new RestNotAuthorizedException("Não é possivel buscar transacoes de outro usuario");

        return transacao.toEntityModel();
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<Transacao>> listByUsuario(){
        Usuario usuario = authService.getUsuarioLogado(usuarioRepository);

        return ResponseEntity.ok(repository.findByUsuarioIdOrderByDataCadastroDesc(usuario.getId()));
    }

    private Transacao getTransacao(Integer id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new RestNotFoundException("cadastro não encontrada"));
    }

}
