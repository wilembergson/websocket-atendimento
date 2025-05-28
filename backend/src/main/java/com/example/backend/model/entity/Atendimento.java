package com.example.backend.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String status;

    @Column
    private LocalDateTime data;

    @Column(name = "SINAL_SONORO")
    private Integer sinalSonoro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ficha", referencedColumnName = "id")
    private Ficha ficha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_guiche", referencedColumnName = "id")
    private Guiche guiche;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCAL_ID", referencedColumnName = "ID", nullable = false)
    private Local local;

    public String getData(){
        return data.getDayOfMonth()+"/"+data.getMonthValue()+"/"+data.getYear()+" "+data.getHour()+":"+data.getMinute();
    }
}
