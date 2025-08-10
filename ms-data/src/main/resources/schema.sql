create table trade
(
    id              bigint unsigned auto_increment comment '内部自增主键',
    trade_id        varchar(32)                            not null comment '交易单号（唯一）',
    merchant_id     varchar(32)                            not null comment '商户号',
    out_trade_id    varchar(32)                            not null comment '商户侧订单号（幂等键）',
    uid             bigint unsigned                        not null comment '用户ID',
    amount          bigint unsigned                        not null comment '金额',
    currency        char(3)                                not null comment '币种',
    trade_type      tinyint unsigned                       not null comment '交易类型',
    status          tinyint unsigned default 0             not null comment '交易状态',
    biz_status      tinyint unsigned default 0             not null comment '业务状态',
    error_code      varchar(16)                            null     comment '错误码',
    error_desc      varchar(32)                            null     comment '错误描述',
    payment_id      varchar(32)                            null     comment '支付单号/聚合支付流水号',
    extra_info      json        default (json_object())    not null comment '扩展字段(JSON)',
    subject         varchar(256)                           null     comment '订单/商品标题',
    attach          varchar(256)                           null     comment '回传附加数据',
    notify_url      varchar(256)                           null     comment '异步通知地址',
    create_time     datetime    default current_timestamp  not null comment '创建时间',
    update_time     datetime    default current_timestamp  not null on update current_timestamp comment '更新时间',
    finish_time     datetime                               null     comment '完成时间',
    expire_time     datetime                               null     comment '过期时间',
    primary key (id),
    unique key uk_trade_id (trade_id),
    unique key uk_out_trade_id (merchant_id, out_trade_id)
) comment='交易表';


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