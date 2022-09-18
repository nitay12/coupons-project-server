package com.JB.couponsproject.util;


import com.JB.couponsproject.enums.Category;
import org.springframework.core.convert.converter.Converter;

@RequestParmeterCovertor
public class StringToCtergotyConverter  implements Converter<String, Category>{

    @Override
    public Category convert(String source) {
        return Category.decode(source);
    }

}
