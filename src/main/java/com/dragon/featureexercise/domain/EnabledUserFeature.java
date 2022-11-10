package com.dragon.featureexercise.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EnabledUserFeature {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "username must be provided")
    private String username;

    @OneToMany(mappedBy = "enabledUserFeature")
    private List<UserFeature> enabledFeatures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserFeature> getEnabledFeatures() {
        return enabledFeatures;
    }

    public void setEnabledFeatures(List<UserFeature> enabledFeatures) {
        this.enabledFeatures = enabledFeatures;
    }
}
