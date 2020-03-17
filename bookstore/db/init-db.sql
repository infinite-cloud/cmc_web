-- Initialize the webapp database --

---- Create types ----
CREATE TYPE STATUS AS ENUM (
	'IN_PROCESSING',
	'READY',
	'DELIVERED',
	'CANCELED'
);

---- Create tables ----

------ ACCOUNT ------
CREATE TABLE account (
	user_id SERIAL PRIMARY KEY,
	user_name VARCHAR(120) NOT NULL,
	home_address TEXT,
	phone_number VARCHAR(60),
	e_mail VARCHAR(120) UNIQUE CHECK (e_mail LIKE '_%@_%._%') NOT NULL,
	password_hash CHAR(44) NOT NULL, -- PBKDF2 hash
	is_admin BOOLEAN NOT NULL
);

------ PURCHASE ------
CREATE TABLE purchase (
	order_id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	order_date TIMESTAMP NOT NULL,
	delivery_address TEXT NOT NULL,
	delivery_date TIMESTAMP CHECK (delivery_date >= order_date) NOT NULL,
	total_price NUMERIC CHECK (total_price > 0.00) NOT NULL,
	order_status STATUS NOT NULL
);

------ ORDERED_BOOK ------
CREATE TABLE ordered_book (
	order_id INTEGER NOT NULL,
	book_id INTEGER NOT NULL,
	book_count INTEGER CHECK (book_count > 0) NOT NULL
);

------ PUBLISHER ------
CREATE TABLE publisher (
	publisher_id SERIAL PRIMARY KEY,
	publisher_name VARCHAR(120) NOT NULL
);

------ BOOK_AUTHOR ------
CREATE TABLE book_author (
	book_id INTEGER NOT NULL,
	author_id INTEGER NOT NULL
);

------ AUTHOR ------
CREATE TABLE author (
	author_id SERIAL PRIMARY KEY,
	author_name VARCHAR(120) NOT NULL
);

------ BOOK ------
CREATE TABLE book (
	book_id SERIAL PRIMARY KEY,
	book_name VARCHAR(240) NOT NULL,
	publisher_id INTEGER NOT NULL,
	publication_date DATE CHECK (publication_date <= NOW()) NOT NULL,
	genre_id INTEGER NOT NULL,
	page_count INTEGER CHECK (page_count > 0) NOT NULL,
	cover_type_id INTEGER NOT NULL,
	book_price NUMERIC CHECK (book_price > 0.00) NOT NULL,
	available_count INTEGER CHECK (available_count >= 0) NOT NULL,
	description TEXT NOT NULL
);

------ GENRE ------
CREATE TABLE genre (
	genre_id SERIAL PRIMARY KEY,
	genre_name VARCHAR(240) NOT NULL
);

------ COVER_TYPE ------
CREATE TABLE cover_type (
	cover_type_id SERIAL PRIMARY KEY,
	cover_type_name VARCHAR(240) NOT NULL
);

---- Set foreign keys ----

------ PURCHASE ------
ALTER TABLE purchase
	ADD FOREIGN KEY (user_id)
		REFERENCES account (user_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

------ ORDERED_BOOK ------
ALTER TABLE ordered_book
	ADD FOREIGN KEY (order_id)
		REFERENCES purchase (order_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD FOREIGN KEY (book_id)
		REFERENCES book (book_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

------ BOOK_AUTHOR ------
ALTER TABLE book_author
	ADD FOREIGN KEY (book_id)
		REFERENCES book (book_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD FOREIGN KEY (author_id)
		REFERENCES author (author_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

------ BOOK ------
ALTER TABLE book
	ADD FOREIGN KEY (publisher_id)
		REFERENCES publisher (publisher_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD FOREIGN KEY (genre_id)
		REFERENCES genre (genre_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD FOREIGN KEY (cover_type_id)
		REFERENCES cover_type (cover_type_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

