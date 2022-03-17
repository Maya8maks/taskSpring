package com.borshcheva.springTask.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class CurrencyPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_first")
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyFirst;

    @Column(name = "currency_second")
    @Enumerated(EnumType.STRING)
    private CurrencyType currencySecond;

    private Double price;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
