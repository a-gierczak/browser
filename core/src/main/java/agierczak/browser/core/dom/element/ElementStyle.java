package agierczak.browser.core.dom.element;

import agierczak.browser.core.css.StyleDeclaration;
import agierczak.browser.core.css.StyleValue;
import agierczak.browser.core.dom.Element;
import agierczak.browser.core.layout.LayoutNode;

public class ElementStyle extends StyleDeclaration {
  private Element owner;

  public ElementStyle(Element owner) {
    super();
    this.owner = owner;
  }

  public ElementStyle(Element owner, StyleDeclaration styles) {
    super(styles);
    this.owner = owner;
  }

  @Override
  public void setProperty(String propertyName, StyleValue propertyValue) {
    super.setProperty(propertyName, propertyValue);

    LayoutNode layoutNode = owner.getLayoutNode();
    if (layoutNode != null) {
      layoutNode.setPaintValid(false);
    }
  }
}
