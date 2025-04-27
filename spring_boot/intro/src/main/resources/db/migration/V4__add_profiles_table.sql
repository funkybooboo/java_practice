create table profiles
(
    id             bigint                 not null,
    bio            TEXT                   null,
    phone_number   varchar(15)            not null,
    date_of_birth  date                   not null,
    loyalty_points int unsigned default 0 not null,
    foreign key (id) references users (id)
);
