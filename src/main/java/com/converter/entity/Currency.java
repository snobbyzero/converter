package com.converter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "currency")
public class Currency {

    @Column(name = "id")
    @NonNull
    @Id
    private String id;

    @Column(name = "num_code")
    @NonNull
    private Integer numCode;

    @Column(name = "char_code")
    @NonNull
    private String charCode;

    @Column(name = "nominal")
    @NonNull
    private Float nominal;

    @Column(name = "name")
    @NonNull
    private String name;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExchangeRate> exchangeRateList = new ArrayList<>();

    public void addExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRateList.add(exchangeRate);
    }
}
