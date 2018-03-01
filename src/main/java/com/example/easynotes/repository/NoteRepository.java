package com.example.easynotes.repository;

import java.util.List;

import com.example.easynotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findByTitleLike(String title);
    //SELECT * FROM NOTES WHERE title like 'TITLE'

    List<Note> findByContentContains(String content);

    List<Note> findByTitleContains(String letter);

}
