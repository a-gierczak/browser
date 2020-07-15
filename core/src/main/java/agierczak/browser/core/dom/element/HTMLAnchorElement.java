package agierczak.browser.core.dom.element;

import agierczak.browser.core.css.ColorStyleValue;
import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.layout.BlockLayout;
import agierczak.browser.core.layout.LayoutNode;
import agierczak.browser.core.css.Color;
import agierczak.browser.core.dom.event.EventType;

public class HTMLAnchorElement extends Element {

  private ColorStyleValue defaultColorStyle = new ColorStyleValue(Color.BLUE);
  private ColorStyleValue hoverColorStyle = new ColorStyleValue(new Color(121, 153, 248));

  HTMLAnchorElement(Document document) {
    super("a", document);
    getStyle().setProperty("color", defaultColorStyle);

    addEventListener(EventType.MouseEnter, (event) -> {
      getStyle().setProperty("color", hoverColorStyle);
    });

    addEventListener(EventType.MouseLeave, (event) -> {
      getStyle().setProperty("color", defaultColorStyle);
    });

    document.addMouseEventTarget(this);
  }

  @Override
  public LayoutNode createLayoutNode() {
    return new BlockLayout(this);
  }
}
