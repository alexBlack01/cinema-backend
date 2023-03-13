create table movies
(
    id               uuid primary key not null,
    "name"           varchar(150)     not null,
    description      varchar(500)     not null,
    age              varchar(5)       not null,
    poster           varchar(200)     default null,
    background_image varchar(200)     default null,
    foreground_image varchar(200)     default null,
    created_at       timestamp        not null            default (now() at time zone 'utc'),
    view_count       integer          default 0
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
    url             varchar(200)     not null
);

create table tags_movies
(
    constraint PK_TagMovie_tag_id_movie_id primary key (tag_id, movie_id),
    tag_id          uuid             not null references tags on delete cascade,
    movie_id        uuid             not null references movies on delete cascade
);