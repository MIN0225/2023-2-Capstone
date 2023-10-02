package com.hapjusil.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate reservationDate;
    private LocalDate startTime;
    private LocalDate endTime;
    private String status;

    @ManyToOne
    @JoinColumn(name = "roomID")
    private Room room;
}

