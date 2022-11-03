package com.flekk.AppLibrary.repository;

import com.flekk.AppLibrary.model.ProductType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductTypeRep extends CrudRepository<ProductType, Long> {

    @Query(nativeQuery = true, value = "select * from PRODUCT_TYPE where name = :name limit 1")
    ProductType findProductTypeByName(@Param("name") String name);
}
