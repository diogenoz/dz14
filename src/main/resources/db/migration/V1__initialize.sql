DROP TABLE IF EXISTS employee CASCADE;
CREATE TABLE employee (id bigserial PRIMARY KEY, name VARCHAR(255), age int);

INSERT INTO employee(name, age) VALUES
('Ivanov', 24)
, ('Petrov', 33)
, ('Sidorov', 44);


DROP TABLE IF EXISTS task CASCADE;
CREATE TABLE task (id bigserial PRIMARY KEY, name VARCHAR(255), owner bigint REFERENCES employee (id), assignee bigint REFERENCES employee (id), description varchar(255), status varchar(255));