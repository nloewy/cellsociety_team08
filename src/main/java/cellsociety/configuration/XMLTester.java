package cellsociety.configuration;

import java.io.InputStream;

public class XMLTester {
    public static void main(String[] args) {
        String path = "data/gameoflife/GameOfLifeTest1.xml";

        XMLParser xmlParser = new XMLParser();
        xmlParser.readXML(path);
        System.out.println(xmlParser.getType());
        System.out.println(xmlParser.getTitle());
        System.out.println(xmlParser.getAuthor());
        System.out.println(xmlParser.getWidth());
        System.out.println(xmlParser.getHeight());
    }
}
