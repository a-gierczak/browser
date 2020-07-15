package agierczak.browser.core.css;

public class LengthStyleValue extends StyleValue {
  private int length;

  public LengthStyleValue() {}

  public LengthStyleValue(int length) {
    this.length = length;
    valueType = ValueType.Length;
  }

  @Override
  public void parse(String string) {
    String strValue = string.endsWith("px")
        ? string.substring(0, string.length() - 2)
        : string;

    try {
      length = Integer.parseInt(strValue, 10);
      valueType = ValueType.Length;
    } catch (NumberFormatException ignored) {
      valueType = ValueType.Invalid;
    }
  }

  public int getLength() {
    return length;
  }
}
