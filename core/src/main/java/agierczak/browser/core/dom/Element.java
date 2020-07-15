package agierczak.browser.core.dom;

import agierczak.browser.core.dom.element.ElementStyle;
import agierczak.browser.core.css.StyleDeclaration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Element extends Node {

  private Map<String, String> attributes;
  private ElementStyle styles;

  public Element(String nodeName, Node rootNode) {
    super(nodeName, rootNode);
    styles = new ElementStyle(this);
  }

  public ElementStyle getStyle() {
    return styles;
  }

  public String getAttribute(String key) {
    if (attributes == null) {
      return null;
    }
    return attributes.get(key);
  }

  public void setAttribute(String key, String value) {
    if (attributes == null) {
      attributes = new HashMap<>();
    }

    attributes.put(key, value);
  }

  public void setStyle(StyleDeclaration styles) {
    this.styles = new ElementStyle(this, styles);
  }

  private void appendChildrenWithTagName(List<Element> elements, String tagName) {
    if (getChildren() == null) {
      return;
    }

    getChildren().forEach(node -> {
      if (!(node instanceof Element)) {
        return;
      }

      Element element = (Element) node;
      if (node.getNodeName().equals(tagName)) {
        elements.add(element);
      }

      element.appendChildrenWithTagName(elements, tagName);
    });
  }

  public List<Element> getElementsByTagName(String tagName) {
    List<Element> result = new LinkedList<>();
    appendChildrenWithTagName(result, tagName);
    return result;
  }

  // TODO: to powinno dzialac jakos rekurencyjnie chyba
  public String getInnerText() {
    if (getChildren() == null) {
      return "";
    }

    return getChildren()
        .toList()
        .stream()
        .filter(node -> node.getNodeName().equals("#text"))
        .map(Node::getNodeValue)
        .findFirst()
        .orElseGet(() -> "");
  }

  @Override
  public String toString() {
    if (getAttribute("id") != null) {
      return "#" + getAttribute("id");
    }

    if (getAttribute("class") != null) {
      return "." + getAttribute("class");
    }

    return super.toString();
  }
}
