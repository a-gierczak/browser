package agierczak.browser.core.dom;

import agierczak.browser.core.dom.event.EventDispatcher;
import agierczak.browser.core.dom.event.EventType;
import agierczak.browser.core.dom.event.MouseEvent;

public final class MouseEventDispatcher implements EventDispatcher {
  private Node hoveredNode;
  private final NodeCollection eventTargets = new NodeCollection();

  public void onMouseMove(int mouseX, int mouseY) {
    if (hoveredNode != null) {
      if (hoveredNode.getLayoutNode().getBoundingBox().contains(mouseX, mouseY)) {
        MouseEvent mouseEvent = new MouseEvent(EventType.MouseMove, mouseX, mouseY);
        hoveredNode.dispatchEvent(mouseEvent);
        return;
      }

      MouseEvent mouseEvent = new MouseEvent(EventType.MouseLeave, mouseX, mouseY);
      hoveredNode.dispatchEvent(mouseEvent);
      hoveredNode = null;
    }

    // mouse enter
    for (Node node : eventTargets) {
      if (node.getLayoutNode().getBoundingBox().contains(mouseX, mouseY)) {
        hoveredNode = node;
        hoveredNode.dispatchEvent(new MouseEvent(EventType.MouseEnter, mouseX, mouseY));
        return;
      }
    }
  }

  @Override
  public void addEventTarget(EventTarget target) {
    eventTargets.add((Node) target);
  }
}
