package com.iche.fxdealswarehouse.controllerTest;

import com.iche.fxdealswarehouse.controller.FXDealController;
import com.iche.fxdealswarehouse.dto.request.FXDealRequest;
import com.iche.fxdealswarehouse.dto.response.APIResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealDataResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealResponse;
import com.iche.fxdealswarehouse.model.FXDeal;
import com.iche.fxdealswarehouse.repository.FXDealRepository;
import com.iche.fxdealswarehouse.service.FXDealService;
import com.iche.fxdealswarehouse.service.fxDealServiceImpl.FxDealServiceImpl;
import com.iche.fxdealswarehouse.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@Slf4j
class FXDealControllerTest {

    @Mock
    private FXDealService fxDealService;

    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private FXDealController fxDealController;

    @Test
    void saveDeal() {
        Instant testInstant = Instant.now();
        FXDealRequest request = new FXDealRequest(
                "FX2387",
                "EUR",
                "USD",
                testInstant,
                BigDecimal.valueOf(110)
        );
        FXDealDataResponse expectedDealDataResponse = new FXDealDataResponse(
                1L, // id is null as it is generated by the database
                request.getUniqueId(),
                request.getFromCurrency(),
                request.getToCurrency(),
                testInstant,
                request.getDealAmount()
        );
        APIResponse<FXDealDataResponse> expectedApiResponse = new APIResponse<>(expectedDealDataResponse);

        // Mocking the behavior of fxDealService.saveFXDeal
        when(fxDealService.saveFXDeal(request)).thenReturn(expectedApiResponse);

        // Execute the method under test
        ResponseEntity<APIResponse<FXDealDataResponse>> responseEntity = fxDealController.saveDeal(request);

        // Logging for debugging
        log.info("Expected APIResponse: {}", expectedApiResponse);
        log.info("Actual APIResponse: {}", responseEntity.getBody());

        // Assertions
        assertNotNull(responseEntity.getBody()); // Check that the response body is not null
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Compare the payload of the expected APIResponse with the payload of the actual APIResponse
        assertEquals(expectedApiResponse.getPayload(), responseEntity.getBody().getPayload());
    }


    @Test
    void testGetFXDeal_Success() {
        String uniqueId = "123";
        FXDealResponse serviceResponse = new FXDealResponse(true, false, new FXDeal(), HttpStatus.OK.value());
        when(fxDealService.getFXDealByUniqueId(uniqueId)).thenReturn(new APIResponse<>(serviceResponse));

        ResponseEntity<APIResponse<FXDealResponse>> response = fxDealController.getFXDeal(uniqueId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        APIResponse<FXDealResponse> apiResponse = response.getBody();

        assertNotNull(apiResponse); // Check that the APIResponse is not null

        FXDealResponse payload = apiResponse.getPayload();
        assertNotNull(payload); // Check that the payload is not null

        assertEquals(serviceResponse, payload);
    }

    @Test
    void testGetAllFXDeals() {
        FXDeal deal1 = new FXDeal();
        FXDeal deal2 = new FXDeal();
        List<FXDeal> expectedDeals = Arrays.asList(deal1, deal2);
        when(fxDealService.getAllFXDeals()).thenReturn(new APIResponse<>(expectedDeals));

        ResponseEntity<APIResponse<List<FXDeal>>> deals = fxDealController.getAllFXDeals();

        assertEquals(HttpStatus.OK, deals.getStatusCode());
        assertEquals(expectedDeals, deals.getBody().getPayload());
    }
}
