package com.iche.fxdealswarehouse.repository;

import com.iche.fxdealswarehouse.model.FXDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FXDealRepository extends JpaRepository<FXDeal, Long> {

    boolean existsByUniqueId(String uniqueId);
    Optional<FXDeal> findFXDealByUniqueId(String uniqueId);
}
