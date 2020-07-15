package agierczak.browser.desktop.render;

import agierczak.browser.core.render.EventLoop;
import agierczak.browser.desktop.gui.GuiWindow;

public class AwtEventLoop extends EventLoop {
  private final GuiWindow window;

  public AwtEventLoop(GuiWindow window) {
    this.window = window;
  }

  @Override
  public void processNextEvent(RenderEvent event) {
    if (event == RenderEvent.Repaint) {
      synchronized (window) {
        // TODO: sprobowac zrobic jakis incremental repaint
        window.repaint();
      }
    }
  }
}
