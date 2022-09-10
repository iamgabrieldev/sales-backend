package com.tcs.company.rest.controller;

import com.tcs.company.domain.entity.Produto;
import com.tcs.company.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos repository;

    public ProdutoController(Produtos produtos) {
        this.repository = produtos;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Produto getProdutoById(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException( NOT_FOUND,
                                "Produto não encontrado!"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody @Valid Produto produto) {
        return repository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repository
                .findById(id)
                .map(p -> {
                    repository.delete(p);
                    return Void.TYPE;
                })
                .orElseThrow(() ->
                        new ResponseStatusException( NOT_FOUND,
                                "Produto não encontrado!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(
            @PathVariable Integer id,
            @RequestBody @Valid Produto produto
    ) {
        repository
                .findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    repository.save(produto);
                    return produto;
                })
                .orElseThrow(() ->
                        new ResponseStatusException( NOT_FOUND,
                                "Produto não encontrado!"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }
}
