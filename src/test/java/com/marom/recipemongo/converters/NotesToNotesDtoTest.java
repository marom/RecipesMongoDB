package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Notes;
import com.marom.recipemongo.dto.NotesDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesDtoTest {

    private NotesToNotesDto notesConverter;

    @Before
    public void setUp() throws Exception {
        notesConverter = new NotesToNotesDto();
    }

    @Test
    public void whenConvertNotesToNotesDtoThenCorrect() {

        // given
        final Notes notes = Notes.builder().id("not1").recipeNotes("Notes").build();

        // when
        final NotesDto notesDto = notesConverter.convert(notes);

        // then
        assertEquals(notesDto.getId(), notes.getId());
        assertEquals(notesDto.getRecipeNotes(), notes.getRecipeNotes());
    }

    @Test
    public void whenConvertNullNotesThenExpectNull() {

        assertNull(notesConverter.convert(null));
    }
}
