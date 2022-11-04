create table collections
(
    id              uuid primary key not null,
    user_id         uuid             not null references users on delete cascade,
    "name"          varchar(300)     not null
);

create table collections_movies
(
    constraint PK_CollectionMovie_collection_id_movie_id primary key (collection_id, movie_id),
    collection_id                uuid             not null references collections on delete cascade,
    movie_id                     uuid             not null references movies on delete cascade
);