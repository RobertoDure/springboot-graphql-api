create table student
(
    id               bigint auto_increment
        primary key,
    address          varchar(255) not null,
    date_of_birth    datetime     not null,
    email            varchar(255) not null,
    gender           varchar(255) not null,
    id_classroom     int          not null,
    name             varchar(255) not null,
    nationality      varchar(255) not null,
    guardian_contact varchar(255) not null,
    guardian_name    varchar(255) not null,
    id_teacher       bigint       not null
);
