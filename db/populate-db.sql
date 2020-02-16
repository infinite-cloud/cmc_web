-- Populate the webapp database --

SET DATESTYLE TO "DMY";

------ ACCOUNT ------
-- The password hashes have been generated using the following:
	-- Message: passwordi, where i is from 0 to 4;
	-- Salt: 4E854A6144D245C86E29D0845355B427;
	-- Iterations: 1000;
	-- Key length: 32 Bytes;
INSERT INTO account (user_name, home_address, phone_number, e_mail,
		password_hash, is_admin) VALUES
	('Иванов Иван Иванович', NULL, NULL,
		'admin@namelessbookstore.com',
		't7z6kgTsq+VdcsxNGWb4o0M2e/IwpOc3oczibsS6+Aw=', TRUE), -- i == 0
	('Владимир Петров', NULL, '+74951234567',
		'vpetrov@firemailer.com',
		'N3shvtPsW4RRBz+KnWe62mej+Zk3k7X2xaWiZsE2M/A=', FALSE), -- i == 1
	('Александра', 'г. Москва, ул. Неизвестная, д. 1', '+74952345678',
		'alexandra@themailplace.org',
		'yAqiS1rTOkL1uP99F0UbbI/HcyyjTg6X/rbzUo22JYc=', FALSE), -- i == 2
	('Валерий', 'г. Москва, ул. Никакая, д. 1, кв. 1', '+74953456789',
		'valery@emailtestings.net',
		'JcD8PwCpJJiebV7NUWiP6nz660BDYjXb3sRha8gqBgg=', FALSE), -- i == 3
	('Попов Игорь', 'г. Москва, ул. Неопознанная, д. 1, корп. 1, кв. 1', NULL,
		'igor@themailalert.net',
		'oUsK9qUEI9JVVJ7jZMvwMF0sWk0FPhzlu++SrGnaG3A=', FALSE); -- i == 4

------ COVER_TYPE ------
INSERT INTO cover_type (cover_type_name) VALUES
	('Твёрдый переплёт'),
	('Мягкий переплёт'),
	('Твёрдая обложка'),
	('Мягкая обложка');

------ AUTHOR ------
INSERT INTO author (author_name) VALUES
	('Пушкин Александр Сергеевич'),
	('Достоевский Федор Михайлович'),
	('Маршак Самуил Яковлевич'),
	('Тихонов Андрей Николаевич'),
	('Самарский Александр Андреевич'),
	('Ницше Фридрих Вильгельм');

------ GENRE ------
INSERT INTO genre (genre_name) VALUES
	('Сказки'),
	('Поэзия'),
	('Классическая проза'),
	('Естественные науки'),
	('Философские науки');

------ PUBLISHER ------
INSERT INTO publisher (publisher_name) VALUES
	('АСТ'),
	('Наука'),
	('Лабиринт'),
	('Речь');

------ BOOK ------
INSERT INTO book (book_name, publisher_id, publication_date, genre_id,
		page_count, cover_type_id, book_price, available_count) VALUES
	('Евгений Онегин', 4, '01.01.2019', 2, 320, 3, 1487.00, 3),
	('Идиот', 4, '01.01.2017', 3, 536, 3, 1792.00, 5),
	('Двенадцать месяцев', 3, '01.01.2020', 1, 128, 3, 1776.00, 7),
	('Уравнения математической физики', 2, '01.01.2014', 4, 742, 1, 356.00, 1),
	('Так говорил Заратустра', 1, '01.01.2015', 5, 416, 2, 169.00, 12);

------ BOOK_AUTHOR ------
INSERT INTO book_author (book_id, author_id) VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4),
	(4, 5),
	(5, 6);

------ PURCHASE ------
INSERT INTO purchase (user_id, order_date, delivery_address, delivery_date,
		total_price, order_status) VALUES
	(2, '02.02.2020', 'г. Москва, ул. Некоторая, д. 1', '30.06.2020',
		1792.00, 'Собран'),
	(3, '05.01.2020', 'г. Москва, ул. Неизвестная, д. 1, кв. 1', '10.01.2020',
		1487.00, 'Поставлен'),
	(3, NOW(), 'г. Москва, ул. Неизвестная, д. 1, кв. 1', '07.05.2020',
		169.00, 'В обработке'),
	(4, '01.02.2020', 'г. Москва, ул. Никакая, д. 1, кв. 1', '02.02.2020',
		2132.00, 'Отменён');

------ ORDERED_BOOK ------
INSERT INTO ordered_book (order_id, book_id, book_count) VALUES
	(1, 2, 1),
	(2, 1, 1),
	(3, 5, 1),
	(4, 3, 1),
	(4, 4, 1);
