package agierczak.browser.core.dom;

import agierczak.browser.core.css.StyleParser;
import agierczak.browser.core.dom.element.ElementFactory;
import agierczak.browser.core.util.Parser;

public final class HTMLParser extends Parser<Document> {

  public HTMLParser(String string) {
    super(string);
  }

  private enum State {
    Free,
    BeforeTagName,
    InTagName,
    InOpeningTag,
    InClosingTag,
    BeforeAttributeName,
    InAttributeValue,
  }

  private boolean isValidNameChar(char ch) {
    return Character.isLetter(ch) || Character.isDigit(ch) || ch == '-';
  }

  @Override
  public Document parse() {
    State state = State.Free;
    Document document = new Document();
    Node currentNode = document;
    String currentAttributeName = null;

    while (peek(idx) != null) {
      switch (state) {
        case Free: {
          if (currentNode == document) {
            consumeUntil('<');
            idx++;

            if (peek() == null) {
              return document;
            }
          } else {
            String text = collectUntil('<').trim();
            idx++;
            if (text.length() > 0) {
              Node textNode = new TextNode(document);
              textNode.setNodeValue(text);
              currentNode.appendChild(textNode);
            }
          }
          state = State.BeforeTagName;
          break;
        }

        case BeforeTagName: {
          Character ch = peek();

          if (ch == '!') {
            // inside doctype
            consumeUntil('>');
            idx++;
            state = State.Free;
            break;
          }

          if (ch == '/') {
            idx++;
            state = State.InClosingTag;
            break;
          }

          state = State.InTagName;
          break;
        }

        case InTagName: {
          String nodeName = collectUntil(ch -> !isValidNameChar(ch));
          Node childNode = ElementFactory.createElement(document, nodeName);
          currentNode.appendChild(childNode);
          currentNode = childNode;
          Character ch = peek();
          if (ch != null && ch != '>') {
            state = State.InOpeningTag;
          } else {
            state = State.Free;
            idx++;
          }
          break;
        }

        case InOpeningTag: {
          consumeUntil(ch -> isValidNameChar(ch) || ch == '>');

          if (peek() == '>') {
            state = State.Free;
            idx++;
          } else {
            state = State.BeforeAttributeName;
          }
          break;
        }

        case BeforeAttributeName: {
          String attributeName = collectUntil(ch -> !isValidNameChar(ch));

          if (peek() == '=') {
            idx += 2; // consume '=' and '"'
            currentAttributeName = attributeName;
            state = State.InAttributeValue;
            break;
          }

          // attribute without value (eg. disabled)
          if (peek() == '>' || peek() == ' ') {
            ((Element) currentNode).setAttribute(attributeName, attributeName);
          }

          if (peek() == '>') {
            idx++;
            state = State.Free;
            break;
          }

          if (peek() == ' ') {
            state = State.InOpeningTag;
            break;
          }

          break;
        }

        case InAttributeValue: {
          String attributeValue = collectUntil(ch -> ch == '"');
          Element currentElement = ((Element) currentNode);
          currentElement.setAttribute(currentAttributeName, attributeValue);

          if (currentAttributeName.equals("id")) {
            document.registerElementId(attributeValue, currentElement);
          }

          if (currentAttributeName.equals("style")) {
            StyleParser styleParser = new StyleParser(attributeValue);
            currentElement.setStyle(styleParser.parse());
          }

          idx++;
          currentAttributeName = null;
          state = State.InOpeningTag;
          break;
        }

        case InClosingTag: {
          String tagName = collectUntil('>');
          if (!tagName.equals(currentNode.getNodeName())) {
            String message = String.format("Unmatched closing tag: %s. %s expected", tagName,
                currentNode.getNodeName());
            throw new RuntimeException(message);
          }
          currentNode = currentNode.getParentNode();
          state = State.Free;
          idx++;
          break;
        }
      }
    }

    return document;
  }
}
