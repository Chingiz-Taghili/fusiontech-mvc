package com.multishop.fusiontech.dtos.category;

import com.multishop.fusiontech.models.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDto {
    private String name;
    private String image;
    private List<String> subcategoryNames;
}
