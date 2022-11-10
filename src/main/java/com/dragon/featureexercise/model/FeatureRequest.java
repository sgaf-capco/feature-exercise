package com.dragon.featureexercise.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class FeatureRequest {

    private String featureName;
    private FeatureType featureType;
}
