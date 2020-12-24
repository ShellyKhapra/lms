package com.lms.dal.entities;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "book")
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "published_on")
    private Date publishedOn;
    
    @Column(name = "number_of_copies")
    private Integer numberOfCopies;

//    @Column(name = "author_id")
//    private Integer authorId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	private Author author;

//    @Column(name = "book_type_id")
//    private Integer bookTypeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "book_type_id")
	private BookType bookType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Date publishedOn) {
		this.publishedOn = publishedOn;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(Integer numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
}
