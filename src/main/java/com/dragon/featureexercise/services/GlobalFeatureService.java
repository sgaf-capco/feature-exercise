package com.dragon.featureexercise.services;

import com.dragon.featureexercise.domain.GlobalFeature;
import com.dragon.featureexercise.domain.UserFeature;
import com.dragon.featureexercise.exceptions.FeatureAlreadyExistException;
import com.dragon.featureexercise.model.FeatureResponse;
import com.dragon.featureexercise.repo.GlobalFeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
public class GlobalFeatureService {
    Logger logger = LoggerFactory.getLogger(GlobalFeatureService.class);

    @Autowired
    GlobalFeatureRepository globalFeatureRepository;

    public Set<String> getGlobalEnabledFeature() {
        logger.info("Inside get features method");
        List<GlobalFeature> globalFeatures = globalFeatureRepository.findAll();

        return globalFeatures.stream().filter(gf -> Boolean.parseBoolean(gf.getIsEnabled())).map(GlobalFeature::getName).collect(Collectors.toSet());
    }

    public void createGlobalFeature(String name) throws FeatureAlreadyExistException {
        GlobalFeature globalFeature = GlobalFeature.builder().name(name).isEnabled("false").build();
        if(isFeatureExist(name)){
            throw new FeatureAlreadyExistException("Feature already exist");
        }
        globalFeatureRepository.save(globalFeature);
    }

    private boolean isFeatureExist(String featureName){
        List<GlobalFeature> globalFeatureList = getGlobalFeatures(featureName);
        return globalFeatureList.size() == 1;

    }


    private List<GlobalFeature> getGlobalFeatures(String featureName) {
        return globalFeatureRepository.findAll().stream().filter(gf -> gf.getName().equals(featureName)).collect(Collectors.toList());
    }

    public void enableGlobalFeature(String name){
        GlobalFeature globalFeatureToBeEnable = getGlobalFeatures(name).get(0);
        if(!Boolean.parseBoolean(globalFeatureToBeEnable.getIsEnabled())){
            globalFeatureToBeEnable.setIsEnabled("true");
            globalFeatureRepository.save(globalFeatureToBeEnable);
        }

    }


}
