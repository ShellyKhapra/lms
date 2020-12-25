package com.lms.mappers;

import com.lms.common.models.Author;
import com.lms.dal.entities.EAuthor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface AuthorMapper {
    Author asAuthor(EAuthor eAuthor);

    EAuthor asEAuthor(Author author);
}
