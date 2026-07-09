package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.CategoryDomainValidation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_category",
indexes = {
        @Index(name = "idx_category_name", columnList = "category_name")
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLRestriction("active = true")
public class CategoryEntity {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String categoryName;

    @Column(name = "category_description", length = MAX_DESCRIPTION_LENGTH)
    private String categoryDescription;

    @Column(name = "category_active", nullable = false)
    private boolean active;

    @CreatedDate
    @Column(name = "category_created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "category_updated_at", nullable = false)
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "category_created_by", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "category_updated_by", nullable = false)
    private String updatedBy;

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CategoryEntity(String categoryName, String categoryDescription) {

        setCategoryName(categoryName);
        setCategoryDescription(categoryDescription);
        this.active = true;
    }

    private void setCategoryName(String categoryName) {
        String normalizedCategoryName = CategoryDomainValidation.normalize(categoryName);
        String upperCase = normalizedCategoryName != null ? normalizedCategoryName.toUpperCase() : null;
        this.categoryName = CategoryDomainValidation.requireNonBlank(upperCase, "Category Name", MAX_NAME_LENGTH);
    }

    private void setCategoryDescription(String categoryDescription) {
        String normalizeCategoryDescription = CategoryDomainValidation.normalize(categoryDescription);
        this.categoryDescription = CategoryDomainValidation.requireNonBlankIfPresent(normalizeCategoryDescription, "Category Description", MAX_DESCRIPTION_LENGTH);
    }

    public void deactivate() {
        this.active = false;
    }

    public void updateFields(String categoryName, String categoryDescription) {
        setCategoryName(categoryName);
        setCategoryDescription(categoryDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryEntity category)) return false;
        return categoryName != null && categoryName.equals(category.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }
}

