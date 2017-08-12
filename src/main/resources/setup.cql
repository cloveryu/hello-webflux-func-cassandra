CREATE KEYSPACE IF NOT EXISTS hello WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS hello.employees (
    id UUID,
    firstName varchar,
    lastName varchar,
    gender varchar,
    age int,
    birthDate varchar,
    primary key(id)
);

CREATE TABLE IF NOT EXISTS hello.employees_by_last_name (
    last_name varchar,
    employee_id UUID,
    firstName varchar,
    gender varchar,
    age int,
    birthDate varchar,
    primary key((last_name), employee_id)
);