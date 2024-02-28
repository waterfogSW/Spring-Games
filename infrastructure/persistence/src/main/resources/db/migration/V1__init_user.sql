create table member
(
    id                    binary(16)   not null,
    name                  varchar(255) not null,
    email                 varchar(255) not null,
    registered_date       datetime(6)  not null,
    level                 varchar(255) not null,
    game_card_total_count int          not null,
    game_card_total_price int          not null,
    primary key (id),
    unique key user_email_uindex (email)
) engine = InnoDB
  default charset = utf8mb4;
