package agierczak.browser.desktop;

import agierczak.browser.core.dom.Document;
import agierczak.browser.core.dom.HTMLParser;
import agierczak.browser.desktop.gui.GuiWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class Main {

  public static void main(String[] args) throws IOException {
    StringBuilder htmlStringBuilder = new StringBuilder();
    List<String> lines = Files
        .readAllLines(Paths.get("examples/anchor.html"), Charset.defaultCharset());
    for (String line : lines) {
      htmlStringBuilder.append(line);
    }

    HTMLParser parser = new HTMLParser(htmlStringBuilder.toString());
    Document document = parser.parse();

    GuiWindow mainWindow = new GuiWindow();
    mainWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent e) {
        mainWindow.openDocument(document);
      }
    });
    mainWindow.open();
  }
}
