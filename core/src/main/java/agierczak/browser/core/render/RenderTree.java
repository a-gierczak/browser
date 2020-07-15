package agierczak.browser.core.render;

import agierczak.browser.core.css.CssException;
import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.dom.Node;
import agierczak.browser.core.layout.LayoutNode;

public class RenderTree {

  private LayoutNode root;
  private RenderContext renderContext;
  private EventLoop eventLoop;

  public void createDocumentLayout(RenderContext context, Document document, EventLoop loop) throws CssException {
    renderContext = context;
    eventLoop = loop;
    Element documentElement = document.getDocumentElement();
    createLayoutNodes(documentElement);
    root = documentElement.getLayoutNode();
    root.layout();
  }

  private void createLayoutNodes(Node node) {
    LayoutNode layoutNode = node.createLayoutNode();
    if (layoutNode == null) {
      return;
    }

    layoutNode.setRenderContext(renderContext);
    layoutNode.setEventLoop(eventLoop);
    node.setLayoutNode(layoutNode);
    for (Node child : node.getChildren()) {
      createLayoutNodes(child);
    }
  }

  public RenderContext getRenderContext() {
    return renderContext;
  }

  public LayoutNode getRootNode() {
    return root;
  }
}
