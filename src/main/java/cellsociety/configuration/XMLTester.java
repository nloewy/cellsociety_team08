package cellsociety.configuration;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;

public class XMLTester {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        String path = "data/gameoflife/GameOfLifeTest1.xml";

        XMLParser xmlParser = new XMLParser();
        xmlParser.readXML(path);

        ArrayList<Integer> states = xmlParser.getStates();
        states.remove(states.size()-1);
        states.add(1);

        xmlParser.createXML("testSavedXML", "gameoflife");

    }
}
