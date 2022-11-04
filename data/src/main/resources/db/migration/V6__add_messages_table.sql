create table messages
(
    id              uuid primary key not null,
    chat_id         uuid             not null references chats on delete cascade,
    created_at      varchar(100)     not null,
    user_id         uuid             not null references users on delete cascade,
    "text"          varchar(500)     not null
);