package com.dragon.featureexercise.repo;

import com.dragon.featureexercise.domain.EnabledUserFeature;
import com.dragon.featureexercise.domain.UserFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnabledUserFeatureRepository extends JpaRepository<EnabledUserFeature, Long> {

}
