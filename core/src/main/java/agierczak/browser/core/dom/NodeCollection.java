package agierczak.browser.core.dom;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class NodeCollection implements Iterable<Node> {

  private LinkedList<Node> nodes;

  public NodeCollection() {
    nodes = new LinkedList<>();
  }

  public NodeCollection(NodeCollection other) {
    nodes = new LinkedList<>(other.nodes);
  }

  public void add(Node node) {
    nodes.add(node);
  }

  public Node get(int index) {
    return nodes.get(index);
  }

  public Node pop() {
    return nodes.pop();
  }

  public int size() {
    return nodes.size();
  }

  public boolean isEmpty() {
    return nodes.isEmpty();
  }

  public List<Node> toList() {
    return nodes;
  }

  @Override
  public Iterator<Node> iterator() {
    return nodes.iterator();
  }

  @Override
  public void forEach(Consumer<? super Node> action) {
    nodes.forEach(action);
  }

  @Override
  public Spliterator<Node> spliterator() {
    return nodes.spliterator();
  }

  public NodeTraverseIterator traverse() {
    return new NodeTraverseIterator(this);
  }
}
