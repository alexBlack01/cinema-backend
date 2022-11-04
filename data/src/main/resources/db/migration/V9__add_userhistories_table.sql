create table user_histories
(
    id              uuid primary key not null,
    user_id         uuid             not null references users on delete cascade,
    episode_id      uuid             not null references episodes on delete cascade,
    "time"          varchar(100)     not null
);