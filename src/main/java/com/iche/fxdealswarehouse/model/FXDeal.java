package com.iche.fxdealswarehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class FXDeal {
    @Id
    @GeneratedValue(strategy =
    GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, name = "unique_id")
    @JsonProperty("unique_id")
    private String uniqueId;

    @Column(name="from_currency")
    @JsonProperty("from_currency")
    private String fromCurrency;

    @Column(name="to_currency")
    @JsonProperty("to_currency")
    private String toCurrency;

    @Column(name = "deal_timestamp")
    @JsonProperty("deal_timestamp")
    private Instant dealTimestamp;

    @Column(name = "deal_amount")
    @JsonProperty("deal_amount")
    private BigDecimal dealAmount;

    public FXDeal(String uniqueId, String fromCurrency,String toCurrency, Instant dealTimestamp, BigDecimal dealAmount){
        this.uniqueId = uniqueId;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.dealTimestamp = dealTimestamp;
        this.dealAmount = dealAmount;
    }
}
