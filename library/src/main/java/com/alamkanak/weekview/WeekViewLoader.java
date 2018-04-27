package com.alamkanak.weekview;

import java.util.List;


/**
 * Edited by Ordu Goodhope on 4/27/2018.
 * Profile: http://github.com/goody-h
 */
public interface WeekViewLoader {

    /**
     * @return A list with the events of this period
     */
    List<? extends WeekViewEvent> onLoad();
}
