package agierczak.browser.desktop.render;

import agierczak.browser.core.css.MissingFallbackStyleException;
import agierczak.browser.core.css.StyleDeclaration;
import agierczak.browser.core.render.FontMetrics;
import java.awt.Font;
import java.awt.Graphics2D;

public final class AwtFontMetrics implements FontMetrics {
  private AwtRenderContext context;

  public AwtFontMetrics(AwtRenderContext renderContext) {
    context = renderContext;
  }

  @Override
  public float measureWidth(String text, StyleDeclaration style)
      throws MissingFallbackStyleException {
    if (text.equals("")) {
      return 0;
    }

    Graphics2D graphics = ((AwtRenderSurface) context.getRenderSurface()).getGraphics();
    Font font = FontResolver.getInstance().resolve(style, context.getVendorStyles());
    return graphics.getFontMetrics(font).stringWidth(text);
  }
}
