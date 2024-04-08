create sequence players_seq start with 1 increment by 50;

create table players (
    points integer, 
    id bigint not null, 
    name varchar(255), 
    document jsonb, 
    primary key (id)
);