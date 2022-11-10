DROP TABLE IF EXISTS enabled_user_feature;
DROP TABLE IF EXISTS global_feature;
DROP TABLE IF EXISTS user_feature;

CREATE TABLE enabled_user_feature (
    id INTEGER NOT NULL,
    username VARCHAR(50),
    enabled_features VARCHAR(100) NOT NULL
);

CREATE TABLE global_feature (
    id INTEGER NOT NULL,
    name VARCHAR(50),
    is_enabled VARCHAR
);

CREATE TABLE user_feature (
    id INTEGER NOT NULL,
    name VARCHAR(50),
    enabled_user_feature VARCHAR
);



