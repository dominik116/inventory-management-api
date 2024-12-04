package com.inventory.api.inventory_management.service;

import com.inventory.api.inventory_management.dto.ArticleCreateDto;
import com.inventory.api.inventory_management.dto.ArticleDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Article;
import com.inventory.api.inventory_management.mapper.ArticleMapper;
import com.inventory.api.inventory_management.repository.ArticleRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository repository;

    private final ArticleMapper mapper;

    private final CacheManager cacheManager;

    public ArticleDto create(final ArticleCreateDto dto) {
        log.info("IN ArticleService: create");
        if (this.repository.findByEan(dto.getEan()).isPresent()) {
            throw new EntityExistsException("Article with EAN: " + dto.getEan() + " already exists.");
        }
        final Article article = this.repository.save(this.mapper.createDtoToEntity(dto));
        this.clearCache();
        return this.mapper.entityToDto(article);
    }

    public ArticleDto findById(final Long id) {
        log.info("IN ArticleService: findById");
        return this.mapper.entityToDto(this.repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Cacheable(value = "articleCache", key = "'page:' + #page + ',size:' + #size")
    public PagingDto<ArticleDto> findAll(final int page, final int size) {
        log.info("IN ArticleService: findAll");
        final Page<Article> article = this.repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
        return this.mapper.toPagingDto(article);
    }

    public void putUpdate(final Long id, final ArticleCreateDto dto) {
        log.info("IN ArticleService: putUpdate");
        final Article entity = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
        this.updateEntityFields(entity, dto);
        this.repository.save(entity);
        this.clearCache();
        log.info("OUT ArticleService: putUpdate");
    }

    public void deleteById(final Long id) {
        log.info("IN ArticleService: deleteById");
        this.repository.deleteById(id);
        this.clearCache();
        log.info("OUT ArticleService: deleteById");
    }

    private void clearCache() {
        Objects.requireNonNull(this.cacheManager.getCache("articleCache")).clear();
        log.info("Cache 'articleCache' has been cleared successfully.");
    }

    private void updateEntityFields(final Article article, final ArticleCreateDto dto) {
        if (dto.getDescription() != null && !dto.getDescription().equals(article.getDescription())) {
            article.setDescription(dto.getDescription());
        }

        if (dto.getQuantity() != null && !dto.getQuantity().equals(article.getQuantity())) {
            article.setQuantity(dto.getQuantity());
        }

        if (dto.getPrice() != null && !dto.getPrice().equals(article.getPrice())) {
            article.setPrice(dto.getPrice());
        }
    }
}
