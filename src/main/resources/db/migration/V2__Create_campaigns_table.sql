create sequence campaigns_seq start with 1 increment by 50;

create table campaigns (
    id bigint not null, 
    name varchar(255), 
    player_schema jsonb, 
    primary key (id)
);

alter table players add column campaign_id bigint not null;

alter table players add constraint fk_campaign foreign key(campaign_id) references campaigns (id);