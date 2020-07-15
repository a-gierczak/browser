package agierczak.browser.core.css;

public class ColorStyleValue extends StyleValue {
  private Color color;

  public ColorStyleValue() {}

  public ColorStyleValue(Color color) {
    this.color = color;
    valueType = ValueType.Color;
  }

  @Override
  public void parse(String string) {
    if (!string.startsWith("#")) {
      valueType = ValueType.Invalid;
      return;
    }

    String hex = string.substring(1, Math.min(string.length(), 7));
    int value = Integer.parseInt(hex, 16);
    color = new Color(value);
    valueType = ValueType.Color;
  }

  public Color getColor() {
    return color;
  }
}
