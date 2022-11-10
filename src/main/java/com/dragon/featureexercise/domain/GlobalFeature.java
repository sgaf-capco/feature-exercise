package com.dragon.featureexercise.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class GlobalFeature {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Please provide a name")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name="is_enabled")
    private String isEnabled;

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

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}
