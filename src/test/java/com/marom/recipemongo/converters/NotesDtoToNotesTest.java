package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Notes;
import com.marom.recipemongo.dto.NotesDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesDtoToNotesTest {

    private NotesDtoToNotes notesDtoToNotes;

    @Before
    public void setUp() {

        notesDtoToNotes = new NotesDtoToNotes();
    }


    @Test
    public void whenConvertToNotesThenCorrect(){

        // given
        NotesDto notesDto = new NotesDto();
        notesDto.setId("not1");
        notesDto.setRecipeNotes("NotestDto note");

        // when
        Notes notes = notesDtoToNotes.convert(notesDto);

        // then
        assertEquals(notes.getId(), notesDto.getId());
        assertEquals(notes.getRecipeNotes(), notesDto.getRecipeNotes());
    }

    @Test
    public void whneConvertNullNotesDtoThenExpectNull() {

        assertNull(notesDtoToNotes.convert(null));
    }
}
