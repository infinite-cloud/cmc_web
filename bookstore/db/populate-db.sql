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
        page_count, cover_type_id, book_price, available_count,
        description, image_name) VALUES
    ('Евгений Онегин', 4, '01.01.2019', 2, 320, 3, 1487.00, 0,
        'Роман А. С. Пушкина "Евгений Онегин" приводится в 
        иллюстрациях известного живописца XX века Лидии Тимошенко. 
        Главное пушкинское произведение стало единственной книгой, к 
        которой Тимошенко обратилась как иллюстратор. Это был 
        эксперимент (от которого, к слову, многие коллеги ее 
        отговаривали), всецело захвативший художницу… на двадцать лет.
         В итоге она создала две разные серии иллюстраций: одну - 
        маслом, в технике гризайль, в начале послевоенного периода 
        (публикуется в настоящем издании), вторую - в 1960-е годы, в 
        цветной литографии. И по сей день работы Лидии Тимошенко 
        считаются лучшим воплощением образов, описанных в романе.
         По мнению известного историка искусства Л. А. Чегодаева, 
        добивавшегося вместе с коллегами издания "Евгения Онегина", 
        оформленного гризайлями Л. Тимошенко, художница подошла к 
        великому творению Пушкина "очень психологически обостренно 
        и очень драматически". Иллюстрации Тимошенко помогают 
        воспринимать роман как очень грустное повествование о трагедии
         одиночества, об истории непростительной ошибки, разрушившей 
        судьбы обоих главных героев, о невольном и горестном разладе 
        двух замечательных и значительных людей в чуждой им обоим 
        окружающей исторической среде…
         У Тимошенко получилась самостоятельная живописная поэма, 
        во многом дополняющая роман Пушкина. Судя по дневниковым 
        записям, по переписке с друзьями и их воспоминаниям, 
        публикуемым в настоящем издании, именно к созданию 
        "энциклопедии русской жизни в живописи" и стремилась художница
         в своем "Онегине".',
        'onegin.png'),
    ('Идиот', 4, '01.01.2017', 3, 536, 3, 1792.00, 5,
        '"Сколько силы! Сколько мест чудесных! Как хорош "Идиот"! 
        Да и все лица очень ярки, пестры - только освещены-то 
        электрическим огнем, при котором самое обыкновенное, 
        знакомое лицо, обыкновенные цвета получают сверхъестественный
         блеск, и их хочется как бы заново рассмотреть". Таким был 
        один из первых отзывов на роман Ф. М. Достоевского, вернее, 
        на первую его часть, опубликованную в 1868 году в журнале 
        "Русский вестник". Дальше будут и восторженные и негативные 
        рецензии, и сомнения самого писателя в успехе "Идиота", и 
        признание публики… А ровно через сто лет к роману обратится 
        художник Виталий Горяяев и как будто действительно осветит 
        героев Достоевского слишком ярким электрическим огнем. 
        Нервными, изломанными, спутанными линиями он создаст темные 
        и светлые лики-маски, сформирует фигуры и убедит их 
        взаимодействовать. Рисунки Горяева заставят почувствовать 
        напряжение идей и страстей, саму суть романа Достоевского, 
        так оригинально и свободно переданную карандашом на бумаге.',
        'idiot.png'),
    ('Двенадцать месяцев', 3, '01.01.2020', 1, 128, 3, 1776.00, 7,
        'Интерактивное иллюстрированное издание пьесы С. Маршака 
        "Двенадцать месяцев" - настоящая книга-театр, в которой 
        есть сцена, занавес, актеры и, конечно, объемные театральные 
        декорации. Читатель будто сидит в первом ряду зрительного 
        зала: перед ним встает, сверкая праздничными огнями, 
        королевский дворец, бушует вьюга, трубят с балкона герольды. 
        Итак, поднимается занавес - и представление начинается!',
        'twelve_months.png'),
    ('Уравнения математической физики', 2, '01.01.2014', 4, 742, 1, 356.00, 1,
        'В книге рассматриваются задачи математической физики, 
        приводящие к уравнениям с частными производными. Расположение
         материала соответствует основным типам уравнений.
        Изучение каждого типа уравнений начинается с простейших 
        физических задач, приводящих к уравнениям рассматриваемого 
        типа. Особое внимание уделяется математической постановке 
        задач, строгому изложению решения простейших задач и 
        физической интерпретации результатов. 
        В каждой главе помещены задачи и примеры.',
        'equations.jpg'),
    ('Так говорил Заратустра', 1, '01.01.2015', 5, 416, 2, 169.00, 12,
        'Трактат "Так говорил Заратустра" называют ницшеанской 
        Библией.
        В нем сформулирована излюбленная идея Ницше - идея 
        Сверхчеловека, который является для автора нравственным 
        образцом, смыслом существования, тем, к чему нужно 
        стремиться.
        Человек же - лишь мост между животным и Сверхчеловеком.
        Необычная форма - поэтичная, афористичная - не совсем 
        соответствует нашим представлениям о философском трактате. 
        Однако, вчитываясь, мы улавливаем ход мысли автора, все 
        глубже проникаемся его идеями и убеждениями...',
        'zarathustra.jpg');

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
        1792.00, 'READY'),
    (3, '05.01.2020', 'г. Москва, ул. Неизвестная, д. 1, кв. 1', '10.01.2020',
        1487.00, 'DELIVERED'),
    (3, NOW(), 'г. Москва, ул. Неизвестная, д. 1, кв. 1', '07.05.2020',
        169.00, 'IN_PROCESSING'),
    (4, '01.02.2020', 'г. Москва, ул. Никакая, д. 1, кв. 1', '02.02.2020',
        2132.00, 'CANCELED');

------ ORDERED_BOOK ------
INSERT INTO ordered_book (order_id, book_id, book_count) VALUES
    (1, 2, 1),
    (2, 1, 1),
    (3, 5, 1),
    (4, 3, 1),
    (4, 4, 1);
