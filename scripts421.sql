ALTER TABLE student
ADD CONSTRAINT age_constrain CHECK (age >= 16);

alter table student
add constraint name_constrraint check (name is not null);

alter table student
add constraint unique_name unique (name);

alter table faculty
add constraint unique_nameColor unique (name, color);

update student set age = 20
where (age = 0);