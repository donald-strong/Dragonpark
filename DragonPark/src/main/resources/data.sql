-- Standard rate periods
insert into standard_rate_period(description, start_hours, end_hours, multi_day) values ('0-1 Hours', 0, 1, false);
insert into standard_rate_period(description, start_hours, end_hours, multi_day) values ('1-2 Hours', 1, 2, false);
insert into standard_rate_period(description, start_hours, end_hours, multi_day) values ('2-3 Hours', 2, 3, false);
insert into standard_rate_period(description, start_hours, end_hours, multi_day) values ('3+ Hours',  3, 24, true);

-- Flat rate periods
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Monday Early Bird', 'MONDAY', '06:00', '09:00', 'MONDAY', '15:30', '23:30');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Tuesday Early Bird', 'TUESDAY', '06:00', '09:00', 'TUESDAY', '15:30', '23:30');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Wednesday Early Bird', 'WEDNESDAY', '06:00', '09:00', 'WEDNESDAY', '15:30', '23:30');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Thursday Early Bird', 'THURSDAY', '06:00', '09:00', 'THURSDAY', '15:30', '23:30');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Friday Early Bird', 'FRIDAY', '06:00', '09:00', 'FRIDAY', '15:30', '23:30');

insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Monday Night Rate', 'MONDAY', '18:00', '24:00', 'TUESDAY', '00:00', '06:00');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Tuesday Night Rate', 'TUESDAY', '18:00', '24:00', 'WEDNESDAY', '00:00', '06:00');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Wednesday Night Rate', 'WEDNESDAY', '18:00', '24:00', 'THURSDAY', '00:00', '06:00');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Thursday Night Rate', 'THURSDAY', '18:00', '24:00', 'FRIDAY', '00:00', '06:00');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Friday Night Rate', 'FRIDAY', '18:00', '24:00', 'SATURDAY', '00:00', '06:00');

-- Weekend Rate enter any time Saturday or Sunday, exit any time Saturday or Sunday. 
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Saturday Weekend Rate', 'SATURDAY', '00:00', '24:00', 'SATURDAY', '00:00', '24:00');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Sunday Weekend Rate', 'SUNDAY', '00:00', '24:00', 'SUNDAY', '00:00', '24:00');
insert into flat_rate_period(description, entry_day, entry_start, entry_end, exit_day, exit_start, exit_end)
    values ('Overnight Weekend Rate', 'SATURDAY', '00:00', '24:00', 'SUNDAY', '00:00', '24:00');
    