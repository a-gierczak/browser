package agierczak.browser.core.dom;

import agierczak.browser.core.layout.LayoutNode;
import agierczak.browser.core.layout.LayoutText;

public class TextNode extends Node {

  public TextNode(Node rootNode) {
    super("#text", rootNode);
  }

  @Override
  public LayoutNode createLayoutNode() {
    return new LayoutText(this);
  }
}
