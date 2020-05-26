package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {


    TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
    this.timeEntryRepository=timeEntryRepository;
    this.timeEntrySummary = meterRegistry.summary("timeEntry.summary");
    this.actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {

        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        return new ResponseEntity<>(timeEntry,HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {

        TimeEntry timeEntry = timeEntryRepository.find(id);
        if(timeEntry != null)
        {actionCounter.increment();
            return new ResponseEntity<>(timeEntry,HttpStatus.OK);}
        else{
            return new ResponseEntity<>(timeEntry,HttpStatus.NOT_FOUND);}
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = timeEntryRepository.list();
        actionCounter.increment();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {

        TimeEntry timeEntry = timeEntryRepository.update(id,expected);

        if(timeEntry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);

        }
        else
            return new ResponseEntity<>(timeEntry,HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        actionCounter.increment();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
