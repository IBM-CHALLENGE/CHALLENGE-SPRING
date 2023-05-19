package br.com.fiap.Insight.ia.models;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.Insight.ia.controllers.AnuncioController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotEmpty
    private String descricao;


    public EntityModel<Anuncio> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(AnuncioController.class).show(id)).withSelfRel(),
            linkTo(methodOn(AnuncioController.class).destroy(id)).withRel("delete"))
            ;
    }

}
