package com.converter.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "rate")
@ToString(exclude = {"currency"})
public class ExchangeRate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "value")
    @NonNull
    private Float value;

    @Column(name = "date")
    @NonNull
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "currencyId")
    private Currency currency;
}
