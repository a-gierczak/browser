package agierczak.browser.core.css;

import agierczak.browser.core.css.StyleValue.ValueType;
import java.util.HashMap;

public class StyleDeclaration {

  private HashMap<String, StyleValue> styleMap = new HashMap<>();

  public StyleDeclaration() {}

  public StyleDeclaration(StyleDeclaration styles) {
    styleMap = new HashMap<>(styles.styleMap);
  }

  public void setProperty(String propertyName, StyleValue propertyValue) {
    styleMap.put(propertyName, propertyValue);
  }

  public StyleValue getPropertyValue(String propertyName) {
    StyleValue value = styleMap.get(propertyName);
    return value != null && value.isValid() ? value : null;
  }

  public StyleValue getPropertyValue(String propertyName, ValueType valueType) {
    StyleValue value = getPropertyValue(propertyName);
    return value != null && value.getValueType() == valueType ? value : null;
  }

  public StyleValue getPropertyValue(String propertyName, ValueType valueType,
      StyleDeclaration fallback) throws MissingFallbackStyleException {
    StyleValue value = getPropertyValue(propertyName, valueType);

    if (value != null) {
      return value;
    }

    StyleValue fallbackValue = fallback.getPropertyValue(propertyName, valueType);
    if (fallbackValue == null) {
      throw new MissingFallbackStyleException(propertyName);
    }

    return fallbackValue;
  }

}
