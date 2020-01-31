package Alina.DedMorozik.repository;

import Alina.DedMorozik.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Integer> {
}