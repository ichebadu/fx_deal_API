package com.iche.fxdealswarehouse.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FXDealRequest {

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



}
