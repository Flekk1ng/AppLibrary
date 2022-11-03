package com.flekk.AppLibrary.repository;

import com.flekk.AppLibrary.model.PrintedProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PrintedProductRep extends CrudRepository<PrintedProduct, Long> {

    @Query(nativeQuery = true, value = "select * from PRINTED_PRODUCT where PRODUCT_TYPE_ID = :id")
    List<PrintedProduct> findPrintedProductsByTypeId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from PRINTED_PRODUCT where PUBLISHER_ID = :id")
    List<PrintedProduct> findPrintedProductsByPublisherId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from PRINTED_PRODUCT where AUTHORS = :authors")
    List<PrintedProduct> findPrintedProductsByAuthors(@Param("authors") String authors);

    @Query(nativeQuery = true, value = "select * from PRINTED_PRODUCT where PUBLICATION_DATE = :publicationDate")
    List<PrintedProduct> findPrintedProductsByPublicationDate(@Param("publicationDate") Date publicationDate);
}
