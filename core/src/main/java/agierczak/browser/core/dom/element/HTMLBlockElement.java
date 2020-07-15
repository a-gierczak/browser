package agierczak.browser.core.dom.element;

import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.layout.BlockLayout;
import agierczak.browser.core.layout.LayoutNode;

/**
 * TODO: to jest tymczasowa klasa, powinna zostac zastapiona przez odpowiednie typy elementow tj.
 * HTMLDivElement itd.
 */
public class HTMLBlockElement extends Element {

  HTMLBlockElement(String nodeName, Document document) {
    super(nodeName, document);
  }

  @Override
  public LayoutNode createLayoutNode() {
    LayoutNode layoutNode = new BlockLayout(this);
    return layoutNode;
  }
}
