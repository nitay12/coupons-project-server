package com.JB.couponsproject.util;

import com.JB.couponsproject.enums.Category;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Component
@Converter(autoApply = true)
public class CategoryConvertor implements AttributeConverter<Category ,String> {
    @Override
    public String convertToDatabaseColumn(Category category) {
        return Optional.ofNullable(category).map(Category::getStr).orElse(null);
    }

    @Override
    public Category convertToEntityAttribute(String s) {
        return Category.decode(s);
    }
}
