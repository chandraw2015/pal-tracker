package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{


    Map<Long,TimeEntry> timeEntryMap = new HashMap<>();
    private Long id = 0L;

    public TimeEntry create(TimeEntry timeEntry) {
        id++;
        TimeEntry te = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(),timeEntry.getHours());
        timeEntryMap.put(id,te);
        return te;
    }

    public TimeEntry find(long id) {

        return timeEntryMap.get(id);
    }



    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(timeEntryMap.get(id) != null) {
            timeEntry.setId(id);
            timeEntryMap.put(id, timeEntry);
            return timeEntryMap.get(id);
        }else {
            return null;
        }
    }

    public void delete(long id) {
        timeEntryMap.remove(id);
    }

    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList();
        list.addAll(timeEntryMap.values());
        return list;
    }
}
