package br.com.fiap.Insight.ia.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comando {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank
    @NotNull
    private String conteudo;

    @NotBlank
    @NotNull
    @ManyToOne
    private Anuncio anuncio;

    

    // public EntityModel<Insight> toEntityModel(){
    //     return EntityModel.of(
    //         this, 
    //         linkTo(methodOn(InsightController.class).show(id)).withSelfRel(),
    //         linkTo(methodOn(InsightController.class).destroy(id)).withRel("delete"));
    //         //linkTo(methodOn(InsightController.class)).index(Pageable.unpaged())).withRel("all");
    // }
}
