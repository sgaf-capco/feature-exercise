package com.dragon.featureexercise.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
public class UserFeature {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Name must be provided")
    private String name;

    @ManyToOne
    private EnabledUserFeature enabledUserFeature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnabledUserFeature getEnabledUserFeature() {
        return enabledUserFeature;
    }

    public void setEnabledUserFeature(EnabledUserFeature enabledUserFeature) {
        this.enabledUserFeature = enabledUserFeature;
    }
}
