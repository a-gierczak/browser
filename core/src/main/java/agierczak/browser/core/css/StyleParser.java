package agierczak.browser.core.css;

import agierczak.browser.core.util.Parser;

public final class StyleParser extends Parser<StyleDeclaration> {

  public StyleParser(String string) {
    super(string);
  }

  private enum State {
    Free, InPropertyName, InPropertyValue
  }

  @Override
  public StyleDeclaration parse() {
    StyleDeclaration result = new StyleDeclaration();
    State state = State.Free;
    String currentPropertyName = null;

    while (peek(idx) != null) {
      switch (state) {
        case Free:
          consumeUntil(Character::isLetter);
          state = State.InPropertyName;
          break;
        case InPropertyName: {
          currentPropertyName = collectUntil(':');
          state = State.InPropertyValue;
          idx++;
          break;
        }
        case InPropertyValue: {
          String propertyValue = collectUntil(c -> c == ';');
          StyleValue[] styleValues = new StyleValue[] {
              new ColorStyleValue(),
              new LengthStyleValue(),
          };

          for (StyleValue styleValue : styleValues) {
            styleValue.parse(propertyValue.trim());
            if (styleValue.isValid()) {
              result.setProperty(currentPropertyName, styleValue);
              break;
            }
          }

          currentPropertyName = null;
          state = State.Free;
          idx++;
          break;
        }
      }
    }

    return result;
  }
}
