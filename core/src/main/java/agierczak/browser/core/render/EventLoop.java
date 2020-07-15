package agierczak.browser.core.render;

import java.util.LinkedList;
import java.util.Queue;

public abstract class EventLoop extends Thread {
  private Queue<RenderEvent> eventQueue = new LinkedList<>();
  private boolean finished = false;

  public enum RenderEvent {
    Repaint,
  }

  public abstract void processNextEvent(RenderEvent event);

  public synchronized void enqueueRepaint() {
    eventQueue.add(RenderEvent.Repaint);
  }

  public void dispose() throws InterruptedException {
    finished = true;
    join();
  }

  @Override
  public void run() {
    while (!finished) {
      if (!eventQueue.isEmpty()) {
        RenderEvent nextEvent = eventQueue.remove();
        processNextEvent(nextEvent);
        continue;
      }

      try {
        sleep(16);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
