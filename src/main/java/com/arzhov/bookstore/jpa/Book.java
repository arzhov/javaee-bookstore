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
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.arzhov.bookstore.jpa.validation.ISBNConstraint;

@Entity
@Table(name = "BOOKS")
public class Book implements Serializable {

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
    @ManyToMany(fetch = FetchType.EAGER)
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

    public void setId(Long id) {
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
    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
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
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", price=" + price + ", isbn=" + isbn + ", authors=" + authors + '}';
    }
}
