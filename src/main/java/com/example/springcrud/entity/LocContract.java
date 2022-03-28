package com.example.springcrud.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loc_contract",uniqueConstraints = @UniqueConstraint(columnNames = "num_contract",name = "uniq_num_constraint"))
@Check(constraints = "date_end >= date_begin")
public class LocContract {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_begin",nullable = false)
    @NonNull

    private LocalDate dateBegin;
    @Column(name = "date_end")
    private LocalDate dateEnd;
    @Column(name = "num_contract",nullable = false)
    @NonNull
    @Pattern(regexp = "[a-zA-ZА-Яа-яЁё0-9/]*",message = "Неверный формат")
    private String numContract;
    @Column(name = "sum",nullable = false)
    @NonNull
    @Digits(integer=17, fraction=4, message = "Неверный формат")
    private BigDecimal sum;
    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy="locContractId",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Column(name = "loc_bond_statuses")
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<LocBondStatus> locBondStatuses;

    public LocContract(Long id, @NonNull LocalDate dateBegin, LocalDate dateEnd, @NonNull String numContract, @NonNull BigDecimal sum, String comment) {
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.numContract = numContract;
        this.sum = sum;
        this.comment = comment;
    }
}
