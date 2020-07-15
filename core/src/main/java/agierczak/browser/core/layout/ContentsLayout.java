package agierczak.browser.core.layout;

import agierczak.browser.core.css.CssException;
import agierczak.browser.core.dom.Node;
import agierczak.browser.core.render.PaintException;
import agierczak.browser.core.render.RenderSurface;

/**
 * Special use case LayoutNode, which only holds information about it's size. It's used only by
 * HTMLHtmlElement. So body can get width from document.
 */
public class ContentsLayout extends BoxModel {

  public ContentsLayout(Node node) {
    super(node);
  }

  @Override
  public void paint(RenderSurface canvas) throws PaintException {
    for (Node child : getNode().getChildren()) {
      if (child.getLayoutNode() == null) {
        continue;
      }
      child.getLayoutNode().paint(canvas);
    }
  }

  @Override
  public void layout() throws CssException {
    for (Node child : getNode().getChildren()) {
      if (child.getLayoutNode() == null) {
        continue;
      }
      child.getLayoutNode().layout();
    }
  }
}
