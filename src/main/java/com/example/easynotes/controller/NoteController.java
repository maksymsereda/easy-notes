package com.example.easynotes.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {
    private final NoteRepository noteRepository;

    @GetMapping("/notes")
    public List<Note> getAllNotes(@RequestParam(value = "title", required = false) String title) {
        if (title == null) {
            return noteRepository.findAll();
        } else {
            return noteRepository.findByTitleLike(title);
        }
    }

    @PostMapping("/notes")
    public Note createNote(@RequestBody @Valid Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<Note> createNote(@PathVariable(value = "id") long noteId) {
        Note note = noteRepository.findOne(noteId);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(note);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable(value = "id") Long noteId,
        @Valid @RequestBody Note noteDetails) {
        Note note = noteRepository.findOne(noteId);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        Note updatedNote = noteRepository.save(note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findOne(noteId);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notestext")
    public List<String> getAllText(){
        return noteRepository.findAll()
                .stream()
                .map(Note::getContent)
                .collect(Collectors.toList());
    }

    @GetMapping("/notesByLetter")
    public List<String> getLetters(){
        return noteRepository.findByContentContains("e")
                .stream()
                .map(note -> note.getContent())
                .collect(Collectors.toList());
    }

    @GetMapping("/getInAllByLetter")
    public List<String> getTextByLetter (@RequestParam(value = "letter") String letter){
        return noteRepository.findByTitleContains(letter)
                .stream()
                .map(Note::getContent)
                .collect(Collectors.toList());
    }
}
