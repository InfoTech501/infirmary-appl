package com.rocs.infirmary.application.utils.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
/**
 * {@code StringListConverter} is a JPA {@code AttributeConverter} implementation
 * that allows automatic conversion between a {@code List} of {@code String} values
 * and a single {@code String} for database persistence.
 */
@Convert
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ",";


    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();

    }
}
