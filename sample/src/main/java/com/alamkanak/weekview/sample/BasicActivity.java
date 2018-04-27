package com.alamkanak.weekview.sample;

import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * A basic example of how to use week view library.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 *
 * Example updated by Ordu Goodhope on 4/27/2018.
 * Profile: http://github.com/goody-h
 */
public class BasicActivity extends BaseActivity {

    @Override
    public List<? extends WeekViewEvent> onWeekChange() {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = WeekViewUtil.today();
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        int dayOfWeek;
        int hour;
        int colors[] = {getResources().getColor(R.color.event_color_01), getResources().getColor(R.color.event_color_02),
                getResources().getColor(R.color.event_color_03), getResources().getColor(R.color.event_color_04)};
        int color;

        int ds[] = {1,2,4,5,7,7,4,3,6,3,2,1,5};
        int hs[] = {1,4,11,14,21,11,6,9,13,16,3,20,15};
        int cs[] = {0,0,3,2,1,2,1,0,3,2,1,0,3};

        for (int i=0; i<13; i++){

            dayOfWeek = ds[i];
            hour = hs[i];
            color = colors[cs[i]];

            Calendar start = (Calendar) startTime.clone();

            start.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            start.set(Calendar.HOUR_OF_DAY, hour);

            Calendar endTime;

            endTime = (Calendar) start.clone();
            endTime.set(Calendar.HOUR_OF_DAY, hour + 2);

            WeekViewEvent event = new WeekViewEvent(i, "Event " + i, "Venue " + i, start, endTime);
            event.setColor(color);

            events.add(event);

        }

        return events;
    }

}
