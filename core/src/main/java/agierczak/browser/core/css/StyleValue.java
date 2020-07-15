package agierczak.browser.core.css;

public abstract class StyleValue {
  public enum ValueType {
    Color, Length, String, Invalid
  }

  protected ValueType valueType = ValueType.Invalid;

  public abstract void parse(String string);

  public boolean isValid() {
    return valueType != ValueType.Invalid;
  }

  public boolean isColor() {
    return valueType == ValueType.Color;
  }

  public boolean isLength() {
    return valueType == ValueType.Length;
  }

  public ValueType getValueType() {
    return valueType;
  }

}
