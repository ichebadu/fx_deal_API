package com.iche.fxdealswarehouse.controllerTest;

import com.iche.fxdealswarehouse.controller.FXDealController;
import com.iche.fxdealswarehouse.dto.request.FXDealRequest;
import com.iche.fxdealswarehouse.dto.response.APIResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealDataResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealResponse;
import com.iche.fxdealswarehouse.model.FXDeal;
import com.iche.fxdealswarehouse.service.FXDealService;
import com.iche.fxdealswarehouse.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FXDealControllerTest {

    @Mock
    private FXDealService fxDealService;

    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private FXDealController fxDealController;

    @Test
    void saveDeal_Success() {
        Instant testInstant = Instant.now();
        FXDealRequest request = new FXDealRequest(
                "FX2387",
                "EUR",
                "USD",
                testInstant,
                BigDecimal.valueOf(110)
        );
        FXDealDataResponse expectedDealDataResponse = new FXDealDataResponse(
                1L,
                request.getUniqueId(),
                request.getFromCurrency(),
                request.getToCurrency(),
                testInstant,
                request.getDealAmount()
        );
        APIResponse<FXDealDataResponse> expectedApiResponse = new APIResponse<>(expectedDealDataResponse);

        when(fxDealService.saveFXDeal(request)).thenReturn(expectedApiResponse);
        ResponseEntity<APIResponse<FXDealDataResponse>> responseEntity = fxDealController.saveDeal(request);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(expectedApiResponse.getPayload(), responseEntity.getBody().getPayload());
    }

    @Test
    void getFXDealByUniqueId_Success() {
        String uniqueId = "123";
        FXDealResponse serviceResponse = new FXDealResponse(true, false, new FXDeal(), HttpStatus.OK.value());
        when(fxDealService.getFXDealByUniqueId(uniqueId)).thenReturn(new APIResponse<>(serviceResponse));

        ResponseEntity<APIResponse<FXDealResponse>> response = fxDealController.getFXDeal(uniqueId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        APIResponse<FXDealResponse> apiResponse = response.getBody();

        assertNotNull(apiResponse);

        FXDealResponse payload = apiResponse.getPayload();
        assertNotNull(payload);

        assertEquals(serviceResponse, payload);
    }

    @Test
    void getAllFXDeals_Success() {


        FXDeal deal1 = new FXDeal();
        FXDeal deal2 = new FXDeal();
        List<FXDeal> expectedDeals = Arrays.asList(deal1, deal2);
        when(fxDealService.getAllFXDeals()).thenReturn(new APIResponse<>(expectedDeals));

        ResponseEntity<APIResponse<List<FXDeal>>> deals = fxDealController.getAllFXDeals();

        assertEquals(HttpStatus.OK, deals.getStatusCode());
        assertEquals(expectedDeals, deals.getBody().getPayload());
    }
}
