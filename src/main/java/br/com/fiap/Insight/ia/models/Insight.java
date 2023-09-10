package br.com.fiap.Insight.ia.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
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
public class Insight implements IChat{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(length = 3000)
    private String conteudo;

    @NotBlank
    @Column(length = 3000)
    private String imagem;

    @NotNull
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Anuncio anuncio;

    @Override
    public String getTipoMensagem() {
        return this.getClass().getSimpleName();
    }

    // public EntityModel<Insight> toEntityModel(){
    //     return EntityModel.of(
    //         this, 
    //         linkTo(methodOn(InsightController.class).show(id)).withSelfRel(),
    //         linkTo(methodOn(InsightController.class).destroy(id)).withRel("delete"));
    //         linkTo(methodOn(InsightController.class)).index(Pageable.unpaged())).withRel("all");
    // }
}
