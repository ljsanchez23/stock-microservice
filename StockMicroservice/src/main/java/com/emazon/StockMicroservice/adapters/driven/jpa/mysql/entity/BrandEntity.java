package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity;

import com.emazon.StockMicroservice.adapters.util.AdapConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = AdapConstants.BRAND_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
