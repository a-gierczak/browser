package agierczak.browser.core.dom.element;

import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.layout.LayoutNode;

public class HTMLHeadElement extends Element {

  HTMLHeadElement(Document document) {
    super("head", document);
  }

  @Override
  public LayoutNode createLayoutNode() {
    return null;
  }
}
