truncate table users cascade;
truncate table media cascade;

insert into users(id, email, password, time_created) values
    (200, 'john@email.com', 'password', '2024-06-04T15:03:03.792009700'),
    (201, 'mary@email.com', 'password', '2024-06-04T15:03:03.792009700'),
    (202, 'chichi@email.com', 'password', '2024-06-04T15:03:03.792009700'),
    (203, 'solomon@email.com', 'password', '2024-06-04T15:03:03.792009700');

insert into media(id, url, description, category, time_created, uploader_id) values
    (100, 'https://www.cloudinary.com/media1', 'media 1', 'ACTION', '2024-06-04T15:03:03.792009700', 200),
    (101, 'https://www.cloudinary.com/media2', 'media 2', 'ACTION', '2024-06-04T15:03:03.792009700', 201),
    (102, 'https://www.cloudinary.com/media3', 'media 3', 'HORROR', '2024-06-04T15:03:03.792009700', 200),
    (103, 'https://www.cloudinary.com/media4', 'media 4', 'COMEDY', '2024-06-04T15:03:03.792009700', 200);
