package com.diary.io.diary;

import org.springframework.stereotype.Service;
import com.diary.io.user.User;
import com.diary.io.user.UserRepository;
import com.diary.io.dto.CreateDiaryRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MyDiaryService {

    private final MyDiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public MyDiaryService(MyDiaryRepository diaryRepository, UserRepository userRep) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRep;
    }

    public MyDiary createEntry(CreateDiaryRequest request, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        MyDiary diary = new MyDiary();
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setUser(user); 

        return diaryRepository.save(diary);
    }



    public List<MyDiary> getAllEntries(Long userId) {
        return diaryRepository.findByUser_Id(userId);
    }
    
    public MyDiary updateEntry(Long id, CreateDiaryRequest request, String username) {
        MyDiary existing = diaryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Diary not found"));

        if (!existing.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to update this entry");
        }

        existing.setTitle(request.getTitle());
        existing.setContent(request.getContent());
        existing.setUpdatedAt(LocalDateTime.now());

        return diaryRepository.save(existing);
    }

    public Optional<MyDiary> getEntryById(Long id) {
        return diaryRepository.findById(id);
    }

    public void deleteEntry(Long id) {
        diaryRepository.deleteById(id);
    }
}

