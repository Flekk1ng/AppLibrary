package com.flekk.AppLibrary.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PRINTED_PRODUCT")
public class PrintedProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Publisher publisher;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "authors", nullable = false)
    private String authors;

    @Column (name = "publicationDate")
    private Date publicationDate;

    public PrintedProduct(String name, String authors, Date publicationDate) {
        setName(name);
        setAuthors(authors);
        setPublicationDate(publicationDate);
    }

    public PrintedProduct() { }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return productType;
    }

    public void setType(ProductType productType) {
        this.productType = productType;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Name of printed product: " + this.name
                + "; authors: " + this.authors
                + "; publication date: " + this.publicationDate
                + "; type: " + this.productType.getName()
                + "; publisher name: " + this.publisher.getName()
                + "; publisher address: " + this.publisher.getAddress();
    }

}
