package agierczak.browser.core.dom;

import agierczak.browser.core.dom.event.Event;
import agierczak.browser.core.dom.event.EventType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public abstract class EventTarget {
  private HashMap<EventType, List<Consumer<Event>>> listeners = new HashMap<>();

  public void addEventListener(EventType eventType, Consumer<Event> listener) {
    List<Consumer<Event>> listenersForType = listeners.get(eventType);
    if (listenersForType == null) {
      listenersForType = new LinkedList<>();
    }

    listenersForType.add(listener);
    listeners.put(eventType, listenersForType);
  }

  public void dispatchEvent(Event event) {
    List<Consumer<Event>> listenersForType = listeners.get(event.getType());
    if (listenersForType == null) {
      return;
    }

    for (Consumer<Event> listener : listenersForType) {
      listener.accept(event);
    }
  }
}
