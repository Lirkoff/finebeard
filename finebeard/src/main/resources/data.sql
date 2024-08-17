insert into users (id, active, email, first_name, last_name, password)
values
    (1, 1, 'master@example.com', 'Master', 'Masterov', 'ee9dae324dda56b899f9072f21f1c05ff7684b21ba6e7c3e4e459cfe27fc538a29eef78afc08e871006483e2848b33ab');


insert into roles (id, role)
VALUES
    (1, 'MASTER'),
    (2, 'ADMIN'),
    (3, 'USER');

insert into users_roles (user_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3);

