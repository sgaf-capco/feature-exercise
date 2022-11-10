package com.dragon.featureexercise.services;

import com.dragon.featureexercise.domain.EnabledUserFeature;
import com.dragon.featureexercise.domain.UserFeature;
import com.dragon.featureexercise.exceptions.FeatureAlreadyExistException;
import com.dragon.featureexercise.exceptions.FeatureDoesNotExistException;
import com.dragon.featureexercise.repo.EnabledUserFeatureRepository;
import com.dragon.featureexercise.repo.UserFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Component
public class UserFeatureService {

    @Autowired
    UserFeatureRepository userFeatureRepository;

    @Autowired
    EnabledUserFeatureRepository enabledUserFeatureRepository;

    public Set<String> getEnabledUserFeature(String username){
        EnabledUserFeature enabledUserFeature = findEnabledFeatureBy(username);
        return (enabledUserFeature != null) ?
                enabledUserFeature.getEnabledFeatures().stream().map(UserFeature::getName).collect(Collectors.toSet()) : new HashSet<>();
    }

    private EnabledUserFeature findEnabledFeatureBy(String username){
        List<EnabledUserFeature> enabledUserFeatures = enabledUserFeatureRepository.findAll();
        List<EnabledUserFeature> enabledUserFeature = enabledUserFeatures.stream().filter(ef -> ef.getUsername().equals(username)).collect(Collectors.toList());
        return enabledUserFeature.size()>0 ? enabledUserFeature.get(0) : null;
    }

    public void enableDisableUserFeature(String username, String userFeatureName) throws Exception {
        if(!isFeatureExist(userFeatureName)) {
            throw new FeatureDoesNotExistException("Feature not found");
        }
        Set<String> enabledFeatures = getEnabledUserFeature(username);
        EnabledUserFeature enabledUserFeature = findEnabledFeatureBy(username);
        List<UserFeature> userFeatureList = enabledUserFeature.getEnabledFeatures();
        UserFeature userFeature = UserFeature.builder().name(userFeatureName).build();

        if(!enabledFeatures.contains(userFeatureName)){
            userFeatureList.add(userFeature);
        } else {
            userFeatureList.remove(userFeature);
        }
        enabledUserFeature.setEnabledFeatures(userFeatureList);
        enabledUserFeatureRepository.save(enabledUserFeature);

    }

    private boolean isFeatureExist(String userFeatureName){
        List<UserFeature> userFeatureList = userFeatureRepository.findAll().stream().filter(uf -> uf.getName().equals(userFeatureName)).collect(Collectors.toList());
        return userFeatureList.size() == 1;

    }

    public void createUserFeature(String userFeatureName) throws Exception {
        UserFeature userFeature = UserFeature.builder().name(userFeatureName).build();
        if(isFeatureExist(userFeatureName)){
            throw new FeatureAlreadyExistException("Feature already exist");
        }

        userFeatureRepository.save(userFeature);
    }


}
