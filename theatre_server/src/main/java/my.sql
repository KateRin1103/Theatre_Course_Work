# поиск сеансов по дате


# >>> поиск сеансов по дате и времени

# поиск броней по id сеанса
SELECT booking.*
FROM booking
WHERE seance_id = 1;

# >>> поиск всех сеансов по посетителю
# сначала в таблице с юзерами ищет его id,
# по id_user ищет сеансы
SELECT booking.*, user.`id` AS `user_id`, user.`login` AS `login`
FROM booking
         INNER JOIN user ON (booking.`user_id` = user.`id`)
WHERE booking.`user_id` = 1;


# >>> поиск сеансов по названию спектакля
# сначала в таблице со спектаклями ищет его id,
# затем в сеансах по spectacle_id ищет спектакли(дату и время выводим)

# поиск занятых мест на конкретный сеанс (таблицы booking)

# поиск

# объединение пользователей с сеансом
# сначала в таблице с юзерами ищет его id,
# по id_user ищет сеансы
#СЛОЖНААААААААААААААА

# поиск в сеансах по id спектакля
SELECT seance.*, spectacle.`id` AS `spectacle_id`, spectacle.title AS `title`
FROM seance
         INNER JOIN spectacle ON (seance.`spectacle_id` = spectacle.`id`)
WHERE seance.`spectacle_id` = 1;

SELECT u.login, sp.title, se.time, se.date, p.place, p.row
FROM booking b
         INNER JOIN place p on b.place_id = p.id
         INNER JOIN user u on b.user_id = u.id
         INNER JOIN seance se on b.seance_id = se.id
         INNER JOIN spectacle sp on se.spectacle_id = sp.id;

SELECT s.title, se.date, se.time
FROM seance se
         INNER JOIN spectacle s on se.spectacle_id = s.id;

SELECT id
FROM spectacle
WHERE title = 'Шрэк';

SELECT id
FROM spectacle
WHERE title = 'Шрэк';

SELECT id
FROM `seance`
WHERE `date` = '2021-05-22'
  AND `time` = '16:45:00'
  AND `spectacle_id` = 2;
#сеанс 15
#спектакль 2
#место 2
#юзер 1

SELECT id
FROM `place`
WHERE `place` = '2'
  AND `row` = '1';
SELECT id
FROM `user`
WHERE `login` = 'katerin';


SELECT COUNT(date) AS count
FROM seance
WHERE date > '2021-05-01'
  AND date < '2021-06-01';

SELECT id
FROM place
WHERE `row` = 1
  AND `place` = 1;

# возвращает количество купленных билетов на спектакль за месяц
SELECT COUNT(place_id) AS count
FROM booking
         INNER JOIN seance s on booking.seance_id = s.id
         INNER JOIN spectacle s2 on s.spectacle_id = s2.id
WHERE title = 'Шрэк'
  AND date > '2021-04-01'
  AND date < '2021-05-01';

select se.*, (count(distinct s.title) * 15)
from seance se
         inner join spectacle s on se.spectacle_id = s.id
where title = 'Шрэк'
  and date between '2021-04-01' and '2021-05-01';

select distinct s.id, s.title
from seance
         inner join spectacle s on seance.spectacle_id = s.id;

#INSERT INTO statistics (goal)
select (COUNT(MONTH(s2.date) * 15)), s.spectacle_id, title
from spectacle
         inner join seance s on spectacle.id = s.spectacle_id
         inner join statistics s2 on spectacle.id = s2.spectacle_id
WHERE MONTH(s.date) = MONTH(s2.date);

insert into statistics(goal)
VALUES ('?');

select (COUNT(MONTH(statistics.date) * 15)) as numbers, statistics.spectacle_id, title
from statistics
         inner join spectacle s3 on statistics.spectacle_id = s3.id
         inner join seance s4 on s3.id = s4.spectacle_id
WHERE MONTH(statistics.date) = MONTH(s4.date);

#по дате найти кол-во подходящих спектаклей

select COUNT((MONTH(date)))
from seance
where month(date) = month(?)
  and spectacle_id = '?';

select count(booking.id)
from booking
         inner join seance s on s.id = booking.seance_id
         inner join spectacle s2 on s.spectacle_id = s2.id
where month(s.date) = 2
  and s.id = booking.seance_id
  and s.spectacle_id = s2.id
  AND title = 'Шрэк';

select count(booking.id)
from booking
         inner join seance s on s.id = booking.seance_id
         inner join spectacle s2 on s.spectacle_id = s2.id
where month(s.date) = 2
  and s.id = booking.seance_id
  and s.spectacle_id = s2.id
  AND title = 'Шрэк';


select ifnull(result / goal, 0.000) as eff
from statistics;

insert into statistics(`efficiency`)
select (`result` / `goal`)
from statistics;

update statistics
set efficiency='?'
where id = ?;

select sum(efficiency)
from statistics
where spectacle_id = 1;

insert into results (spectacle_id, specific_efficiency) value (?, ?);

select max(risk), title
from results
         inner join spectacle s on s.id = results.spectacle_id;


select min(risk), s.title
from results
         inner join spectacle s on s.id = results.spectacle_id
group by spectacle_id;

select max(risk), s.title
from results
         inner join spectacle s on s.id = results.spectacle_id
group by spectacle_id;

select title
from results
         inner join spectacle s on results.spectacle_id = s.id
where risk = (select max(risk) from results);

select title
from results
         inner join spectacle s on results.spectacle_id = s.id
where risk = (select min(risk) from results);

select *
from statistics
         inner join spectacle s on statistics.spectacle_id = s.id;

select `row`, `place`, seance_id, place_id
from booking
         inner join seance s on booking.seance_id = s.id
         inner join place p on booking.place_id = p.id
where seance_id = 10;

select *, title
from statistics
         inner join spectacle s on statistics.spectacle_id = s.id;

select *, title
from results
         inner join spectacle s on results.spectacle_id = s.id;

select id
from seance
inner join spectacle s on seance.spectacle_id = s.id
where title=

select place_id
from booking
         inner join place p on p.id = booking.place_id
where seance_id = 17;

