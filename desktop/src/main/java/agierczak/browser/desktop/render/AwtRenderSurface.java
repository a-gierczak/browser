package agierczak.browser.desktop.render;

import agierczak.browser.core.css.Color;
import agierczak.browser.core.css.ColorStyleValue;
import agierczak.browser.core.css.LengthStyleValue;
import agierczak.browser.core.css.MissingFallbackStyleException;
import agierczak.browser.core.css.StringStyleValue;
import agierczak.browser.core.css.StyleDeclaration;
import agierczak.browser.core.css.StyleValue;
import agierczak.browser.core.css.StyleValue.ValueType;
import agierczak.browser.core.layout.Box;
import agierczak.browser.core.render.RenderContext;
import agierczak.browser.core.render.RenderSurface;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

public class AwtRenderSurface implements RenderSurface {

  private AwtRenderContext renderContext;
  private Graphics2D graphics;
  private StyleDeclaration vendorStyles;

  public AwtRenderSurface() {
    renderContext = new AwtRenderContext(this);
    vendorStyles = new StyleDeclaration();

    // TODO: przeniesc to stad
    vendorStyles.setProperty("font-size", new LengthStyleValue(16));
    vendorStyles.setProperty("font-family", new StringStyleValue("serif"));
    vendorStyles.setProperty("font-weight", new StringStyleValue("normal"));
    vendorStyles.setProperty("line-height", new LengthStyleValue(16));
    vendorStyles.setProperty("color", new ColorStyleValue(Color.BLACK));
  }

  @Override
  public void drawRect(Box box, StyleDeclaration style) {
    Stroke prevStroke = graphics.getStroke();
    Paint prevPaint = graphics.getPaint();

    StyleValue backgroundStyle = style.getPropertyValue(
        "background",
        ValueType.Color
    );

    if (backgroundStyle != null) {
      Color backgroundColor = ((ColorStyleValue) backgroundStyle).getColor();
      Rectangle2D rect = new Rectangle2D.Float(box.x, box.y, box.width, box.height);
      graphics.setPaint(new java.awt.Color(backgroundColor.getValue()));
      graphics.fill(rect);
      graphics.setPaint(prevPaint);
    }

    StyleValue borderWidthStyle = style.getPropertyValue(
        "borderWidth",
        ValueType.Length
    );

    if (borderWidthStyle != null) {
      int borderWidth = ((LengthStyleValue) borderWidthStyle).getLength();
      Box strokeBox = box.shrink(borderWidth / 2.0f);
      Rectangle2D rect = new Rectangle2D.Float(
          strokeBox.x,
          strokeBox.y,
          strokeBox.width,
          strokeBox.height);
      graphics.setStroke(new BasicStroke(borderWidth));
      graphics.draw(rect);
      graphics.setStroke(prevStroke);
    }
  }

  @Override
  public void drawText(String text, float x, float y, StyleDeclaration style)
      throws MissingFallbackStyleException {
    Font prevFont = graphics.getFont();
    java.awt.Color prevColor = graphics.getColor();

    Font font = FontResolver.getInstance().resolve(style, getVendorStyles());
    FontMetrics metrics = graphics.getFontMetrics(font);
    graphics.setFont(font);

    ColorStyleValue colorStyle = (ColorStyleValue) style.getPropertyValue(
        "color",
        ValueType.Color,
        getVendorStyles()
    );
    graphics.setColor(new java.awt.Color(colorStyle.getColor().getValue()));

    LengthStyleValue lineHeightStyle = (LengthStyleValue) style.getPropertyValue(
        "line-height",
        ValueType.Length,
        getVendorStyles()
    );

    float canvasY =
        y + ((lineHeightStyle.getLength() - metrics.getHeight()) / 2.0f) + metrics.getAscent();

    graphics.drawString(text, x, canvasY);
    graphics.setFont(prevFont);
    graphics.setColor(prevColor);
  }

  public Graphics2D getGraphics() {
    return graphics;
  }

  public void setGraphics(Graphics2D graphics) {
    this.graphics = graphics;
  }

  @Override
  public RenderContext getRenderContext() {
    return renderContext;
  }

  public StyleDeclaration getVendorStyles() {
    return vendorStyles;
  }
}
