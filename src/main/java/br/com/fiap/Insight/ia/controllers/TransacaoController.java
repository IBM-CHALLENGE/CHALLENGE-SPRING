package br.com.fiap.Insight.ia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.Insight.ia.exception.RestNotFoundException;
import br.com.fiap.Insight.ia.models.Transacao;
import br.com.fiap.Insight.ia.repository.TransacaoRepository;

@RestController
@RequestMapping("api/transacao")
public class TransacaoController {

    Logger log = LoggerFactory.getLogger(TransacaoController.class);

    @Autowired
    TransacaoRepository repository; // IoD

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>>index(@PageableDefault(size = 5) org.springframework.data.domain.Pageable pageable) {
            var transacoes = repository.findAll(pageable);

        return assembler.toModel(transacoes.map(Transacao::toEntityModel));
    }

    @PostMapping
        public ResponseEntity<EntityModel<Transacao>> create(@RequestBody Transacao transacao){
        log.info("Cadastrando transacao" + transacao);

        repository.save(transacao);

        return ResponseEntity
            .created(transacao.toEntityModel().getRequiredLink("self").toUri())
            .body(transacao.toEntityModel());
        }

        @GetMapping("{id}")
        public EntityModel<Transacao> show(@PathVariable Integer id){
            log.info("Buscando transacao com id " + id);
    
            
            return gettransacao(id).toEntityModel();
    
        }

    @DeleteMapping("{id}")
        public ResponseEntity<Transacao> destroy(@PathVariable Integer id){
            log.info("Apagando usuario com id " + id);
            var transacao = gettransacao(id);
    
            repository.delete(transacao);
    
            return ResponseEntity.noContent().build();
    
        }

        private Transacao gettransacao(Integer id) {
            return repository.findById(id).orElseThrow(() -> new RestNotFoundException("cadastro não encontrada"));
        }
    
}
