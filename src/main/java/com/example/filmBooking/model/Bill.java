package com.example.filmBooking.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "bill",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillTicket> listBillTicket;

    @OneToMany(mappedBy = "bill",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillFood> listBillFood;


    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "total_money")
    private BigDecimal totalMoney;
}
