package agierczak.browser.core.dom.element;

import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.Element;

public final class ElementFactory {

  private ElementFactory() {
  }

  public static Element createElement(Document document, String nodeName) {
    switch (nodeName) {
      case "html":
        return new HTMLHtmlElement(document);

      case "body":
        return new HTMLBodyElement(document);

      case "head":
        return new HTMLHeadElement(document);

      case "a":
        return new HTMLAnchorElement(document);

      default:
        return new HTMLBlockElement(nodeName, document);
    }
  }
}
