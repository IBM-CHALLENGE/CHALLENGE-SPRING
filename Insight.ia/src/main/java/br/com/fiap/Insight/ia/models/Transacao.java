package br.com.fiap.Insight.ia.models;

import java.util.Calendar;


import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;


import br.com.fiap.Insight.ia.controllers.TransacaoController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotEmpty
    private Calendar dataCadastro;

    @NotEmpty
    private Double valor;


    public EntityModel<Transacao> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(TransacaoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(TransacaoController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(TransacaoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }


    
}