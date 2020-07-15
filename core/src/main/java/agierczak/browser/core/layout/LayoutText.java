package agierczak.browser.core.layout;

import agierczak.browser.core.css.CssException;
import agierczak.browser.core.css.LengthStyleValue;
import agierczak.browser.core.css.MissingFallbackStyleException;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.dom.TextNode;
import agierczak.browser.core.css.StyleDeclaration;
import agierczak.browser.core.css.StyleValue;
import agierczak.browser.core.css.StyleValue.ValueType;
import agierczak.browser.core.render.RenderSurface;

public class LayoutText extends LayoutNode {

  public LayoutText(TextNode node) {
    super(node);
  }

  private StyleDeclaration getFontStyle() {
    return ((Element) getNode().getParentNode()).getStyle();
  }

  @Override
  public void paint(RenderSurface canvas) throws CssException {
    canvas.drawText(
        getNode().getNodeValue(),
        getBoundingBox().x,
        getBoundingBox().y,
        getFontStyle()
    );
  }

  @Override
  public void layout() throws MissingFallbackStyleException {
    Box boundingBox = getBoundingBox();
    boundingBox.width = getRenderContext().getFontMetrics().measureWidth(
        getNode().getNodeValue(),
        getFontStyle()
    );

    StyleValue lineHeightStyle = getFontStyle()
        .getPropertyValue("line-height", ValueType.Length);

    if (lineHeightStyle == null) {
      lineHeightStyle = getRenderContext().getVendorStyles()
          .getPropertyValue("line-height", ValueType.Length);
    }

    System.out.println(boundingBox.width);
    boundingBox.height = ((LengthStyleValue) lineHeightStyle).getLength();
  }
}
