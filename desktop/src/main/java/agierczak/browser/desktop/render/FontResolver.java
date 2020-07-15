package agierczak.browser.desktop.render;

import agierczak.browser.core.css.LengthStyleValue;
import agierczak.browser.core.css.MissingFallbackStyleException;
import agierczak.browser.core.css.StringStyleValue;
import agierczak.browser.core.css.StyleDeclaration;
import agierczak.browser.core.css.StyleValue.ValueType;
import java.awt.Font;

public final class FontResolver {

  private static FontResolver instance;

  private FontResolver() {
  }

  public static FontResolver getInstance() {
    if (instance == null) {
      instance = new FontResolver();
    }

    return instance;
  }

  public Font resolve(StyleDeclaration style, StyleDeclaration fallback)
      throws MissingFallbackStyleException {
    StringStyleValue fontFamilyStyle = (StringStyleValue) style.getPropertyValue(
        "font-family",
        ValueType.String,
        fallback
    );
    String fontFamily = fontFamilyStyle.getValue();
    StringStyleValue fontWeightStyle = (StringStyleValue) style.getPropertyValue(
        "font-family",
        ValueType.String,
        fallback
    );

    int fontWeight = Font.PLAIN;
    switch (fontWeightStyle.getValue()) {
      case "bold":
        fontWeight = Font.BOLD;
        break;
    }

    LengthStyleValue fontSizeStyle = (LengthStyleValue) style.getPropertyValue(
        "font-size",
        ValueType.Length,
        fallback
    );
    int fontSize = fontSizeStyle.getLength();

    return new Font(fontFamily, fontWeight, fontSize);
  }
}
