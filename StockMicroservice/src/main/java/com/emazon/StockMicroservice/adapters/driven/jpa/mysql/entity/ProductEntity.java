package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity;

import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = AdapConstants.PRODUCT_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = AdapConstants.MAX_NAME_VALUE)
    private String name;
    @Column(nullable = false, length = AdapConstants.MAX_DESCRIPTION_VALUE)
    private String description;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double price;
    @ManyToMany
    @JoinTable(
            name = AdapConstants.PRODUCT_CATEGORY,
            joinColumns = @JoinColumn(name = AdapConstants.PRODUCT_ID),
            inverseJoinColumns = @JoinColumn(name = AdapConstants.CATEGORY_ID)
    )
    private List<CategoryEntity> categories = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = AdapConstants.BRAND_ID, nullable = false)
    private BrandEntity brand;
}
