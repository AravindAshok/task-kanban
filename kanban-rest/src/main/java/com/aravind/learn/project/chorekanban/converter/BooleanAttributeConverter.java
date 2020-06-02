package com.aravind.learn.project.chorekanban.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanAttributeConverter implements AttributeConverter<Boolean, String> {
  @Override
  public String convertToDatabaseColumn(Boolean booleanValue) {
    return booleanValue == true ? "Y" : "N";
  }

  @Override
  public Boolean convertToEntityAttribute(String stringValue) {
    return "Y".equalsIgnoreCase(stringValue) ? true : false;
  }
}
