create table user_0
(
    id          bigint unsigned auto_increment,
    uid         bigint unsigned                            not null,
    uname       varchar(64)                                not null,
    phone       varchar(16)                                not null,
    email       varchar(64)                                null,
    avatar      varchar(128)                               null,
    birthday    date                                       null,
    sex         tinyint                                    null,
    status      tinyint unsigned default 0                 not null,
    extra_info  json             default (json_object())   not null,
    create_time datetime         default CURRENT_TIMESTAMP not null,
    update_time datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (id),
    unique key user_uid_uk (uid),
    index user_uname_index (uname),
    index user_email_index (email),
    index user_phone_index (phone)
);

create table auth_0
(
    id          bigint unsigned auto_increment,
    uid         bigint unsigned                            not null,
    type        tinyint unsigned default 0                 not null,
    identifier  varchar(64)                                not null,
    credential  varchar(64)                                not null,
    extra_info  json             default (json_object())   not null,
    create_time datetime         default CURRENT_TIMESTAMP not null,
    update_time datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (id),
    index user_uid_index (uid)
);