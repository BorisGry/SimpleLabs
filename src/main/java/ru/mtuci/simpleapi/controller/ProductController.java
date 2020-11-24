package ru.mtuci.simpleapi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.simpleapi.model.Product;
import ru.mtuci.simpleapi.service.ProductService;

import java.util.List;
@Slf4j
@RestController
@RequestMapping(value = ProductController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    public static final String REST_URL = "/api/v1/product";


    private final ProductService ProductService;

    @Autowired
    public ProductController(ProductService productService) {
        this.ProductService = productService;
    }

    @GetMapping(value = "/{id}")
    public Product get(@PathVariable("id")Long id){
        log.info("get" +id);
        return ProductService.get(id);
    }


    @GetMapping
    public List<Product> getALL(){
        log.info("getall");
        return ProductService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product save(@RequestBody Product product) {
        log.info("save" + product);
        return ProductService.save(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Long id) {
        log.info("delete" + id);
        ProductService.delete(id);
    }

}