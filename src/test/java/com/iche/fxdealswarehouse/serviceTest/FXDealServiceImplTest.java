package com.iche.fxdealswarehouse.serviceTest;

import com.iche.fxdealswarehouse.dto.request.FXDealRequest;
import com.iche.fxdealswarehouse.dto.response.APIResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealDataResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealResponse;
import com.iche.fxdealswarehouse.exception.DuplicateException;
import com.iche.fxdealswarehouse.exception.NotFoundException;
import com.iche.fxdealswarehouse.model.FXDeal;
import com.iche.fxdealswarehouse.repository.FXDealRepository;
import com.iche.fxdealswarehouse.service.fxDealServiceImpl.FxDealServiceImpl;
import com.iche.fxdealswarehouse.utils.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class FXDealServiceImplTest {

    @Mock
    private FXDealRepository fxDealRepository;

    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private FxDealServiceImpl fxDealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveFXDeal_ShouldSaveAndReturnDeal() {
        FXDealRequest request = new FXDealRequest(
                "123",
                "USD",
                "EUR",
                Instant.now(),
                new BigDecimal(100));

        when(fxDealRepository.existsByUniqueId(any())).thenReturn(false);
        when(fxDealRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        doNothing().when(validationUtils).validate(any());
        APIResponse<FXDealDataResponse> apiResponse = fxDealService.saveFXDeal(request);

        assertEquals("123", apiResponse.getPayload().getUniqueId());
    }

    @Test
    void saveFXDeal_ShouldThrowDuplicateDealException() {
        FXDealRequest request = new FXDealRequest("123", "USD", "EUR", Instant.now(), new BigDecimal(100));

        when(fxDealRepository.existsByUniqueId(any())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> {
            fxDealService.saveFXDeal(request);
        });
    }

    @Test
    void testGetFXDealByUniqueId_NotFound() {
        when(fxDealRepository.findFXDealByUniqueId("123")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> fxDealService.getFXDealByUniqueId("123"));
    }

    @Test
    void testGetFXDealByUniqueId_Found() {
        FXDeal deal = new FXDeal();
        when(fxDealRepository.findFXDealByUniqueId("123")).thenReturn(Optional.of(deal));

        APIResponse<FXDealResponse> apiResponse = fxDealService.getFXDealByUniqueId("123");

        assertEquals(HttpStatus.OK.value(), apiResponse.getPayload().getStatusCode());
        assertFalse(apiResponse.getPayload().isError());
        assertTrue(apiResponse.getPayload().isSuccess());
    }

    @Test
    void testGetAllFXDeals() {
        FXDeal deal1 = new FXDeal();
        FXDeal deal2 = new FXDeal();
        when(fxDealRepository.findAll()).thenReturn(Arrays.asList(deal1, deal2));

        APIResponse<List<FXDeal>> apiResponse = fxDealService.getAllFXDeals();

        assertEquals(2, apiResponse.getPayload().size());
        assertTrue(apiResponse.getPayload().contains(deal1));
        assertTrue(apiResponse.getPayload().contains(deal2));
    }
}
