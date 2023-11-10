package com.iche.fxdealswarehouse.service;

import com.iche.fxdealswarehouse.dto.request.FXDealRequest;
import com.iche.fxdealswarehouse.dto.response.APIResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealDataResponse;
import com.iche.fxdealswarehouse.dto.response.FXDealResponse;
import com.iche.fxdealswarehouse.model.FXDeal;

import java.util.List;

public interface FXDealService {
    APIResponse<FXDealDataResponse> saveFXDeal (FXDealRequest fxDealRequest);
    APIResponse<FXDealResponse> getFXDealByUniqueId(String uniqueId);
    APIResponse<List<FXDeal>> getAllFXDeals();
}
