create table movies
(
    id              uuid primary key not null,
    name            varchar(150)     not null,
    description     varchar(500)     not null,
    age             varchar(5)       not null,
    poster          varchar(200)     not null,
    background_image varchar(200)    not null,
    foreground_image varchar(200)    not null
);

create table tags
(
    id              uuid primary key not null,
    tag_name        varchar(150)     not null,
    category_name   varchar(150)     not null
);

create table images
(
    id              uuid primary key not null,
    movie_id        uuid             not null references movies on delete cascade,
    url             varchar(150)     not null
);

create table tags_movies
(
    tag_id          uuid             not null references tags on delete cascade,
    movie_id        uuid             not null references movies on delete cascade
);