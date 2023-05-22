package br.com.fiap.Insight.ia.models;

import java.util.Calendar;


import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Pageable;


import br.com.fiap.Insight.ia.controllers.TransacaoController;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @EqualsAndHashCode(of = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @NotNull
    private String titulo;

    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Calendar dataCadastro;

    @NotNull
    private Double valor;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Usuario usuario;

    public EntityModel<Transacao> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(TransacaoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(TransacaoController.class).index(Pageable.unpaged())).withRel("all")
        );
    }


    
}