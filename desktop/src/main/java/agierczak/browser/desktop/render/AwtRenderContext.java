package agierczak.browser.desktop.render;

import agierczak.browser.core.css.StyleDeclaration;
import agierczak.browser.core.render.FontMetrics;
import agierczak.browser.core.render.RenderContext;
import agierczak.browser.core.render.RenderSurface;

public class AwtRenderContext implements RenderContext {

  private AwtFontMetrics fontMetrics;
  private AwtRenderSurface renderSurface;

  public AwtRenderContext(RenderSurface surface) {
    renderSurface = (AwtRenderSurface) surface;
    fontMetrics = new AwtFontMetrics(this);
  }

  @Override
  public FontMetrics getFontMetrics() {
    return fontMetrics;
  }

  RenderSurface getRenderSurface() {
    return renderSurface;
  }

  @Override
  public StyleDeclaration getVendorStyles() {
    return renderSurface.getVendorStyles();
  }
}
