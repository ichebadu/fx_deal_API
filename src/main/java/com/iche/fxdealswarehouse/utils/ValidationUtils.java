package com.iche.fxdealswarehouse.utils;

import com.iche.fxdealswarehouse.exception.ValidationException;
import com.iche.fxdealswarehouse.model.FXDeal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Component
@Data
public class ValidationUtils {
    public static final int ISO_CODE_LENGTH = 3;


    public void validate(FXDeal fxDeal){

        List<String> errors = new ArrayList<>();

        validateUniqueId(fxDeal.getUniqueId(), errors);
        validateCurrencyISO(fxDeal.getToCurrency(), "Invalid from currency ISO Code", errors);

        validateTimestamp(fxDeal.getDealTimestamp(), errors);
        validateDealAmount(fxDeal.getDealAmount(), errors);

        if(!errors.isEmpty()){
            throw new ValidationException(String.join(", ", errors));
        }
    }

    private static void validateUniqueId(String uniqueId, List<String> errors) {
        if (uniqueId == null || uniqueId.trim().isEmpty()) {
            errors.add("Deal Unique ID is missing or empty");
        }
    }

    private static void validateTimestamp(Instant timestamp, List<String> errors) {
        if (timestamp == null) {
            errors.add("Deal timestamp is missing");
        }
    }

    private static void validateCurrencyISO(String currency, String errorMessage, List<String> errors){
        if(currency == null || currency.length() != ISO_CODE_LENGTH){
            errors.add(errorMessage);
        }
    }
    private static void validateDealAmount(BigDecimal amount, List<String> errors) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Invalid deal amount");
        }
    }


}
