package com.dragon.featureexercise.controller;

import com.dragon.featureexercise.model.FeatureRequest;
import com.dragon.featureexercise.model.FeatureType;
import com.dragon.featureexercise.model.UserFeatureRequest;
import com.dragon.featureexercise.services.GlobalFeatureService;
import com.dragon.featureexercise.services.UserFeatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeatureControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GlobalFeatureService globalFeatureService;

    @MockBean
    UserFeatureService userFeatureService;

    @BeforeEach
    public void init(){
        Set<String> enabledGlobalFeatures = new HashSet<>(Arrays.asList("openBankingVRP","setStandingOrder"));
        Set<String> enabledUserFeatures = new HashSet<>(Arrays.asList("changeAddress", "updateEmail"));
        Mockito.when(globalFeatureService.getGlobalEnabledFeature()).thenReturn(enabledGlobalFeatures);
        Mockito.when(userFeatureService.getEnabledUserFeature("user")).thenReturn(enabledUserFeatures);

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getEnabledFeaturesForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/features")
                    .contentType(APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(4)))
                    .andExpect(jsonPath("$", containsInAnyOrder("openBankingVRP","setStandingOrder","changeAddress", "updateEmail")));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getEnabledFeaturesForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/features")
                        .contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder("openBankingVRP","setStandingOrder")));

    }

    @Test
    @WithAnonymousUser
    void getEnabledFeaturesForAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/features").contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertEquals("Unauthorized", result.getResponse().getErrorMessage()));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createUserFeatureByAdmin() throws Exception {
        FeatureRequest featureToBeCreated = FeatureRequest.builder().featureName("someUserFeature").featureType(FeatureType.USER).build();
        mockMvc
                .perform(MockMvcRequestBuilders.post("/feature")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(featureToBeCreated)))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void userWontBeAbleToCreateFeature() throws Exception {
        FeatureRequest featureToBeCreated = FeatureRequest.builder().featureName("someUserFeature").featureType(FeatureType.USER).build();
        mockMvc
                .perform(MockMvcRequestBuilders.post("/feature")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(featureToBeCreated)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertEquals("Forbidden", result.getResponse().getErrorMessage()));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void admincanSwitchOnFeaTureForUser() throws Exception {
        UserFeatureRequest userFeatureRequest = UserFeatureRequest.builder()
                                                    .featureName("someUserFeature")
                                                            .userName("user").build();
        mockMvc
                .perform(MockMvcRequestBuilders.put("/feature")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(userFeatureRequest)))
                .andExpect(status().isNoContent());

    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}