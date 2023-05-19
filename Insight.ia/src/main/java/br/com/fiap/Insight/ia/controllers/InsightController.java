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
import br.com.fiap.Insight.ia.models.Insight;
import br.com.fiap.Insight.ia.repository.InsightRepository;

@RestController
@RequestMapping("/api/insight")
public class InsightController {

    Logger log = LoggerFactory.getLogger(InsightController.class);

    @Autowired
    InsightRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    //@GetMapping
    //    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) org.springframework.data.domain.Pageable pageable){
    //      var insights = repository.findAll(pageable);

    //    return assembler.toModel(insights.map(Insight::toEntityModel));
//}

    @PostMapping
        public ResponseEntity<EntityModel<Insight>> create(@RequestBody Insight insight){
        log.info("Cadastrando Insight" + insight);

        repository.save(insight);

        return ResponseEntity
            .created(insight.toEntityModel().getRequiredLink("self").toUri())
            .body(insight.toEntityModel());
        }

    
    @GetMapping("{id}")
    public EntityModel<Insight> show(@PathVariable Integer id){
        log.info("Buscando Insight com id " + id);

        return getinsight(id).toEntityModel();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Insight> destroy(@PathVariable Integer id){
        log.info("Apagando insight com id " + id);
        var insight = getinsight(id);

        repository.delete(insight);

        return ResponseEntity.noContent().build();

    }


        private Insight getinsight (Integer id){
            return repository.findById(id).orElseThrow(() -> new RestNotFoundException("Insight não encontrado"));
        }

        
    }

