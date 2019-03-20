package com.marom.recipemongo.converters;

import com.marom.recipemongo.domain.Notes;
import com.marom.recipemongo.dto.NotesDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesDto implements Converter<Notes, NotesDto> {

    @Override
    public NotesDto convert(Notes source) {

        if (source == null) {
            return null;
        }

        final NotesDto notesDto = new NotesDto();
        notesDto.setId(source.getId());
        notesDto.setRecipeNotes(source.getRecipeNotes());
        return notesDto;
    }
}
