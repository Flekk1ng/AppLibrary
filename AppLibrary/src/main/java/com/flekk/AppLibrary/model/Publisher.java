package com.flekk.AppLibrary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PUBLISHER")
public class Publisher {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrintedProduct> printedProducts = new ArrayList<>();

    public Publisher(String name) {
        setName(name);
    }

    public Publisher() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addProduct(PrintedProduct product) {
        printedProducts.add(product);
        product.setPublisher(this);
    }

    public void removeProduct(PrintedProduct product) {
        printedProducts.remove(product);
        product.setPublisher(null);
    }

}
