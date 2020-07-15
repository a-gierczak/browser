package agierczak.browser.core.dom.event;

public class MouseEvent implements Event {

  private EventType eventType;
  private float mouseX;
  private float mouseY;

  public MouseEvent(EventType eventType, float mouseX, float mouseY) {
    this.eventType = eventType;
    this.mouseX = mouseX;
    this.mouseY = mouseY;
  }

  @Override
  public EventType getType() {
    return eventType;
  }

  public float getMouseX() {
    return mouseX;
  }

  public float getMouseY() {
    return mouseY;
  }
}
