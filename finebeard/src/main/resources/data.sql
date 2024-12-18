# insert into users (id, active, email, first_name, last_name, password)
# values (2, true, 'user2@example.com', 'FirstName2', 'LastName2', 'password2'),
#        (3, true, 'user3@example.com', 'FirstName3', 'LastName3', 'password3'),
#        (4, true, 'user4@example.com', 'FirstName4', 'LastName4', 'password4'),
#        (5, true, 'user5@example.com', 'FirstName5', 'LastName5', 'password5'),
#        (6, true, 'user6@example.com', 'FirstName6', 'LastName6', 'password6'),
#        (7, true, 'user7@example.com', 'FirstName7', 'LastName7', 'password7'),
#        (8, true, 'user8@example.com', 'FirstName8', 'LastName8', 'password8'),
#        (9, true, 'user9@example.com', 'FirstName9', 'LastName9', 'password9'),
#        (10, true, 'user10@example.com', 'FirstName10', 'LastName10', 'password10'),
#        (11, true, 'user11@example.com', 'FirstName11', 'LastName11', 'password11');


# insert into users_roles (user_id, role_id)
# values (2, 3),
#        (3, 3),
#        (3, 2),
#        (4, 3),
#        (5, 3),
#        (5, 2),
#        (6, 3),
#        (7, 3),
#        (7, 2),
#        (8, 3),
#        (9, 3),
#        (9, 2),
#        (10, 3),
#        (11, 3);


insert into categories (id, image_url, name)
VALUES (1, '../images/categories/Oils.png', 'BEARD_OILS'),
       (2, '../images/categories/Balms.png', 'BEARD_BALMS'),
       (3, '../images/categories/Shampoos and Washes.png', 'BEARD_SHAMPOOS_AND_WASHES'),
       (4, '../images/categories/Conditioners.png', 'BEARD_CONDITIONERS'),
       (5, '../images/categories/Combs and Brushes.png', 'COMBS_AND_BRUSHES'),
       (6, '../images/categories/Trimmers and Scissors.png', 'TRIMMERS_AND_SCISSORS'),
       (7, '../images/categories/Waxes.png', 'WAXES'),
       (8, '../images/categories/Kits and Gift sets.png', 'KITS_AND_GIFT_SETS'),
       (9, '../images/categories/Growth products.png', 'GROWTH_PRODUCTS'),
       (10, '../images/categories/Shaving creams and Gels.png', 'SHAVING_CREAMS_AND_GELS'),
       (11, '../images/categories/Aftershaves and Post-Shave care.png', 'AFTERSHAVES_AND_POST_SHAVE_CARE'),
       (12, '../images/categories/Accessories.png', 'ACCESSORIES');

insert into brands (id, name)
values (1, 'Beardbrand'),
       (2, 'Honest_Amish'),
       (3, 'Mountaineer_Brand'),
       (4, 'The_Art_of_Shaving'),
       (5, 'Viking_Revolution'),
       (6, 'Jack_Black'),
       (7, 'Bossman');



INSERT INTO blog_articles (id, author, content_path, image_url, title, uuid)
VALUES (1, 'Author 1', 'templates/articles/article-content.txt', '/images/4.webp',
        'First Blog Title',
        '123e4567-e89b-12d3-a456-426614174000'),
       (2, 'Author 2', 'templates/articles/article-content.txt', '/images/4.webp',
        'Second Blog Title',
        '223e4567-e89b-12d3-a456-426614174001'),
       (3, 'Author 3', 'templates/articles/article-content.txt', '/images/4.webp',
        'Third Blog Title',
        '323e4567-e89b-12d3-a456-426614174002'),
       (4, 'Author 4', 'templates/articles/article-content.txt', '/images/4.webp',
        'Fourth Blog Title',
        '423e4567-e89b-12d3-a456-426614174003'),
       (5, 'Author 5', 'templates/articles/article-content.txt', '/images/4.webp',
        'Fifth Blog Title',
        '523e4567-e89b-12d3-a456-426614174004');


insert into products (id, description, image_url, price, uuid, brand_id, category_id)
VALUES (1, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '20.00',
        '923cd63e-59f3-483e-818d-691836beb729', 2, 1),
       (2, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '20.00',
        '0a90f164-3ab7-4ede-9370-1c469297374b', 2, 1),
       (3, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '20.00',
        '6b8f1f0e-1b8b-4a8a-a8b6-47a339e4e8a1', 2, 1),
       (4, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '25.00',
        '1e3104e2-c3ce-4ac8-99e2-4f6f3d090bf6', 3, 1),
       (5, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '30.00',
        'a12e4b91-ea5c-4a7d-ade7-705088e5db0b', 3, 1),
       (6, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '35.00',
        '682484e0-478f-4acf-bc9e-b32ac38bcb71', 3, 1),
       (7, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '40.00',
        '29fc7962-dc0c-4441-b0aa-60f6fa1f8fda', 1, 2),
       (8, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '45.00',
        '7086ad09-2d37-4dd2-891e-489bc0672b5f', 1, 2),
       (9, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '50.00',
        '37c68a90-242e-4f84-b33f-d12baf15d4d6', 2, 2),
       (10, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '55.00',
        'b83065da-f264-4a34-9125-20d0274e1e89', 2, 2),
       (11, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '60.00',
        '5f3b2247-15f5-4e71-8e8c-7fac842e36ac', 3, 2),
       (12, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '65.00',
        '2f2feeeb-68b6-4d6e-be37-751a2facda4f', 1, 3),
       (13, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '70.00',
        'b17cf599-cba0-4d9a-8361-5a32fce1a1fd', 1, 3),
       (14, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '75.00',
        'f07d2508-ab45-4b2c-9c34-8553d77ce2ca', 2, 3),
       (15, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '80.00',
        '9a74b5a3-f499-4636-bced-76d84eedd484', 2, 3),
       (16, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '85.00',
        '64254979-08ba-4ff0-97ef-347374d08542', 3, 3),
       (17, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '90.00',
        '717c9cf0-1bad-47e9-bf0d-10f99f0e432f', 1, 4),
       (18, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '95.00',
        'abc0b1e9-d1a2-4885-ab2d-7f41d46488e0', 1, 4),
       (19, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '100.00',
        'fc50db76-1497-4897-ba0d-08eb8ad1b1e9', 2, 4),
       (20, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '105.00',
        'b8a6c74d-91ae-41e5-8650-cf1c5180abf7', 2, 4),
       (21, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '110.00',
        'f525d8c7-8044-4ec3-873b-a6b693093a1c', 3, 4),
       (22, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '115.00',
        '5976aea2-e594-451e-b51d-bc9cc6fa40b7', 1, 5),
       (23, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '120.00',
        '0239f617-20fe-4ea4-bd12-7f35a3e6804f', 1, 5),
       (24, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '125.00',
        '828ade15-c724-4ca1-83b2-412f8edd7110', 2, 5),
       (25, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '130.00',
        'a6ba5dee-619b-49fe-b533-46f0e5294943', 2, 5),
       (26, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/2.webp', '135.00',
        '94ff0434-10e8-4d93-8a05-d1ad56661fc1', 3, 5),
       (27, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '140.00',
        '118f9833-7f6b-462e-8ea7-e7c870c1a2d4', 1, 6),
       (28, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '145.00',
        '4a06ea26-2d2d-4681-9635-1844be39310f', 1, 6),
       (29, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '150.00',
        '70d3c45f-fc13-490a-82da-7d723cfe5286', 2, 6),
       (30, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '155.00',
        'd613b5cd-8ee2-4f9d-85ef-60c8d85fb603', 2, 7),
       (31, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '160.00',
        '90cccb59-4106-41e5-9c70-a89e162cd238', 3, 7),
       (32, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '165.00',
        '96de6024-9d58-4371-a520-2a69c8a19778', 3, 8),
       (33, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '170.00',
        '62d41c89-bf2a-430f-a9fc-b04a140cf721', 1, 8),
       (34, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '175.00',
        '0177d4f6-a7ee-4824-9e28-c3c0bc1a279f', 1, 9),
       (35, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '180.00',
        'a5c7c1be-7de2-4dbd-a1cf-3e972234f9dd', 2, 9),
       (36, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '185.00',
        'c4f018c9-4b2e-4928-bf00-79f0d9c1217a', 2, 10),
       (37, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '190.00',
        'a139fb5c-2339-414f-ac96-8f70da2e1a65', 3, 10),
       (38, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '195.00',
        '159ee214-9588-4dcb-a084-1cf6652ce02f', 3, 11),
       (39, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '200.00',
        '5f97fc3f-12d6-40f8-8065-57e4e874fb63', 1, 11),
       (40, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '205.00',
        '6a270e27-6f44-4298-85af-1913bc5c5aeb', 1, 12),
       (41, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '210.00',
        '7231b8cb-a310-4569-a741-91de68b89cf4', 2, 12),
       (42, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '215.00',
        '98ba4e94-4145-41b3-9d38-679cb42b02c0', 2, 12),
       (43, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '220.00',
        '4fb3803f-a0f2-4ea6-a4bc-9d5e99f8aa1f', 1, 12),
       (44, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '225.00',
        '92d7284d-23e7-4c33-9ed9-d217bc946d3c', 2, 12),
       (45, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '230.00',
        'a3b49c79-7b07-496a-9d77-22488aeb9089', 3, 12),
       (46, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '235.00',
        '8f7713d0-8747-4ad7-95e3-b9184fa2b3db', 1, 12),
       (47, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '240.00',
        '6014e8c1-94fa-4b7a-8fcc-e9257c45641b', 2, 12),
       (48, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '245.00',
        '88c5e4e7-279f-44e9-b7b8-dc12755302df', 3, 12),
       (49, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '250.00',
        '2702c542-7d40-4b6d-8bdf-d0b8986f3b2b', 1, 12),
       (50, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '255.00',
        '1b1fdc6a-324e-4cad-ba0e-6bcc5050ae42', 2, 12),
       (51, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '260.00',
        '91cb33e8-bdd0-4cef-8229-5b8cf847d446', 3, 12),
       (52, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '265.00',
        'f4bc6f1b-3c89-4daf-9c8a-342110c935fd', 1, 12),
       (53, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '270.00',
        '1cfaa199-74c5-4741-bbe7-df6d6206c6b8', 2, 12),
       (54, 'Lorem ipsum dolor sit amet, consectetur adipisc.', '/images/3.webp', '275.00',
        '7d863da2-0db4-4965-b10e-efb4efcbf2cc', 3, 12);