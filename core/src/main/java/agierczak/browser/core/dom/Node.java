package agierczak.browser.core.dom;

import agierczak.browser.core.layout.LayoutNode;
import java.util.LinkedList;
import java.util.ListIterator;

public abstract class Node extends EventTarget {

  private final String nodeName;
  private String nodeValue;
  private NodeCollection children;
  private Node parentNode;
  private LayoutNode layoutNode;
  private Node rootNode;

  public abstract LayoutNode createLayoutNode();

  Node(String nodeName, Node rootNode) {
    this.nodeName = nodeName;
    children = new NodeCollection();
    setRootNode(rootNode);
  }

  public void appendChild(Node node) {
    children.add(node);
    node.setParentNode(this);
  }

  public Node getPreviousSibling() {
    if (parentNode == null) {
      return null;
    }

    LinkedList<Node> siblings = (LinkedList<Node>) parentNode.children.toList();
    ListIterator<Node> iterator = siblings.listIterator(siblings.indexOf(this));
    if (iterator.hasPrevious()) {
      return iterator.previous();
    } else {
      return null;
    }
  }

  public String getNodeValue() {
    return nodeValue;
  }

  public void setNodeValue(String value) {
    nodeValue = value;
  }

  public Node getParentNode() {
    return parentNode;
  }

  private void setParentNode(Node parentNode) {
    this.parentNode = parentNode;
  }

  public String getNodeName() {
    return nodeName;
  }

  public LayoutNode getLayoutNode() {
    return layoutNode;
  }

  public void setLayoutNode(LayoutNode layoutNode) {
    this.layoutNode = layoutNode;
  }

  public NodeCollection getChildren() {
    return children;
  }

  @Override
  public String toString() {
    return nodeName;
  }

  public Node getRootNode() {
    return rootNode;
  }

  public void setRootNode(Node rootNode) {
    this.rootNode = rootNode;
  }
}
