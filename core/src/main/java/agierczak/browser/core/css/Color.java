package agierczak.browser.core.css;

public final class Color {
  public static final Color BLACK = new Color(0xff000000);
  public static final Color RED = new Color(0xffff0000);
  public static final Color GREEN = new Color(0xff00ff00);
  public static final Color BLUE = new Color(0xff0000ff);
  public static final Color TRANSPARENT = new Color(0x00000000);
  private int value;

  public Color(int argb) {
    value = argb;
  }

  public Color(int red, int green, int blue) {
    value = rgbToValue(red, green, blue);
  }

  public Color(int red, int green, int blue, int alpha) {
    value = rgbToValue(red, green, blue);
    value |= (alpha & 0xff) << 24;
  }

  private int rgbToValue(int r, int g, int b) {
    int red = (r & 0xff) << 16;
    int green = (g & 0xff) << 8;
    int blue = (b & 0xff);
    return red | green | blue;
  }

  public int getValue() {
    return value;
  }
}
