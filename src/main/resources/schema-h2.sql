-- ub_user
create table if not exists ub_user
(
    id       int8         not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    constraint ub_user_pkey primary key (id)
);

-- ub_burse
create table if not exists ub_burse
(
    id      int8    not null auto_increment,
    user_id int8    not null,
    balance decimal not null default 0,
    constraint ub_burse_pkey primary key (id)
);
-- ub_record
create table if not exists ub_record
(
    id          int8         not null auto_increment,
    user_id     int8         not null,
    type        int8         not null,
    count       decimal      not null default 0,
    create_time timestamp(6) not null default 0,
    constraint ub_record_pkey primary key (id)
);
