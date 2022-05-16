package ru.nsu.yattroman.dormsys.entity.dormitory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endTime;

    @OneToOne(fetch = FetchType.LAZY)
    private Inhabitant inhabitant;

    public Contract(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
