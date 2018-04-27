package com.alamkanak.weekview;

import java.util.List;


/**
 * Edited by Ordu Goodhope on 4/27/2018.
 * Profile: http://github.com/goody-h
 */
public class WeekLoader implements WeekViewLoader {

    private WeekChangeListener mOnWeekChangeListener;

    public WeekLoader(WeekChangeListener listener){
        this.mOnWeekChangeListener = listener;
    }

    @Override
    public List<? extends WeekViewEvent> onLoad(){
        return mOnWeekChangeListener.onWeekChange();
    }

    public WeekChangeListener getOnWeekChangeListener() {
        return mOnWeekChangeListener;
    }

    public void setOnWeekChangeListener(WeekChangeListener onWeekChangeListener) {
        this.mOnWeekChangeListener = onWeekChangeListener;
    }

    public interface WeekChangeListener {
        /**
         * Very important interface, it's the base to load events in the calendar.
         * This method is called once to load the current week.<br/>
         * @return a list of the events happening <strong>during the current week</strong>.
         */
        List<? extends WeekViewEvent> onWeekChange();
    }
}
