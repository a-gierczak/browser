package agierczak.browser.core.css;

public class MissingFallbackStyleException extends CssException {

  public MissingFallbackStyleException(String propertyName) {
    super(String.format("Missing vendor style for property %s.", propertyName));
  }
}
