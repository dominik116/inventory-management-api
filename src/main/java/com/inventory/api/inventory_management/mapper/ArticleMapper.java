package com.inventory.api.inventory_management.mapper;

import com.inventory.api.inventory_management.dto.ArticleCreateDto;
import com.inventory.api.inventory_management.dto.ArticleDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDto entityToDto(Article entity);

    Article dtoToEntity(ArticleDto dto);

    default PagingDto<ArticleDto> toPagingDto(Page<Article> articles) {
        List<ArticleDto> articleDtos = articles.getContent().stream()
                .map(ArticleMapper::toArticleDto)
                .collect(Collectors.toList());

        PagingDto<ArticleDto> pagingDto = new PagingDto<>();
        pagingDto.setContent(articleDtos);
        pagingDto.setPage(articles.getNumber());
        pagingDto.setSize(articles.getSize());
        pagingDto.setSort(articles.getSort().toString());
        pagingDto.setTotal((int) articles.getTotalElements());

        return pagingDto;
    }

    private static ArticleDto toArticleDto(Article article) {
        final ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setEan(article.getEan());
        dto.setName(article.getName());
        dto.setDescription(article.getDescription());
        dto.setQuantity(article.getQuantity());
        dto.setPrice(article.getPrice());

        return dto;
    }

    @Mapping(target = "id", ignore = true)
    Article createDtoToEntity(ArticleCreateDto dto);
}
