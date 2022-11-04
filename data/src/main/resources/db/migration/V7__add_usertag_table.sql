create table users_tags
(
    constraint PK_UserTag_user_id_tag_id primary key (user_id, tag_id),
    user_id                     uuid             not null references users on delete cascade,
    tag_id                      uuid             not null references tags on delete cascade
);