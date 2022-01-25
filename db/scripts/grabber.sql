create table if not exists grabber (
    id serial primary key,
    name varchar(150),
    text text,
    link varchar(100) unique not null,
    created timestamp
);