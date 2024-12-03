create table if not exists projects(
    id int primary key ,
    name varchar(100) not null ,
    description varchar(250) not null
);

create table if not exists tasks(id int primary key ,
                                       title varchar(100) not null ,
                                        status  varchar(50) not null,
    due_date Date not null,
      project_id int  references projects(id),
    description varchar(250) not null
    );
