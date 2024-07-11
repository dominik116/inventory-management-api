package com.inventory.api.inventory_management.controller;

import com.inventory.api.inventory_management.dto.ArticleCreateDto;
import com.inventory.api.inventory_management.dto.ArticleDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ArticleController {

    private final ArticleService articleService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/articles", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<ArticleDto> postArticle(@Valid @RequestBody final ArticleCreateDto dto) {
        return ResponseEntity.created(URI.create("/articles")).body(this.articleService.create(dto));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/articles", produces = {"application/json"})
    public ResponseEntity<PagingDto<ArticleDto>> findAllArticlesWithPagination(
            @RequestParam(name = "page", defaultValue = "0") final int page, @RequestParam(name = "size", defaultValue = "10") final int size) {
        return ResponseEntity.ok(this.articleService.findAll(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/articles/{id}", produces = {"application/json"})
    public ResponseEntity<ArticleDto> findArticleById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.articleService.findById(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, path = "/articles/{id}", produces = {"application/json"})
    public void putUpdateArticle(@PathVariable("id") final Long id, @Valid @RequestBody final ArticleCreateDto dto) {
        this.articleService.putUpdate(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, path = "/articles/{id}", produces = {"application/json"})
    public void deleteArticleById(@PathVariable("id") final Long id) {
        this.articleService.deleteById(id);
    }
}
