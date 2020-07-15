package agierczak.browser.core.layout;

import agierczak.browser.core.css.CssException;
import agierczak.browser.core.css.LengthStyleValue;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.dom.Node;
import agierczak.browser.core.dom.NodeCollection;
import agierczak.browser.core.render.PaintException;
import agierczak.browser.core.css.StyleDeclaration;
import agierczak.browser.core.css.StyleValue;
import agierczak.browser.core.css.StyleValue.ValueType;
import agierczak.browser.core.render.RenderSurface;

/**
 * Box model follows border-box (IE) implementation
 */
public class BoxModel extends LayoutNode {

  public BoxModel(Node node) {
    super(node);
  }

  public Box getContentBox() {
    Node node = getNode();
    Box boundingBox = getBoundingBox();
    if (!(node instanceof Element)) {
      return boundingBox;
    }

    return boundingBox.shrink(getContentBoxInset());
  }

  private Box contentBoxToBoundingBox(Box contentBox) {
    Node node = getNode();
    if (!(node instanceof Element)) {
      return contentBox;
    }

    return contentBox.expand(getContentBoxInset());
  }

  private int getContentBoxInset() {
    int result = 0;
    StyleDeclaration styles = ((Element) getNode()).getStyle();
    StyleValue padding = styles.getPropertyValue("padding", ValueType.Length);
    StyleValue borderWidth = styles.getPropertyValue("borderWidth", ValueType.Length);

    if (padding != null) {
      result += ((LengthStyleValue) padding).getLength();
    }

    if (borderWidth != null) {
      result += ((LengthStyleValue) borderWidth).getLength();
    }

    return result;
  }

  protected float getInnerPositionX() {
    return getContentBox().x;
  }

  protected float getInnerPositionY() {
    return getContentBox().y;
  }

  public float getInnerWidth() {
    return getContentBox().width;
  }

  /**
   * PARENT ustawia x,y dla childow CHILD liczy swoj height finalnie PARENT liczy laczny height
   */

  protected void computeBaseSize(Element element) {
    Box boundingBox = getBoundingBox();

    boundingBox.width += getContentBoxInset() * 2;
    boundingBox.height += getContentBoxInset() * 2;
  }

  private void computeChildrenLayout() throws CssException {
    NodeCollection children = getNode().getChildren();
    LayoutNode lastChildLayout = null;
    Box contentBox = getContentBox();

    for (Node child : children) {
      LayoutNode childLayout = child.getLayoutNode();
      if (childLayout == null) {
        continue;
      }

      Box childBoundingBox = childLayout.getBoundingBox();
      childBoundingBox.x = getInnerPositionX();
      boolean isFirstChild = lastChildLayout == null;
      if (isFirstChild) {
        childBoundingBox.y = getInnerPositionY();
      } else {
        Box lastChildBoundingBox = lastChildLayout.getBoundingBox();
        childBoundingBox.y = lastChildBoundingBox.y + lastChildBoundingBox.height;
      }

      childLayout.layout();
      contentBox.width = Math.max(
          contentBox.width,
          childLayout.getBoundingBox().width
      );
      contentBox.height += childBoundingBox.height;
      lastChildLayout = childLayout;
    }

    Box newBoundingBox = contentBoxToBoundingBox(contentBox);
    setBoundingBox(newBoundingBox);
  }

  @Override
  public void layout() throws CssException {
    Node node = getNode();
    NodeCollection children = node.getChildren();
    Box boundingBox = getBoundingBox();

    boundingBox.width = 0;
    boundingBox.height = 0;

    if (node instanceof Element) {
      computeBaseSize((Element) node);
    }

    if (children != null && children.size() > 0) {
      computeChildrenLayout();
    }
  }

  @Override
  public void paint(RenderSurface canvas) throws PaintException {
    Node node = getNode();
    if (node instanceof Element) {
      StyleDeclaration style = ((Element) node).getStyle();
      canvas.drawRect(getBoundingBox(), style);
    }

    for (Node child : getNode().getChildren()) {
      child.getLayoutNode().paint(canvas);
    }
  }
}
