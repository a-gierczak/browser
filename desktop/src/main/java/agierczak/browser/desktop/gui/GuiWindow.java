package agierczak.browser.desktop.gui;

import agierczak.browser.core.css.CssException;
import agierczak.browser.core.dom.Document;
import agierczak.browser.core.render.EventLoop;
import agierczak.browser.core.render.PaintException;
import agierczak.browser.core.render.RenderTree;
import agierczak.browser.desktop.render.AwtEventLoop;
import agierczak.browser.desktop.render.AwtRenderSurface;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class GuiWindow extends Frame {

  private Document document;
  private RenderTree renderTree;
  private AwtRenderSurface renderSurface;
  private EventLoop eventLoop;

  public GuiWindow() {
    super("Blank page");
    setSize(800, 600);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        try {
          if (eventLoop != null) {
            eventLoop.dispose();
          }
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }

        dispose();
        System.exit(0);
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(java.awt.event.MouseEvent e) {
        super.mouseMoved(e);

        int canvasX = e.getX() - getInsets().left;
        int canvasY = e.getY() - getInsets().top;

        document.onMouseMove(canvasX, canvasY);
      }
    });
  }

  public void openDocument(Document document) {
    this.document = document;
    document.setViewportWidth(getWidth() - getInsets().left - getInsets().right);
    setTitle(document.getTitle());

    renderSurface = new AwtRenderSurface();
    renderSurface.setGraphics((Graphics2D) getGraphics());

    eventLoop = new AwtEventLoop(this);

    try {
      renderTree = new RenderTree();
      renderTree.createDocumentLayout(renderSurface.getRenderContext(), document, eventLoop);
    } catch (CssException e) {
      exitWithError(e);
    }

    eventLoop.start();
  }

  public void open() {
    setVisible(true);
  }

  @Override
  public void paint(Graphics graphics) {
    if (renderTree == null || renderTree.getRootNode() == null) {
      return;
    }

    Graphics2D graphics2d = (Graphics2D) graphics;
    graphics2d.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics2d.translate(getInsets().left, getInsets().top);
    renderSurface.setGraphics(graphics2d);

    try {
      renderTree.getRootNode().paint(renderSurface);
    } catch (PaintException e) {
      exitWithError(e);
    }
  }

  private void exitWithError(Exception e) {
    e.printStackTrace();
    System.exit(1);
  }
}
