package com.flekk.AppLibrary.controller;

import com.flekk.AppLibrary.model.FieldValue;
import com.flekk.AppLibrary.model.PrintedProduct;
import com.flekk.AppLibrary.model.ProductType;
import com.flekk.AppLibrary.model.Publisher;
import com.flekk.AppLibrary.repository.PrintedProductRep;
import com.flekk.AppLibrary.repository.ProductTypeRep;
import com.flekk.AppLibrary.repository.PublisherRep;
import com.flekk.AppLibrary.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces = "application/json")
public class Controller {

    @Autowired
    private PrintedProductRep productRep;

    @Autowired
    private ProductTypeRep typeRep;

    @Autowired
    private PublisherRep publisherRep;

    private PrintedProduct getPrintedProductById(Long id) {
        Optional<PrintedProduct> optionalPrintedProduct = productRep.findById(id);
        return optionalPrintedProduct.orElse(null);
    }

    @PostMapping("/printed_products")
    public ResponseEntity<PrintedProduct> addPrintedProduct(@RequestBody PrintedProduct printedProduct) {
        Publisher publisher = publisherRep.findPublisherByName(printedProduct.getPublisher().getName());
        if (publisher == null)
            publisherRep.save(printedProduct.getPublisher());
        else {
            publisher.addProduct(printedProduct);
            printedProduct.setPublisher(publisher);
        }
        ProductType productType = typeRep.findProductTypeByName(printedProduct.getType().getName());
        if (productType == null)
            typeRep.save(printedProduct.getType());
        else {
            productType.addProduct(printedProduct);
            printedProduct.setType(productType);
        }
        productRep.save(printedProduct);
        System.out.println(printedProduct.toString() + " has been added to the database!");
        return new ResponseEntity<>(printedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/printed_products")
    public ResponseEntity<PrintedProduct> deletePrintedProduct(@RequestBody Long id) {
        PrintedProduct printedProduct = getPrintedProductById(id);
        if (printedProduct == null) {
            System.out.println("Product with this id is not in the database!");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        System.out.println(printedProduct + " removed from database!");
        productRep.deleteById(id);
        return new ResponseEntity<>(printedProduct, HttpStatus.OK);
    }

    @PutMapping("/printed_products")
    public ResponseEntity<PrintedProduct> updatePrintedProduct(@RequestBody PrintedProduct printedProduct) {
        Long id = printedProduct.getId();
        PrintedProduct newPrintedProduct = getPrintedProductById(id);
        if (newPrintedProduct == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else {
            newPrintedProduct.setName(printedProduct.getName());
            newPrintedProduct.setAuthors(printedProduct.getAuthors());
            newPrintedProduct.setPublicationDate(printedProduct.getPublicationDate());
            newPrintedProduct.setType(printedProduct.getType());
            newPrintedProduct.setPublisher(printedProduct.getPublisher());
            productRep.save(newPrintedProduct);
            System.out.println(printedProduct.toString() + "\n changed to: \n" + newPrintedProduct.toString());
            return new ResponseEntity<>(newPrintedProduct, HttpStatus.OK);
        }
    }

    @GetMapping("/printed_products")
    public ResponseEntity<List<PrintedProduct>> findPrintedProduct(@RequestBody FieldValue fieldValue) {
        List<PrintedProduct> printedProducts;
        switch (fieldValue.getField()) {
            case "type":
                Long typeId = typeRep.findProductTypeByName(fieldValue.getValue()).getId();
                printedProducts = productRep.findPrintedProductsByTypeId(typeId);
                break;
            case "publisher":
                Long publisherId = publisherRep.findPublisherByName(fieldValue.getValue()).getId();
                printedProducts = productRep.findPrintedProductsByPublisherId(publisherId);
                break;
            case "authors":
                printedProducts = productRep.findPrintedProductsByAuthors(fieldValue.getValue());
                break;
            case "publication_date":
                printedProducts = productRep.findPrintedProductsByPublicationDate(DateService.parseDate(fieldValue.getValue()));
                break;
            default:
                System.out.println("This field does not exist or search by it is not available.");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        System.out.println("Products whose " + fieldValue.getField() + " = " + fieldValue.getValue());
        for (PrintedProduct printedProduct: printedProducts) {
            System.out.println(printedProduct.toString());
        }
        return new ResponseEntity<>(printedProducts, HttpStatus.OK);
    }

}