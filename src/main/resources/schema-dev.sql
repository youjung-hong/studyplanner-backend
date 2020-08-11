create database if not exists study_planner_db;
use study_planner_db;

create table if not exists subject (
    id bigint not null,
    created_at timestamp not null,
    deleted_at timestamp,
    title varchar(255) not null,
    updated_at timestamp not null,
    primary key (id)
);

create table if not exists task (
    id bigint not null,
    created_at timestamp not null,
    date date not null,
    deleted_at timestamp,
    status varchar(255) not null,
    title varchar(255) not null,
    updated_at timestamp not null,
    subject_id bigint,
    primary key (id)
);

create table if not exists  task_time_log (
    id bigint not null,
    created_at timestamp not null,
    deleted_at timestamp,
    end_at timestamp not null,
    start_at timestamp not null,
    updated_at timestamp not null,
    task_id bigint,
    primary key (id)
);