package com.multishop.fusiontech.dtos.singledtos;

import com.multishop.fusiontech.models.SubcategoryName;
import com.multishop.fusiontech.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryDto {
    private Long id;
    private Category category;
    private SubcategoryName subcategoryName;
}
