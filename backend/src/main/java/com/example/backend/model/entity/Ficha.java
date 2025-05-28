package com.example.backend.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ficha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Integer numero;

    @Column
    private String tipo;

    @OneToOne(mappedBy = "ficha")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCAL_ID", referencedColumnName = "ID", nullable = false)
    private Local local;

    public String getIdentificacao(){
        return "FI"+numero;
    }
}
