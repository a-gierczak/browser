package agierczak.browser.core.dom.element;

import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.dom.Node;
import agierczak.browser.core.layout.ContentsLayout;
import agierczak.browser.core.layout.LayoutNode;

public class HTMLHtmlElement extends Element {

  Document document;

  HTMLHtmlElement(Document document) {
    super("html", document);
    this.document = document;
  }

  @Override
  public void appendChild(Node node) {
    super.appendChild(node);

    if (node instanceof HTMLBodyElement) {
      document.setBody((Element) node);
    } else if (node instanceof HTMLHeadElement) {
      document.setHead((Element) node);
    }
  }

  @Override
  public LayoutNode createLayoutNode() {
    LayoutNode layoutNode = new ContentsLayout(this);
    layoutNode.setWidth(document.getViewportWidth());
    return layoutNode;
  }
}
