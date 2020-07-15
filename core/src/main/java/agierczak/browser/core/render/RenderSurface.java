package agierczak.browser.core.render;

import agierczak.browser.core.css.CssException;
import agierczak.browser.core.layout.Box;
import agierczak.browser.core.css.StyleDeclaration;

public interface RenderSurface {
  RenderContext getRenderContext();
  void drawRect(Box box, StyleDeclaration style) throws CssException;
  void drawText(String text, float x, float y, StyleDeclaration style) throws CssException;
}
