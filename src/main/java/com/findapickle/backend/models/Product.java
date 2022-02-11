package com.findapickle.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;

    private String code;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String quantity;

    private String categories;

    private String brands;

    private String stores;

    private String countries;

    private String image_url;
}
