create table game_card
(
    id            binary(16)     not null,
    game_id       binary(16)     not null,
    member_id     binary(16)     not null,
    title         varchar(255)   not null,
    serial_number bigint         not null,
    price         decimal(10, 2) not null,
    primary key (id),
    unique (game_id, serial_number),
    index (member_id)
);
