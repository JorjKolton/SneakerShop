package com.sobchenko.sneakershop.repository;

import com.sobchenko.sneakershop.model.Bucket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends CrudRepository<Bucket, String> {
    boolean existsByUserId(String userId);
}