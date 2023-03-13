create table episodes
(
    id              uuid primary key not null,
    movie_id        uuid             not null references movies on delete cascade,
    "name"          varchar(150)     not null,
    description     varchar(500)     not null,
    director        varchar(150)     not null,
    "year"          varchar(10)      not null,
    runtime         varchar(50)      not null,
    preview         varchar(150)     default null,
    file_path       varchar(150)     not null
);

create table stars
(
    id              uuid primary key not null,
    "name"          varchar(250)     not null
);

create table episode_images
(
    id              uuid primary key not null,
    episode_id      uuid             not null references episodes on delete cascade,
    url             varchar(150)     not null
);

create table episodes_stars
(
    constraint PK_EpisodeStar_episode_id_star_id primary key (episode_id, star_id),
    episode_id      uuid             not null references episodes on delete cascade,
    star_id         uuid             not null references stars on delete cascade
);