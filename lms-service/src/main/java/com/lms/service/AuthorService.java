package com.lms.service;

import com.lms.common.LMSException;
import com.lms.common.models.Author;
import com.lms.common.models.AuthorList;
import com.lms.dal.entities.EAuthor;
import com.lms.dal.repository.AuthorRepository;
import com.lms.mappers.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper){
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public Author register(Author author) {
        EAuthor eAuthor = this.authorMapper.asEAuthor(author);
        EAuthor savedAuthor = this.authorRepository.save(eAuthor);
        return this.authorMapper.asAuthor(savedAuthor);
    }

    public AuthorList getAuthors(String firstName, String lastName){
        return null;
    }

    public Author getAuthor(Integer id){

        Optional<EAuthor> eAuthor = this.authorRepository.findById(new Long(id));

//        if(eAuthor.isPresent()) {
//            return this.authorMapper.asAuthor(eAuthor.get());
//        } else {
//            throw new LMSException(404, "AuthorService", "Author not found", null);
//        }

        return eAuthor
                .map(this.authorMapper::asAuthor)
                .orElseThrow(() -> new LMSException(404, "AuthorService", "Author not found", null));

    }
}
