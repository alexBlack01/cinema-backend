create table comments (
      id              uuid primary key not null,
      episode_id      uuid             not null references episodes on delete cascade,
      created_at      varchar(100)     not null,
      author_id       uuid             not null references users on delete cascade,
      "text"          varchar(500)     not null
);