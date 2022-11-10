package com.dragon.featureexercise.repo;

import com.dragon.featureexercise.domain.UserFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFeatureRepository extends JpaRepository<UserFeature, Long> {

}
