package agierczak.browser.core.dom;

import java.util.Iterator;
import java.util.Stack;

public class NodeTraverseIterator implements Iterator<Node> {
  private Stack<NodeCollection> nestedChildren = new Stack<>();
  private NodeCollection remainingDirectChildren;

  public NodeTraverseIterator(NodeCollection children) {
    remainingDirectChildren = new NodeCollection(children);
  }

  @Override
  public boolean hasNext() {
    return !nestedChildren.empty() || !remainingDirectChildren.isEmpty();
  }

  // leci wszerz
  @Override
  public Node next() {
    if (remainingDirectChildren.isEmpty()) {
      remainingDirectChildren = nestedChildren.pop();
      return next();
    }

    Node nextNode = remainingDirectChildren.pop();
    NodeCollection nodeChildren = nextNode.getChildren();
    if (!nodeChildren.isEmpty()) {
      nestedChildren.push(nodeChildren);
    }

    return nextNode;
  }
}
