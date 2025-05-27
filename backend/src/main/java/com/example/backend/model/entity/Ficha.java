package com.example.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ficha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Integer numero;

    @Column
    private String tipo;

    @OneToOne(mappedBy = "ficha")
    @JsonBackReference
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCAL_ID", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    private Local local;

    public String getIdentificacao(){
        return "FI"+numero;
    }
}
