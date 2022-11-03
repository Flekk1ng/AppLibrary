package com.flekk.AppLibrary.repository;

import com.flekk.AppLibrary.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PublisherRep extends CrudRepository<Publisher, Long> {

    @Query(nativeQuery = true, value = "select * from PUBLISHER where name = :name limit 1")
    Publisher findPublisherByName(@Param("name") String name);
}
