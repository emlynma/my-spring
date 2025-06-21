create table user
(
    id          bigint unsigned auto_increment,
    uid         bigint unsigned                    not null,
    uname       varchar(32)                        not null,
    email       varchar(64)                        not null,
    status      tinyint  default 0                 not null,
    extra_info  json     default (json_object())   not null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (id),
    unique key user_uid_uk (uid),
    unique key user_uname_uk (uid),
    index user_email_index (email)
);

create table auth
(
    id          bigint unsigned auto_increment,
    uid         bigint unsigned                            not null,
    type        tinyint unsigned default 0                 not null,
    identifier  varchar(64)                                not null,
    credential  varchar(64)                                not null,
    create_time datetime         default CURRENT_TIMESTAMP not null,
    update_time datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (id),
    index user_uid_index (uid),
    index user_identifier_index (identifier)
);