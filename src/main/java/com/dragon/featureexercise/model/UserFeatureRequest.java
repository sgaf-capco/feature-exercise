package com.dragon.featureexercise.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserFeatureRequest {

    private String featureName;
    private String userName;
}
