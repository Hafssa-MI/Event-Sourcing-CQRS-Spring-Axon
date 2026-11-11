package ma.formation.demoeventscqrsspringaxon.query.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.formation.demoeventscqrsspringaxon.enums.OperationType;

import java.time.Instant;
import java.util.Date;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountOperation {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String currency;
    @ManyToOne
    private Account account;
}
