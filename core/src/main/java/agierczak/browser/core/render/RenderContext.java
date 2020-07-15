package agierczak.browser.core.render;

import agierczak.browser.core.css.StyleDeclaration;

public interface RenderContext {
  FontMetrics getFontMetrics();
  StyleDeclaration getVendorStyles();
}
