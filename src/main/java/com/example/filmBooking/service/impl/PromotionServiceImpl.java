package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Promotion;
import com.example.filmBooking.repository.PromotionRepository;
import com.example.filmBooking.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<Promotion> fillAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion save(Promotion promotion) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        promotion.setCode("code_promotion_" + value);
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion update(UUID id, Promotion promotion) {
        Promotion promotionNew = findById(id);
        promotionNew.setCode(promotion.getCode());
        promotionNew.setName(promotion.getName());
        promotionNew.setDescription(promotion.getDescription());
        promotionNew.setComparisonPoint(promotion.getComparisonPoint());
        promotionNew.setEndDate(promotion.getEndDate());
        promotionNew.setStartDate(promotion.getStartDate());
        promotionNew.setPercent(promotion.getPercent());
        promotionNew.setQuantity(promotion.getQuantity());
        promotionNew.setType(promotion.getType());
        return promotionRepository.save(promotion);
    }

    @Override
    public void delete(UUID id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public Promotion findById(UUID id) {
        return promotionRepository.findById(id).get();
    }
}
