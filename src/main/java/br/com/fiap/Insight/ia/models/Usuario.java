package br.com.fiap.Insight.ia.models;

import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.fiap.Insight.ia.controllers.UsuarioController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
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
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @NotNull
    @Size(min = 3, max = 100)
    private String nome;
    
    @NotEmpty
    @NotNull
    @Size(min = 1, max = 100)
    @Email
    @Column(unique = true)
    private String email;
    
    @NotEmpty
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 5, max = 200)
    private String senha;

    @NotNull
    private Double saldo = 100.0;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public EntityModel<Usuario> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(UsuarioController.class).show(id)).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(UsuarioController.class).update(id, this)).withRel("update")
        );
    }
}
