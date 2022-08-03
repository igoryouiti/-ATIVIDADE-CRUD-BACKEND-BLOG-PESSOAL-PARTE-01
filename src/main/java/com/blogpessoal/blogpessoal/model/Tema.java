package com.blogpessoal.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "tb_temas")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String descricao;

    @OneToMany(mappedBy = "tema", cascade = CascadeType.REMOVE)  // remove as postagens relacionadas ao tema deletado  // nome do objeto tema na classe postagens
    @JsonIgnoreProperties("tema")   // não permite que a busca entre em loop - ao buscar um tema, chama postagem e para, postagem nao chama mais tema
    private List<Postagem> postagens;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
