package agierczak.browser.core.layout;

import agierczak.browser.core.dom.Element;
import agierczak.browser.core.dom.Node;

public class BlockLayout extends BoxModel {

  public BlockLayout(Node node) {
    super(node);
  }

  @Override
  protected void computeBaseSize(Element element) {
    super.computeBaseSize(element);

    Box boundingBox = getBoundingBox();
    BoxModel parentLayout = (BoxModel) getNode().getParentNode().getLayoutNode();
    boundingBox.width = Math.max(parentLayout.getInnerWidth(), boundingBox.width);
  }
}
