create table role_t
(
  role varchar(15) not null
    constraint role_t_pkey
    primary key
)
;

create unique index role_t_role_uindex
  on role_t (role)
;

create table condition
(
  condition varchar(15) not null
    constraint condition_pkey
    primary key
)
;

create unique index condition_condition_uindex
  on condition (condition)
;

create table user_t
(
  id serial not null
    constraint user_t_pkey
    primary key,
  login varchar(40) not null,
  first_name varchar(40),
  last_name varchar(40),
  password varchar(200) not null,
  role varchar(15) not null
    constraint user_t_role_t_role_fk
    references role_t
)
;

create unique index user_t_id_uindex
  on user_t (id)
;

create unique index user_t_login_uindex
  on user_t (login)
;

create table task
(
  id serial not null
    constraint task_pkey
    primary key,
  condition varchar(15) not null,
  description text,
  date_add timestamp not null,
  dead_line timestamp not null,
  user_id integer
)
;

create unique index task_id_uindex
  on task (id)
;

create table comment
(
  id serial not null
    constraint comment_pkey
    primary key,
  task_id integer not null
    constraint comment_task_id_fk
    references task,
  user_id integer not null
    constraint comment_user_t_id_fk
    references user_t,
  date timestamp not null,
  comment text not null
)
;

create unique index comment_id_uindex
  on comment (id)
;

INSERT INTO role_t (role) VALUES ('MANAGER'), ('WORKER'), ('ADMIN');

SELECT * FROM role_t;

INSERT INTO condition (condition) VALUES ('VERIFICATION'), ('IN_PROCESS'), ('DONE');

SELECT * FROM condition;

INSERT INTO user_t (login, first_name, last_name, password, role) VALUES
('test_manager_1', 'test_m_name_1', 'test_m_last_name_1', 'manager', 'MANAGER'),
('test_manager_2', 'test_m_name_2', 'test_m_last_name_2', 'manager', 'MANAGER'),
('test_admin_1', 'test_a_name_1', 'test_a_last_name_1', 'admin', 'ADMIN'),
('test_admin_2', 'test_a_name_2', 'test_a_last_name_2', 'admin', 'ADMIN'),
('test_worker_1', 'test_w_name_1', 'test_w_last_name_1', 'worker', 'WORKER'),
('test_worker_2', 'test_w_name_2', 'test_w_last_name_2', 'worker', 'WORKER');

INSERT INTO user_t (login, first_name, last_name, password, role) VALUES
  ('testreturningint', 'noname', 'nolaste', 'pass','WORKER') RETURNING id;

SELECT * FROM user_t;

INSERT INTO task (condition, description, date_add, dead_line, user_id) VALUES
('VERIFICATION', 'its a test task', '2018-01-14 17:33:40', '2018-01-29 18:33:00', '4'),
('DONE', 'its a test task', '2018-01-14 17:33:40', '2018-01-29 18:33:00', '4'),
('VERIFICATION', 'its a test task', '2018-01-14 17:33:40', '2018-01-29 18:33:00', '8'),
('IN_PROCESS', 'its a test task', '2018-01-14 17:33:40', '2018-01-29 18:33:00', '8'),
('VERIFICATION', 'its a test task', '2018-01-14 17:33:40', '2018-01-29 18:33:00', '4');

SELECT * FROM task;


DELETE FROM task WHERE user_id NOT IN (SELECT id FROM user_t);