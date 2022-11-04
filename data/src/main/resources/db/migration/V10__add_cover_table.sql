create table cover
(
    id               uuid primary key not null,
    movie_id         uuid             not null references movies on delete cascade,
    background_image varchar(200) default null,
    foreground_image varchar(200) default null
);