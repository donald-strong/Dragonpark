
    create table standard_rate_period (
        description varchar(255) not null,
        start_hours integer,
        end_hours integer,
        multi_day boolean,
        primary key (description)
    );
    
     create table flat_rate_period (
        description varchar(255) not null,
        entry_day  varchar(10) not null,
        entry_start  varchar(10) not null,
        entry_end varchar(10) not null,
        exit_day varchar(10) not null,
        exit_start varchar(10) not null,
        exit_end   varchar(10) not null,
        primary key (description)
    );
    