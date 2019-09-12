package com.arzhov.bookstore.jpa;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AUTHORS")
@NamedQueries({
        @NamedQuery(name = Author.QUERY_ALL_AUTHOR_BOOKS, query = "SELECT a FROM Author a JOIN FETCH a.authoredBooks b WHERE a.id = :id"),
        @NamedQuery(name = Author.QUERY_FIND_AUTHOR_BOOKS, query = "SELECT a FROM Author a JOIN FETCH a.authoredBooks b WHERE a.firstName like :name OR a.lastName like :name")
})
@NamedEntityGraph(name = Author.GRAPH_AUTHOR_BOOKS, attributeNodes = @NamedAttributeNode("authoredBooks"))
public class Author implements Serializable {
	public static final String QUERY_ALL_AUTHOR_BOOKS = "query.Author.books";
    public static final String QUERY_FIND_AUTHOR_BOOKS = "query.find.Author.books";
    public static final String GRAPH_AUTHOR_BOOKS = "graph.Author.books";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Size(min = 1, max = 50)
    @NotNull
    private String firstName;
    @Size(min = 1, max = 50)
    @NotNull
    private String lastName;
    @JsonIgnore
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> authoredBooks;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getAuthoredBooks() {
        return authoredBooks;
    }

    public void setAuthoredBooks(final Set<Book> authoredBooks) {
        this.authoredBooks = authoredBooks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Author{" + "firstName=" + firstName + ", lastName=" + lastName + '}';
    }
}
