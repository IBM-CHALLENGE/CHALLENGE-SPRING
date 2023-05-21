package br.com.fiap.Insight.ia.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.Insight.ia.controllers.AnuncioController;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 1, max = 100)
    private String descricao;

    @NotEmpty
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotEmpty
    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "anuncio")
    private List<Comando> comandos;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "anuncio")
    private List<Insight> insights;

    public EntityModel<Anuncio> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(AnuncioController.class).show(id)).withSelfRel(),
            linkTo(methodOn(AnuncioController.class).destroy(id)).withRel("delete"))
            ;
    }

}
