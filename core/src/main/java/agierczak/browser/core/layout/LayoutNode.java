package agierczak.browser.core.layout;

import agierczak.browser.core.css.CssException;
import agierczak.browser.core.dom.Node;
import agierczak.browser.core.render.EventLoop;
import agierczak.browser.core.render.PaintException;
import agierczak.browser.core.render.RenderContext;
import agierczak.browser.core.render.RenderSurface;

public abstract class LayoutNode {

  public abstract void paint(RenderSurface canvas) throws PaintException;

  public abstract void layout() throws CssException;

  private Box boundingBox;
  private Node node;
  private RenderContext renderContext;
  private EventLoop eventLoop;
  private boolean isLayoutValid;
  private boolean isPaintValid;

  public LayoutNode(Node node) {
    this.node = node;
    this.boundingBox = new Box();
    this.isLayoutValid = false;
    this.isPaintValid = false;
  }

  public void setRenderContext(RenderContext context) {
    renderContext = context;
  }

  public EventLoop getEventLoop() {
    return eventLoop;
  }

  public void setEventLoop(EventLoop loop) {
    eventLoop = loop;
  }

  protected RenderContext getRenderContext() {
    return renderContext;
  }

  public Box getBoundingBox() {
    return boundingBox;
  }

  public void setBoundingBox(Box boundingBox) {
    this.boundingBox = boundingBox;
  }

  public Node getNode() {
    return node;
  }

  public void invalidateLayout() {
    this.isLayoutValid = false;
  }

  protected void setLayoutValid() {
    this.isLayoutValid = true;
  }

  public void setWidth(float width) {
    getBoundingBox().width = width;
    invalidateLayout();
  }

  public boolean shouldRepaint() {
    return !isPaintValid;
  }

  public void setPaintValid(boolean value) {
    isPaintValid = value;

    if (!value) {
      eventLoop.enqueueRepaint();
    }
  }
}
