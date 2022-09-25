create table avatars
(
    id              uuid primary key not null,
    url             varchar(150)     not null
);

create table users
(
    id          uuid primary key not null,
    email       varchar(255)     not null,
    password    varchar(255)     not null,
    first_name  varchar(100)     not null,
    last_name   varchar(100)     not null,
    avatar_id   uuid             default null references avatars on delete cascade
);

create table auth_tokens
(
    id                       uuid primary key not null,
    refresh_token            varchar(256)     not null,
    refresh_token_expires_in bigint           not null,
    user_id                  uuid             not null references users on delete cascade
);

create table user_roles
(
    id      uuid primary key not null,
    role    varchar(50)      not null,
    user_id uuid             not null references users on delete cascade
);