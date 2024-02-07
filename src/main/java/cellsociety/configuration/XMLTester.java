package cellsociety.configuration;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * The XMLTester is a class for testing operations on objects of the XMLParser class, particularly
 * its readXML and writeXML methods.
 *
 * @author Judy He
 */
public class XMLTester {

  public static void main(String[] args) throws ParserConfigurationException, TransformerException {
    String path = "data/gameoflife/GameOfLifeGlider.xml";

    XMLParser xmlParser = new XMLParser();
    xmlParser.readXML(path);

    List<Integer> states = xmlParser.getStates();
    states.remove(states.size() - 1);
    states.add(1);

    xmlParser.createXML("testSavedXML", "gameoflife");

  }
}
