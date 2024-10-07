package auth.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchedulerService {

    /**
     * cron expression
     * second, minute, hour, day of month, month, day(s) of week
     *
     */
    //syntax                        means
    //------------------------------------------------------------------------------------
    //"0 0 * * * *"                 the top of every hour of every day.
    //"*/10 * * * * *"              every ten seconds.
    //"0 0 8-10 * * *"              8, 9 and 10 o'clock of every day.
    //"0 0/30 8-10 * * *"           8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
    //"0 0 9-17 * * MON-FRI"        on the hour nine-to-five weekdays
    //"0 0 0 25 12 ?"               every Christmas Day at midnight
}
