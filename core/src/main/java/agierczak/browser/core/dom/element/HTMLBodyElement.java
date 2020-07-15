package agierczak.browser.core.dom.element;

import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.layout.BlockLayout;
import agierczak.browser.core.layout.Box;
import agierczak.browser.core.layout.LayoutNode;

public class HTMLBodyElement extends Element {

  HTMLBodyElement(Document document) {
    super("body", document);
  }

  @Override
  public LayoutNode createLayoutNode() {
    LayoutNode layoutNode = new BlockLayout(this);
    Box boundingBox = layoutNode.getBoundingBox();
    boundingBox.x = 0;
    boundingBox.y = 0;
    return layoutNode;
  }
}
