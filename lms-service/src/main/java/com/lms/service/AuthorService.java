package com.lms.service;

import com.lms.common.LMSException;
import com.lms.common.models.Author;
import com.lms.common.models.AuthorList;
import com.lms.common.models.AuthorPost;
import com.lms.dal.entities.EAuthor;
import com.lms.dal.entities.EBook;
import com.lms.dal.repository.AuthorRepository;
import com.lms.mappers.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    /**
     *
     * @param authorRepository
     * @param authorMapper
     */
    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper){
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public Author register(AuthorPost authorPost) {
        EAuthor eAuthor = this.authorMapper.asEAuthor(authorPost);
        EAuthor savedAuthor = this.authorRepository.save(eAuthor);

        return this.authorMapper.asAuthor(savedAuthor);
    }

    /**
     * Method to get list of authors
     * @param firstName
     * @param lastName
     * @return
     */
    public AuthorList getAuthors(String firstName, String lastName){

        List<EAuthor> list = new ArrayList<>();

        if(firstName != null && !"".equals(firstName) && lastName != null && !"".equals(lastName)){
            list = this.authorRepository.getAuthorByFirstNameAndLastName(firstName, lastName);
        } else if(firstName != null && !"".equals(firstName)){
            list = this.authorRepository.getAuthorByFirstName(firstName);
        } else if(lastName != null && !"".equals(lastName)){
            list = this.authorRepository.getAuthorByLastName(lastName);
        } else {
            Iterator<EAuthor> iterator = this.authorRepository.findAll().iterator();
            iterator.forEachRemaining(list::add);
        }

        List<Author> authors = list.stream().map(this.authorMapper::asAuthor).collect(Collectors.toList());

        AuthorList authorList = new AuthorList();
        authorList.items(authors);

        return authorList;
    }

    public Author getAuthor(Integer id){

        Optional<EAuthor> eAuthor = this.authorRepository.findById(id);

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
