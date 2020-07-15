package agierczak.browser.core.css;

public class StringStyleValue extends StyleValue {
  private String value;

  public StringStyleValue() {}

  public StringStyleValue(String value) {
    this.value = value;
    valueType = ValueType.String;
  }

  @Override
  public void parse(String string) {

  }

  public String getValue() {
    return value;
  }
}
