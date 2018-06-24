-- Parking rates
 insert into parking_rate(rate_name, rate, type, entry_condition, exit_condition)
     values('EarlyBird', '$15.00', 'FlatRate', 'Enter between 6AM to 9AM', 'Exit between 3:30PM to 11:30PM');

-- Standard rate periods
insert into standard_rate_period(rate_name, description, start_hours, end_hours, multi_day) values ('HourlyRate', '0-1 Hours', 0, 1, false);
insert into standard_rate_period(rate_name, description, start_hours, end_hours, multi_day) values ('HourlyRate', '1-2 Hours', 1, 2, false);
insert into standard_rate_period(rate_name, description, start_hours, end_hours, multi_day) values ('HourlyRate', '2-3 Hours', 2, 3, false);
insert into standard_rate_period(rate_name, description, start_hours, end_hours, multi_day) values ('HourlyRate', '3+ Hours',  3, 24, true);

-- Flat rate periods
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('EarlyBird', 'Monday Early Bird', 'MONDAY', '06:00', '09:00', 'MONDAY', '15:30', '23:30');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('EarlyBird', 'Tuesday Early Bird', 'TUESDAY', '06:00', '09:00', 'TUESDAY', '15:30', '23:30');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('EarlyBird', 'Wednesday Early Bird', 'WEDNESDAY', '06:00', '09:00', 'WEDNESDAY', '15:30', '23:30');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('EarlyBird', 'Thursday Early Bird', 'THURSDAY', '06:00', '09:00', 'THURSDAY', '15:30', '23:30');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('EarlyBird', 'Friday Early Bird', 'FRIDAY', '06:00', '09:00', 'FRIDAY', '15:30', '23:30');

insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('NightRate', 'Monday Night Rate', 'MONDAY', '18:00', '24:00', 'TUESDAY', '00:00', '06:00');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('NightRate', 'Tuesday Night Rate', 'TUESDAY', '18:00', '24:00', 'WEDNESDAY', '00:00', '06:00');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('NightRate', 'Wednesday Night Rate', 'WEDNESDAY', '18:00', '24:00', 'THURSDAY', '00:00', '06:00');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('NightRate', 'Thursday Night Rate', 'THURSDAY', '18:00', '24:00', 'FRIDAY', '00:00', '06:00');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('NightRate', 'Friday Night Rate', 'FRIDAY', '18:00', '24:00', 'SATURDAY', '00:00', '06:00');

-- Weekend Rate enter any time Saturday or Sunday, exit any time Saturday or Sunday. 
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('WeekendRate', 'Saturday Weekend Rate', 'SATURDAY', '00:00', '24:00', 'SATURDAY', '00:00', '24:00');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('WeekendRate', 'Sunday Weekend Rate', 'SUNDAY', '00:00', '24:00', 'SUNDAY', '00:00', '24:00');
insert into flat_rate_period(rate_name, description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('WeekendRate', 'Overnight Weekend Rate', 'SATURDAY', '00:00', '24:00', 'SUNDAY', '00:00', '24:00');
    