package com.iche.fxdealswarehouse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FXDealDataResponse {
    private Long id;
    private String uniqueId;

    private String fromCurrency;

    private String toCurrency;

    private Instant dealTimestamp;

    private BigDecimal dealAmount;



}
