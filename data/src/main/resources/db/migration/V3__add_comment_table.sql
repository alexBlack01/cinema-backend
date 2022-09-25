create table comments (
      id              uuid primary key not null,
      episode_id      uuid             not null references episodes on delete cascade,
      created_at      varchar(100)     not null,
      author_name     varchar(256)     not null,
      author_avatar   varchar(300)     default null,
      "text"          varchar(500)     not null
);