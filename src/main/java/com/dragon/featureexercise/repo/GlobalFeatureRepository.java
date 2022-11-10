package com.dragon.featureexercise.repo;

import com.dragon.featureexercise.domain.GlobalFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalFeatureRepository extends JpaRepository<GlobalFeature, Long> {

}
