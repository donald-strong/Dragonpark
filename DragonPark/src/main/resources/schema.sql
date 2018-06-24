    create table parking_rate (
        id        int primary key auto_increment,
        rate_name varchar(50) not null,
        rate      varchar(20) not null,
        type      varchar(50) not null,
        entry_condition varchar(50) not null,
        exit_condition  varchar(50) not null,
        primary key (id)
    );
    
    create table standard_rate_period (
        id          int primary key auto_increment,
        rate_name   varchar(50) not null,
        description varchar(255) not null,
        start_hours integer,
        end_hours   integer,
        multi_day   boolean,
        primary key (id)
    );
    
     create table flat_rate_period (
        id           int primary key auto_increment,
        rate_name    varchar(50) not null,
        description  varchar(255) not null,
        entry_day    varchar(10) not null,
        entry_start  varchar(10) not null,
        entry_end    varchar(10) not null,
        exit_day     varchar(10) not null,
        exit_start   varchar(10) not null,
        exit_end     varchar(10) not null,
        primary key (id)
    );
    
    create table parking_event (
        id         int primary key auto_increment,
        entry_date varchar(50) not null,
        exit_date  varchar(50) not null,
        rate       varchar(20) not null,
        rate_name  varchar(50) not null,
        primary key (id)
    );
    