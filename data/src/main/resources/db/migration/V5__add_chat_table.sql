create table chats
(
    id              uuid primary key not null,
    "name"          varchar(150)     not null
);

create table users_chats
(
    constraint PK_UserChat_user_id_chat_id primary key (user_id, chat_id),
    user_id                     uuid             not null references users on delete cascade,
    chat_id                     uuid             not null references chats on delete cascade
);