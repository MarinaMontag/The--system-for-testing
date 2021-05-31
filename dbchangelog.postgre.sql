-- liquibase formatted sql changeLogId:c06b80fb-9851-4b2b-907d-70a23c96b277
--changeset marina:1
create table users(
    user_id serial primary key,
    ufname varchar(50) not null,
    ulname varchar(50) not null,
    uemail varchar(50) not null,
    upassword varchar(50) not null,
    urole varchar(7) check (urole in ('student', 'tutor')) not null
)

--changeset marina:2
drop table users

--changeset marina:3
create table users(
    user_id serial primary key,
    ufname varchar(50) not null,
    ulname varchar(50) not null,
    uemail varchar(50) not null,
    upassword varchar(50) not null,
    urole int check (urole in (0, 1)) not null
)

--changeset marina:4
drop table users

--changeset marina:5
create table users(
    user_id serial primary key,
    ufname varchar(50) not null,
    ulname varchar(50) not null,
    uemail varchar(50) not null,
    upassword varchar(50) not null,
    urole int check (urole in (0, 1)) not null
)

--changeset marina:6
drop table users

--changeset marina:7
create table users(
    user_id serial primary key,
    ufname varchar(50) not null,
    ulname varchar(50) not null,
    uemail varchar(50) unique not null,
    upassword varchar(50) not null,
    urole int check (urole in (0, 1)) not null
)

--changeset marina:8
create table subj(
    s_id serial primary key,
    sname varchar(50) unique  not null
)
--changeset marina:9
insert into subj values(default, 'Algebra');
insert into subj values(default, 'Geometry');
insert into subj values(default, 'Biology');
insert into subj values(default, 'Physics');
insert into subj values(default, 'Geography');
insert into subj values(default, 'Chemistry');

--changeset marina:10
create table tests(
    t_id serial primary key,
    s_id int not null,
    tname varchar(100) not null,
    tdesc varchar(300) not null,
    constraint tests_sid_fk foreign key (s_id) references subj(s_id)
)

--changeset marina:11
insert into tests values(default, 1, 'Adding', 'Calculating');
insert into tests values(default, 1, 'Subtraction', 'Calculating');
insert into tests values(default, 2, 'Planimetry', 'Solve some exercises');

--changeset marina:12
create table ques(
    q_id serial primary key,
    t_id int not null,
    qtext varchar(300) not null,
    constraint ques_tid_fk foreign key (t_id) references tests(t_id)
);

create table answ(
    a_id serial primary key,
    q_id int not null,
    atext varchar(300) not null,
    corr int check(corr in(0,1)) not null,
    constraint answ_qid_fk foreign key (q_id) references ques(q_id)
);

--changeset marina:13
insert into ques values(default, 1, '1 + 5 = ');
insert into answ values(default, 1, '-6', 0);
insert into answ values(default, 1, '6', 1);
insert into answ values(default, 1, '4', 0);

insert into ques values(default, 1, '-2 + 3 = ');
insert into answ values(default, 2, '0', 0);
insert into answ values(default, 2, '3', 0);
insert into answ values(default, 2, '1', 1);

insert into ques values(default, 3, 'Formula of hypotenuse(legs of a right triangle: AB and BC; hypotenuse: AC)');
insert into answ values(default, 3, 'AB + BC', 0);
insert into answ values(default, 3, 'AB^2 + BC^2', 0);
insert into answ values(default, 3, 'sqrt(AB^2 + BC^2)', 1);

insert into ques values(default, 2, '-2 - (-3) = ');
insert into answ values(default, 4, '0', 0);
insert into answ values(default, 4, '3', 0);
insert into answ values(default, 4, '1', 1);
--changeset marina:14
select * from tests;


