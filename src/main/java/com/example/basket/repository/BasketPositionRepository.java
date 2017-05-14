package com.example.basket.repository;

import com.example.basket.entity.BasketPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketPositionRepository extends JpaRepository<BasketPosition, Long> {

}
