package Alina.DedMorozik.repository;

import Alina.DedMorozik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}