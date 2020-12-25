package com.lms.dal.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book_type")
public class EBookType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "type")
    private String type;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "book_type_id")
	private List<EBook> books;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<EBook> getBooks() {
		return books;
	}

	public void setBooks(List<EBook> books) {
		this.books = books;
	}
}
