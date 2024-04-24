create table city (
    id bigint not null,
    country varchar(255),
    country_code varchar(255),
    name varchar(255),
    region varchar(255),
    region_name varchar(255),
    zip varchar(255),
    primary key (id)
);
    
create table city_provider_relation (
    city_id bigint not null,
    provider_id bigint not null,
    primary key (city_id, provider_id)
);
    
create table ip_info (
    city_id bigint,
    id bigint not null,
    position_id bigint,
    provider_id bigint,
    ip varchar(255),
    time_zone varchar(255),
    primary key (id)
);
    
create table position (
    latitude float(53),
    longitude float(53),
    id bigint not null,
    primary key (id)
);
    
create table provider (
    id bigint not null,
    autonomous_system_name varchar(255),
    internet_service_provider varchar(255),
    organisation varchar(255),
    primary key (id)
);