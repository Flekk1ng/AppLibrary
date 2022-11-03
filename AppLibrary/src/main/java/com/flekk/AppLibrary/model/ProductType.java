package com.flekk.AppLibrary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT_TYPE")
public class ProductType {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrintedProduct> printedProducts = new ArrayList<>();

    public ProductType(String name) {
        setName(name);
    }

    public ProductType() { }

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

    public void addProduct(PrintedProduct product) {
        printedProducts.add(product);
        product.setType(this);
    }

    public void removeProduct(PrintedProduct product) {
        printedProducts.remove(product);
        product.setType(null);
    }

}
