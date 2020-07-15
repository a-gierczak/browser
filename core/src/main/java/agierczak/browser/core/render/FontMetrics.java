package agierczak.browser.core.render;

import agierczak.browser.core.css.MissingFallbackStyleException;
import agierczak.browser.core.css.StyleDeclaration;

public interface FontMetrics {
  float measureWidth(String text, StyleDeclaration style) throws MissingFallbackStyleException;
}
