-- liquibase formatted sql

-- changeset formatted ityapkin2.1:1
create index faculty_name_index on faculty (name)

-- changeset formatted ityapkin2.2:1
select * from faculty where name like 'str%'