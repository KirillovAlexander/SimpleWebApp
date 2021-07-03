CREATE TABLE IF NOT EXISTS employees
(
    employee_id     BIGSERIAL   PRIMARY KEY ,
    first_name      VARCHAR(30) NOT NULL ,
    last_name       VARCHAR(50) NOT NULL ,
    department_id   INTEGER     NOT NULL ,
    job_title       varchar(50) NOT NULL ,
    gender          varchar(6)  NOT NULL
);