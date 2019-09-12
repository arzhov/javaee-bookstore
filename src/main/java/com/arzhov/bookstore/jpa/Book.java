package com.arzhov.bookstore.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.arzhov.bookstore.jpa.validation.ISBNConstraint;

@Entity
@Table(name = "BOOKS")
@NamedQueries({
        @NamedQuery(name = Book.QUERY_ALL_BOOKS_AUTHORS,
                query = "select b from Book b join fetch b.authors a where a.id = :id")
})
@NamedEntityGraph(name = Book.GRAPH_BOOKS_AUTHORS, attributeNodes = @NamedAttributeNode("authors"))
public class Book implements Serializable {

    public static final String QUERY_ALL_BOOKS_AUTHORS = "query.all.Book.authors";
    public static final String GRAPH_BOOKS_AUTHORS = "graph.Book.authors";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Size(min = 5, max = 240)
    private String title;
    @NotNull
    @DecimalMin("0")
    private BigDecimal price;
    @NotEmpty
    @ISBNConstraint
    private String isbn;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "AUTHOR_BOOK",
    joinColumns = {
        @JoinColumn(name = "bookId")},
    inverseJoinColumns = {
        @JoinColumn(name = "authorId")})
    private Set<Author> authors;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    /**
     * @param title - between 5 and 240 symbols
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    /**
     * ISBN_10 "90-70002-34-5" ISBN_13 "978-92-95055-02-5"
     *
     * @see ISBNConstraint
     * @param isbn
     */
    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(final Set<Author> authors) {
        this.authors = authors;
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
        final Book other = (Book) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", price=" + price + ", isbn=" + isbn + ", authors=" + authors + '}';
    }
}
