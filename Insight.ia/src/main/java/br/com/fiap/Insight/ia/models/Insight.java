package br.com.fiap.Insight.ia.models;


import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import br.com.fiap.Insight.ia.controllers.InsightController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Insight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String conteudo;

    @NotBlank
    private String imagem;

    public EntityModel<Insight> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(InsightController.class).show(id)).withSelfRel(),
            linkTo(methodOn(InsightController.class).destroy(id)).withRel("delete"));
            //linkTo(methodOn(InsightController.class)).index(Pageable.unpaged())).withRel("all");
    }

    

}
