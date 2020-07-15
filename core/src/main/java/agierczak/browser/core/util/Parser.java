package agierczak.browser.core.util;

import java.util.function.Predicate;

public abstract class Parser<T> {
  public abstract T parse();

  protected String string;
  protected int idx;

  public Parser(String string) {
    this.string = string;
    idx = 0;
  }

  protected Character peek(int index) {
    return index < string.length() ? string.charAt(index) : null;
  }

  protected Character peek() {
    return peek(idx);
  }

  protected void consumeUntil(Predicate<Character> isMatch) {
    Character ch;
    do {
      ch = peek(idx);
      if (ch == null || isMatch.test(ch)) {
        break;
      }
      ++idx;
    } while (true);
  }

  protected void consumeUntil(char needle) {
    consumeUntil(ch -> ch == needle);
  }

  // TODO: tutaj dodac callback parametr w stylu isInvalid ktory bedzie throwac
  protected String collectUntil(Predicate<Character> isMatch) {
    StringBuilder builder = new StringBuilder();
    Character ch;
    do {
      ch = peek(idx);
      if (ch == null || isMatch.test(ch)) {
        break;
      }
      ++idx;
      builder.append(ch);
    } while (true);
    return builder.toString();
  }

  protected String collectUntil(char needle) {
    return collectUntil(ch -> ch == needle);
  }
}
