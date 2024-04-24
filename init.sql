CREATE SEQUENCE city_sequence START 1 INCREMENT 1;
CREATE SEQUENCE ipinfo_sequence START 1 INCREMENT 1;
CREATE SEQUENCE position_sequence START 1 INCREMENT 1;
CREATE SEQUENCE provider_sequence START 1 INCREMENT 1;

CREATE TABLE city (
    id BIGINT PRIMARY KEY DEFAULT nextval('city_sequence'),
    country VARCHAR(255) NOT NULL,
    country_code VARCHAR(2) NOT NULL,
    region VARCHAR(255) NOT NULL,
    region_name VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL
);

CREATE TABLE position (
    id BIGINT PRIMARY KEY DEFAULT nextval('position_sequence'),
    latitude NUMERIC(10, 8) NOT NULL,
    longitude NUMERIC(11, 8) NOT NULL
);

CREATE TABLE provider (
    id BIGINT PRIMARY KEY DEFAULT nextval('provider_sequence'),
    internet_service_provider VARCHAR(255) NOT NULL,
    organisation VARCHAR(255) NOT NULL,
    autonomous_system_name VARCHAR(255) NOT NULL
);

CREATE TABLE city_provider_relation (
    provider_id BIGINT NOT NULL,
    city_id BIGINT NOT NULL,
    PRIMARY KEY (provider_id, city_id),
    FOREIGN KEY (provider_id) REFERENCES provider(id),
    FOREIGN KEY (city_id) REFERENCES city(id)
);

CREATE TABLE ip_info (
    id BIGINT PRIMARY KEY DEFAULT nextval('ipinfo_sequence'),
    city_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    time_zone VARCHAR(255) NOT NULL,
    provider_id BIGINT NOT NULL,
    ip VARCHAR(255) NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(id),
    FOREIGN KEY (position_id) REFERENCES position(id),
    FOREIGN KEY (provider_id) REFERENCES provider(id)
);