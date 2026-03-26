package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private JournalEntryService journalEntryService;

    // All id find ho jaygi
    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();

    }

    // All id crete ho jayngi
    @PostMapping(consumes = "application/json")
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    // Id ke base pe mil jaygi
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).orElse(null);
    }

    //Delete bali id mil jaygi
    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.DeleteById(myId);
        return true;

    }

    // id ko change kar skate hai
    @PutMapping("/id/{id}")
    public JournalEntry updateJournalById(@PathVariable String id, @RequestBody JournalEntry newEntry) {

        ObjectId objectId = new ObjectId(id);

        JournalEntry old = journalEntryService.findById(objectId).orElse(null);

        if (old != null) {

            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                old.setTitle(newEntry.getTitle());
            }

            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                old.setContent(newEntry.getContent());
            }

            journalEntryService.saveEntry(old);
        }

        return old;
    }
}