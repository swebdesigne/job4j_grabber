create database job;

insert into company (id, name) values (1, 'WebisGroup');
insert into company (id, name) values (2, 'Impulse');
insert into company (id, name) values (3, 'Protech');
insert into company (id, name) values (4, 'Technoline');
insert into company (id, name) values (5, 'Industrial');
insert into company (id, name) values (6, 'Pixel');
insert into company (id, name) values (7, 'SoftE');

insert into person (id, name, company_id) values (1, 'Igor', 1);
insert into person (id, name, company_id) values (2, 'Boris', 2);
insert into person (id, name, company_id) values (3, 'Alina', 3);
insert into person (id, name, company_id) values (4, 'Fedor', 4);
insert into person (id, name, company_id) values (5, 'Galina', 5);
insert into person (id, name, company_id) values (6, 'Ivan', 6);
insert into person (id, name, company_id) values (7, 'Vladimir', 1);
insert into person (id, name, company_id) values (8, 'Sergey', 1);
insert into person (id, name, company_id) values (9, 'Nasya', 7);
insert into person (id, name, company_id) values (10, 'Vadim', 7);
insert into person (id, name, company_id) values (11, 'Pasha', 7);


-- имена всех person, которые не состоят в компании с id = 5;
select * from person where company_id in (select id from company where id <> 5);

-- название компании для каждого человека
select p.name, c.name from person as p inner join company as c on p.company_id = c.id;

-- Необходимо выбрать название компании с максимальным количеством человек + количество человек в этой компании
select c.name, count(p.id) as count_person from company as c
left join person as p on c.id = p.company_id
group by c.name having count(p.id) = (select count(id) as c from person group by company_id order by c desc limit 1);