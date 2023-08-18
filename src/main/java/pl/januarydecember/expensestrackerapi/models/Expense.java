package pl.januarydecember.expensestrackerapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_seq")
    @SequenceGenerator(name = "expense_seq", sequenceName = "expense_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "price", nullable = false)
    private String price;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "comment", length = 2500)
    private String comment;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
