package com.alamkanak.weekview.sample;

import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewUtil;
import com.alamkanak.weekview.sample.apiclient.Event;
import com.alamkanak.weekview.sample.apiclient.MyJsonService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * An example of how events can be fetched from network and be displayed on the week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 *
 * Example updated by Ordu Goodhope on 4/27/2018.
 * Profile: http://github.com/goody-h
 */
public class AsynchronousActivity extends BaseActivity implements Callback<List<Event>> {

    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    boolean calledNetwork = false;

    private Calendar startOfWeek;
    private Calendar endOfWeek;

    @Override
    public List<? extends WeekViewEvent> onWeekChange() {

        startOfWeek = WeekViewUtil.today();
        startOfWeek.set(Calendar.DAY_OF_WEEK, 1);
        startOfWeek.set(Calendar.HOUR, 0);
        startOfWeek.set(Calendar.MINUTE, 0);
        startOfWeek.set(Calendar.SECOND, 0);
        startOfWeek.set(Calendar.MILLISECOND, 0);

        endOfWeek = (Calendar) startOfWeek.clone();
        endOfWeek.set(Calendar.DAY_OF_WEEK, 7);
        endOfWeek.set(Calendar.HOUR, 23);
        endOfWeek.set(Calendar.MINUTE, 59);
        endOfWeek.set(Calendar.SECOND, 59);
        endOfWeek.set(Calendar.MILLISECOND, 999);

        // Download events from network if it hasn't been done already. To understand how events are
        // downloaded using retrofit, visit http://square.github.io/retrofit
        if (!calledNetwork) {
            RestAdapter retrofit = new RestAdapter.Builder()
                    .setEndpoint("https://api.myjson.com/bins")
                    .build();
            MyJsonService service = retrofit.create(MyJsonService.class);
            service.listEvents(this);
            calledNetwork = true;
        }

        // Return only the events that matches newYear and newMonth.
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    /**
     * Checks if an event falls within the week.
     * @param event The event to check for.
     * @return True if the event falls within the week.
     */
    private boolean eventMatches(WeekViewEvent event) {
        return ((event.getStartTime().after(startOfWeek) || event.getStartTime().equals(startOfWeek)) && event.getStartTime().before(endOfWeek)
                && event.getEndTime().after(startOfWeek) && (event.getEndTime().before(endOfWeek) || event.getEndTime().equals(endOfWeek)));
    }

    @Override
    public void success(List<Event> events, Response response) {
        this.events.clear();
        for (Event event : events) {
            this.events.add(event.toWeekViewEvent());
        }
        getWeekView().notifyDatasetChanged();
    }

    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
        Toast.makeText(this, R.string.async_error, Toast.LENGTH_SHORT).show();
    }
}
