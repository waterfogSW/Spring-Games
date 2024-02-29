create table game
(
    id   binary(16)   not null,
    name varchar(255) not null,
    primary key (id)
);

insert game (id, name)
values (UUID_TO_BIN('018df3a8-7c53-78d3-a076-c5b3f5851080'), '매직더게더링');

insert game (id, name)
values (UUID_TO_BIN('018df3a8-7c53-78d3-a076-c5b3f5851081'), '유희왕');

insert game (id, name)
values (UUID_TO_BIN('018df3a8-7c53-78d3-a076-c5b3f5851082'), '포켓몬');
