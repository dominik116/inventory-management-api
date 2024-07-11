package com.inventory.api.inventory_management.repository;

import com.inventory.api.inventory_management.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
