package com.inventory.api.inventory_management.repository;

import com.inventory.api.inventory_management.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByEan(String ean);

    Optional<Article> findByName(String name);
}
