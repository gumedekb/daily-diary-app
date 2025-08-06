package com.diary.io.diary;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.diary.io.dto.CreateDiaryRequest;
import com.diary.io.security.UserPrincipal;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diary")
public class MyDiaryController {

    private final MyDiaryService diaryService;

    public MyDiaryController(MyDiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    public ResponseEntity<MyDiary> createEntry(@RequestBody CreateDiaryRequest request, Principal principal) {
        String username = principal.getName();
        MyDiary created = diaryService.createEntry(request, username);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<MyDiary>> getAllForUser() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MyDiary> entries = diaryService.getAllEntries(currentUser.getId());
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<Optional<MyDiary>> getById(@PathVariable Long id) {
        Optional<MyDiary> entry = diaryService.getEntryById(id);
        return entry != null ? ResponseEntity.ok(entry) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        diaryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MyDiary> updateEntry(
            @PathVariable Long id,
            @RequestBody CreateDiaryRequest request,
            Principal principal) {
        String username = principal.getName();
        MyDiary updatedEntry = diaryService.updateEntry(id, request, username);
        return ResponseEntity.ok(updatedEntry);
    }

}
