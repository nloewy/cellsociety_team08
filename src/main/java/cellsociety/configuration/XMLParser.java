package cellsociety.configuration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.io.*;
import java.util.*;

// References:
// https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
// https://mkyong.com/java/how-to-create-xml-file-in-java-dom/

public class XMLParser {
    private String type; // simulation type
    private String title; // simulation title
    private String author; // author of configuration file
    private String fileDescription; // description of configuration file
    private String displayDescription; // description to be displayed on GUI
    private int width; // number of columns
    private int height; // number of rows
    private String neighborhoodType; // adjacent or cardinal
    private ArrayList<Integer> states; // the state of each cell
    private Map<String, Double> parameters; // Hashmap mapping parameter names to their values

    // Constructor for the states ArrayList and parameters HashMap
    public XMLParser() {
        states = new ArrayList<>();
        parameters = new HashMap<>();
    }

    // Public getter and setter methods for all attributes
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getNeighborhoodType() {
        return neighborhoodType;
    }

    public void setNeighborhoodType(String neighborhoodType) {
        this.neighborhoodType = neighborhoodType;
    }

    public ArrayList<Integer> getStates() {
        return states;
    }

    public void setStates(ArrayList<Integer> states) {
        this.states = states;
    }

    public Map<String, Double> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Double> parameters) {
        this.parameters = parameters;
    }

    // read an XML configuration file, initializing all data fields in the XMLParser
    public void readXML(String path) {
        try {
            // create a new File object for the XML file
            File file = new File(path);

            // create a new instance of document builder factory that allows for a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // create an instance of document builder to parse the XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Root Node :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");
            // obtaining the simulation node containing all the configuration data
            Node simulationNode = doc.getElementsByTagName("simulation").item(0);

            // parse all configuration data
            Element eElement = (Element) simulationNode;
            type = eElement.getElementsByTagName("type").item(0).getTextContent();
            title = eElement.getElementsByTagName("title").item(0).getTextContent();
            author = eElement.getElementsByTagName("author").item(0).getTextContent();
            fileDescription = eElement.getElementsByTagName("file_description").item(0).getTextContent();
            displayDescription = eElement.getElementsByTagName("display_description").item(0).getTextContent();
            neighborhoodType = eElement.getElementsByTagName("neighborhood_type").item(0).getTextContent();
            width = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
            height = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());

            // Parse initial states
            String rawStates = eElement.getElementsByTagName("initial_states").item(0).getTextContent();
            parseStates(rawStates);

            // Parse parameters
            Node parametersNode = eElement.getElementsByTagName("parameters").item(0);
            Element parameterElement = (Element) parametersNode;
            NodeList parametersNodeList = parameterElement.getElementsByTagName("*");

            parseParameters(parametersNodeList);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // parse the parameters in the XML file, populating the hashmap that maps parameter names to values.
    private void parseParameters(NodeList parametersNodeList) {
        // iterate through the parameters node list to obtain the value for each parameter and create new entries in the parameters hashmap
        for (int i = 0; i < parametersNodeList.getLength(); i++) {
            Node parameterNode = parametersNodeList.item(i);
            String name = parameterNode.getNodeName();
            Double value = Double.parseDouble(parameterNode.getTextContent());
            parameters.put(name, value);
        }
    }

    // convert the states data received as a single String to an ArrayList of Integers.
    private void parseStates(String rawStates) {
        this.states.clear();
        String[] states = rawStates.split(" ");
        System.out.println(Arrays.toString(states));
        for (String state: states) {
            this.states.add(Integer.parseInt(state));
        }
    }

    // creating a new XML file, saving the current state the simulation the user is running
    public void createXML(String filename, String folderName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // create root element simulation
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("simulation");
        doc.appendChild(rootElement);

        addElement(doc, rootElement, "type", type);
        addElement(doc, rootElement, "title", title);
        addElement(doc, rootElement, "author", author);
        addElement(doc, rootElement, "file_description", fileDescription);
        addElement(doc, rootElement, "display_description", displayDescription);
        addElement(doc, rootElement, "width", String.valueOf(width));
        addElement(doc, rootElement, "height", String.valueOf(height));
        addElement(doc, rootElement, "neighborhood_type", neighborhoodType);

        // Convert Integer ArrayList recording the current states of cell to a single String
        ArrayList<String> states = new ArrayList<>();
        for (Integer cell: this.states) {
            states.add(String.valueOf(cell));
        }
        String convertedStatesData = String.join(" ", states);
        addElement(doc, rootElement, "initial_states", convertedStatesData);

        addElement(doc, rootElement, "parameters", null);

        // write document to a new file
        try (FileOutputStream output =
                     new FileOutputStream("data/" + folderName + "/" + filename + ".xml")) {
            writeXml(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // add element under a root element when creating a new XML file
    private void addElement(Document doc, Element rootElement, String name, String content) {
        Element element = doc.createElement(name);
        if (name.equals("parameters")) {
            createParameters(doc, element);
        }
        else {
            element.setTextContent(content);
        }
        rootElement.appendChild(element);
    }

    // create the parameter root element in the XML file that contains a list of individual parameters each with their specified name and value.
    private void createParameters(Document doc, Element parameterElement) {
        for (String parameterName: parameters.keySet()) {
            addElement(doc, parameterElement, parameterName, String.valueOf(parameters.get(parameterName)));
        }
    }

    // write XML document to output stream
    private static void writeXml(Document doc, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // formatting XML file
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);

    }

}
