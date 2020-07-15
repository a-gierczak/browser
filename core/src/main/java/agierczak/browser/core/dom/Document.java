package agierczak.browser.core.dom;

import agierczak.browser.core.dom.element.HTMLHtmlElement;
import agierczak.browser.core.layout.LayoutNode;
import java.util.HashMap;
import java.util.List;

public class Document extends Node {

  private Element head;
  private Element body;
  private float viewportWidth;
  private HashMap<String, Element> elementById;
  private MouseEventDispatcher mouseEventDispatcher;

  public Document() {
    super("document", null);
    elementById = new HashMap<>();
    mouseEventDispatcher = new MouseEventDispatcher();
  }

  @Override
  public LayoutNode createLayoutNode() {
    return null;
  }

  public Element getElementById(String id) {
    return elementById.get(id);
  }

  public void registerElementId(String id, Element element) {
    if (elementById.containsKey(id)) {
//      Log.w("Document", "Element with id " + id + " already exists.");
      return;
    }

    elementById.put(id, element);
  }

  @Override
  public void appendChild(Node node) {
    super.appendChild(node);

    if (!(node instanceof HTMLHtmlElement)) {
      throw new RuntimeException("Only HTMLHtmlElement node can be direct child of Document.");
    }
  }

  public Element getHead() {
    return head;
  }

  public Element getBody() {
    return body;
  }

  public void setHead(Element head) {
    this.head = head;
  }

  public void setBody(Element body) {
    this.body = body;
  }

  public float getViewportWidth() {
    return viewportWidth;
  }

  public void setViewportWidth(float viewportWidth) {
    this.viewportWidth = viewportWidth;
  }

  public Element getDocumentElement() {
    return (Element) getChildren().get(0);
  }

  public String getTitle() {
    if (head == null) {
      return "";
    }

    List<Element> titleElements = head.getElementsByTagName("title");
    if (titleElements.size() < 1) {
      return "";
    }

    return titleElements.get(0).getInnerText();
  }

  public void onMouseMove(int mouseX, int mouseY) {
    mouseEventDispatcher.onMouseMove(mouseX, mouseY);
  }

  public void addMouseEventTarget(EventTarget eventTarget) {
    mouseEventDispatcher.addEventTarget(eventTarget);
  }
}
