create table if not exists grabber (
    id serial primary key,
    name varchar(150) unique not null,
    text text,
    link varchar(100),
    created timestamp
);