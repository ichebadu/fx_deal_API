package com.iche.fxdealswarehouse.service.fxDealServiceImpl;

import com.iche.fxdealswarehouse.dto.request.FXDealRequest;
import com.iche.fxdealswarehouse.dto.response.APIResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealDataResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealResponse;
import com.iche.fxdealswarehouse.exception.DuplicateException;
import com.iche.fxdealswarehouse.exception.NotFoundException;
import com.iche.fxdealswarehouse.model.FXDeal;
import com.iche.fxdealswarehouse.repository.FXDealRepository;
import com.iche.fxdealswarehouse.service.FXDealService;
import com.iche.fxdealswarehouse.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class FxDealServiceImpl implements FXDealService {
    private final FXDealRepository fxDealRepository;
    private final ValidationUtils validationUtils;

    @Override
    public APIResponse<FXDealDataResponse> saveFXDeal(FXDealRequest fxDealRequest) {

        if(fxDealRepository.existsByUniqueId(fxDealRequest.getUniqueId())){
            log.info("Attempt to save a duplicate deal with ID {}", fxDealRequest.getUniqueId());
            throw new DuplicateException("Deal already exists with ID: " +
                    fxDealRequest.getUniqueId());
        }

        FXDeal fxDeal = FXDeal.builder()
                .uniqueId(fxDealRequest.getUniqueId())
                .fromCurrency(fxDealRequest.getFromCurrency())
                .toCurrency(fxDealRequest.getToCurrency())
                .dealTimestamp(fxDealRequest.getDealTimestamp())
                .dealAmount(fxDealRequest.getDealAmount())
                .build();

        validationUtils.validate(fxDeal);


        fxDealRepository.save(fxDeal);

        FXDealDataResponse fxDealDataResponse = FXDealDataResponse.builder()
                .id(fxDeal.getId())
                .uniqueId(fxDeal.getUniqueId())
                .fromCurrency(fxDeal.getFromCurrency())
                .toCurrency(fxDeal.getToCurrency())
                .dealTimestamp(fxDeal.getDealTimestamp())
                .dealAmount(fxDeal.getDealAmount())
                .build();

        log.info("FX Deal response {}", fxDealDataResponse);

        return new APIResponse<>(fxDealDataResponse) ;
    }

    @Override
    public APIResponse<FXDealResponse> getFXDealByUniqueId(String uniqueId) {

        FXDeal fxDeal = fxDealRepository.findFXDealByUniqueId(uniqueId)
                .orElseThrow(() -> {
                    log.error("No deal found with Id: {}", uniqueId);
                    return new NotFoundException("No deal found with ID: " + uniqueId);
                });

        FXDealResponse fxDealResponse = new FXDealResponse(true, false, fxDeal, HttpStatus.OK.value());
        return new APIResponse<>(fxDealResponse);
    }

    @Override
    public APIResponse<List<FXDeal>> getAllFXDeals() {
        var fxdealList = fxDealRepository.findAll();
        return new APIResponse<>(fxdealList);
    }

}
