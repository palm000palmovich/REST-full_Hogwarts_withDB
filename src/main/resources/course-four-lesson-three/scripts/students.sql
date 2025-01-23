-- liquibase formatted sql

-- changeset formatted ityapkin1.1:1
create index student_name_index on student (name)

-- changeset formatted ityapkin1.2:1
select * from student where name like 'Andrey%'