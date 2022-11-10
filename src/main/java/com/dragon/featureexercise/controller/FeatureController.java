package com.dragon.featureexercise.controller;

import com.dragon.featureexercise.model.FeatureRequest;
import com.dragon.featureexercise.model.UserFeatureRequest;
import com.dragon.featureexercise.services.GlobalFeatureService;
import com.dragon.featureexercise.services.UserFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@Validated
public class FeatureController {

    @Autowired
    GlobalFeatureService globalFeatureService;

    @Autowired
    UserFeatureService userFeatureService;

    @GetMapping("/features")
    public Set<String> getEnabledFeatures(Principal principal){
        Set<String> enabledFeatures =  globalFeatureService.getGlobalEnabledFeature();
        if(hasUserRole()){
            enabledFeatures.addAll(userFeatureService.getEnabledUserFeature(principal.getName()));
        }
        return enabledFeatures;
    }

    @PostMapping("/feature")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFeature(@RequestBody FeatureRequest featureRequest) throws Exception {
        switch (featureRequest.getFeatureType()){
            case GLOBAL:
                globalFeatureService.createGlobalFeature(featureRequest.getFeatureName());
            case USER:
                userFeatureService.createUserFeature(featureRequest.getFeatureName());
        }
    }

    @PutMapping("/feature")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleUserFeature(@RequestBody UserFeatureRequest userFeatureRequest) throws Exception {
        userFeatureService.enableDisableUserFeature(userFeatureRequest.getUserName(), userFeatureRequest.getFeatureName());
    }

    private boolean hasUserRole(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
    }
}
