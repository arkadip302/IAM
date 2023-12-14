package com.tux.iam.repository;

import com.tux.iam.entity.Office;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OfficeRepository extends MongoRepository<Office,Integer> {

    Optional<Office> findByOfficeName(String officeName);
}
