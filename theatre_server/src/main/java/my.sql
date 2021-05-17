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

