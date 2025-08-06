package com.diary.io.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MyDiaryRepository extends JpaRepository<MyDiary, Long> {
	List<MyDiary> findByUser_Id(Long userId);
}
