create table user_dislikes
(
    constraint PK_UserMovie_user_id_movie_id primary key (user_id, movie_id),
    user_id       uuid             not null references users on delete cascade,
    movie_id      uuid             not null references movies on delete cascade
);