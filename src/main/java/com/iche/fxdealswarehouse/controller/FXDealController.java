package com.iche.fxdealswarehouse.controller;

import com.iche.fxdealswarehouse.dto.request.FXDealRequest;
import com.iche.fxdealswarehouse.dto.response.APIResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealDataResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealResponse;
import com.iche.fxdealswarehouse.model.FXDeal;
import com.iche.fxdealswarehouse.service.FXDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.iche.fxdealswarehouse.utils.FxDealsEndPoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL)
public class FXDealController {
    private final FXDealService fxDealService;
    @PostMapping(IMPORT)
    public ResponseEntity<APIResponse<FXDealDataResponse>> saveDeal(@RequestBody FXDealRequest fxDealRequest){
        return new  ResponseEntity<>(fxDealService.saveFXDeal(fxDealRequest), HttpStatus.OK);

    }
    @GetMapping(GET_FX_DEAL_BY_UNIQUE_ID)
    public ResponseEntity<APIResponse<FXDealResponse>> getFXDeal(@PathVariable String uniqueId){
        return new ResponseEntity<>(fxDealService.getFXDealByUniqueId(uniqueId),HttpStatus.OK);
    }
    @GetMapping(GET_ALL_FX_DEALS)
    public ResponseEntity<APIResponse<List<FXDeal>>> getAllFXDeals(){
        return new ResponseEntity(fxDealService.getAllFXDeals(),HttpStatus.OK);
    }

}
