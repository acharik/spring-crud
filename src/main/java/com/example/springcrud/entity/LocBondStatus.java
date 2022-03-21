package com.example.springcrud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loc_bond_status",uniqueConstraints = @UniqueConstraint(columnNames = { "id", "date_status","id_loc_contract" },
                                                                            name = "uniq_num_constraint"))
public class LocBondStatus {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_status",nullable = false)
    @NonNull
    private LocalDate dateStatus;
    @Column(name = "total_main",nullable = false)
    @NonNull
    @Digits(integer=17, fraction=4, message = "Неверный формат")
    private BigDecimal totalMain;
    @Column(name = "cancel_main")
    @Digits(integer=17, fraction=4, message = "Неверный формат")
    private BigDecimal cancelMain;
    @NonNull
    @JsonBackReference
    @ManyToOne(
            targetEntity = LocContract.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "id_loc_contract",nullable = false)
    private LocContract locContractId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LocBondStatus that = (LocBondStatus) o;
        return id != null && Objects.equals(id, that.id);
    }

}
