package com.example.easynotes.controller;

import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteControllerTest{

    @Mock
    NoteRepository noteRepository;
    @InjectMocks
    NoteController noteController;

    @Test
    public void getAllNotes() throws Exception {
        //GIVEN
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        note.setContent("string");
        notes.add(note);
        when(noteRepository.findAll()).thenReturn(notes);
        //WHEN
        List<Note> allNotes = noteController.getAllNotes(null);
        //THEN
        assertThat(allNotes).isNotEmpty();
        assertFalse(allNotes.isEmpty());
        assertEquals("string",allNotes.get(0).getContent());
    }

    @Test
    public void createNote() throws Exception {
        //given

        Note note = new Note();
        note.setContent("byc albo nie byc");
        note.setTitle("nie");
        when(noteRepository.save(note)).thenReturn(note);

        //when
        Note note1 = noteController.createNote(note);

        //then
        assertEquals(note.getContent(),note1.getContent());
        verify(noteRepository).save(any(Note.class));
    }


    @Test
    public void updateNote() throws Exception {
        //GIVEN
        Note note = new Note();
        Note note2 = new Note();
        note.setContent("content");
        note.setTitle("title");
        note2.setContent("note2");
        note2.setTitle("title2");
        when(noteRepository.save(note2)).thenReturn(note2);
        when(noteRepository.findOne(123L)).thenReturn(note2);
        //WHEN
        ResponseEntity<Note> noteResponseEntity = noteController.updateNote(123L, note);
        //THEN
        assertEquals(note,noteResponseEntity.getBody());
    }

    @Test
    public void deleteNote() throws Exception {
        //GIVEN
        Note note = new Note();
        note.setContent("some text");
        note.setTitle("title");
        note.setId(1L);
        when(noteRepository.findOne(1L)).thenReturn(note);
        //WHEN
        ResponseEntity<Note> noteResponseEntity = noteController.deleteNote(1L);
        //THEN
        assertNull("should be null",noteResponseEntity.getBody());
        assertEquals(200, noteResponseEntity.getStatusCodeValue());
        verify(noteRepository).delete(any(Note.class));
    }

    @Test
    public void getAllText() throws Exception {
        //GIVEN
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        note.setContent("string");
        notes.add(note);
        when(noteRepository.findAll()).thenReturn(notes);
        //WHEN
        List<String> allText = noteController.getAllText();
        //THEN
        assertFalse(allText.isEmpty());
        verify(noteRepository).findAll();
    }

    @Test
    public void getLetters() throws Exception {
        //GIVEN
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        Note note1 = new Note();
        note.setContent("string");
        note1.setContent("no");
        notes.add(note);
        notes.add(note1);
        String let = "n";
        when(noteRepository.findByContentContains(let)).thenReturn(notes);
        //WHEN
        List<String> letters = noteController.getLetters();
        //THEN
        assertTrue(letters.isEmpty());
    }

    @Test
    public void getTextByLetter() throws Exception {
        //GIVEN
        String letter = "a";
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        note.setContent("string");
        notes.add(note);
        when(noteRepository.findByTitleContains("a")).thenReturn(notes);
        //WHEN
        List<String> e = noteController.getTextByLetter("a");
        //THEN
        assertFalse(e.isEmpty());
    }

    @Test
    public void getAllNotes1() throws Exception {
        //GIVEN
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        note.setContent("string");
        notes.add(note);
        when(noteRepository.findByTitleLike(anyString())).thenReturn(notes);
        //WHEN
        List<Note> allNotes = noteController.getAllNotes("text");

        //THEN
        assertFalse(allNotes.isEmpty());
        assertEquals("string",allNotes.get(0).getContent());
    }
}