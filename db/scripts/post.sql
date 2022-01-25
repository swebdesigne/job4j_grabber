create table if not exists post (
    id serial primary key,
    name varchar(150),
    text text,
    link varchar(255) unique not null,
    created timestamp
);