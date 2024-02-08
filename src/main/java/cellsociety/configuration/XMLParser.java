package cellsociety.configuration;


import cellsociety.exception.InputMissingParametersException;
import cellsociety.exception.InvalidFileFormatException;
import cellsociety.exception.InvalidGridBoundsException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * The XMLParser reads and stores all data from a given XML configuration file. Using on the values
 * stored in its attributes, the XMLParser may also write and save a new XML configuration file.
 *
 * @author Judy He
 */

public class XMLParser {

  public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.configuration.";
  private ResourceBundle resourceBundle;
  private String type; // simulation type
  private String title; // simulation title
  private String author; // author of configuration file
  private String fileDescription; // description of configuration file
  private String displayDescription; // description to be displayed on GUI
  private String stateColor; // states and corresponding colors of the simulation to be displayed on GUI
  private int width; // number of columns
  private int height; // number of rows
  private String neighborhoodType; // adjacent or cardinal
  private List<Integer> states; // the state of each cell
  private Map<String, Double> parameters; // Hashmap mapping parameter names to their values
  private String language;
  private String cellShape;
  private String gridEdgeType;
  private Map<String, Integer> randomConfigurationTotalStates;

  /**
   * Constructor for initializing the states ArrayList and parameters HashMap
   */
  public XMLParser() {
    states = new ArrayList<>();
    parameters = new HashMap<>();
    randomConfigurationTotalStates = new HashMap<>();
    cellShape = "square";
    gridEdgeType = "Normal";
    language = "English";
    // use resources for errors
    resourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Errors" + language);
  }

  /**
   * Retrieves type instance variable
   *
   * @return type, the type of simulation currently being run.
   */
  public String getType() {
    return type;
  }

  /**
   * Updates type instance variable
   *
   * @param type, the type of simulation currently being run.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Retrieves title instance variable
   *
   * @return title, the title of the simulation currently being run.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Updates title instance variable
   *
   * @param title, the title of the simulation currently being run.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Retrieves author instance variable
   *
   * @return author, the author of the XML configuration file for the simulation currently being
   * run.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Updates author instance variable
   *
   * @param author, the author of the XML configuration file for the simulation currently being
   *                run.
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Retrieves fileDescription instance variable
   *
   * @return fileDescription, the description of the XML configuration file currently being read.
   */
  public String getFileDescription() {
    return fileDescription;
  }

  /**
   * Updates fileDescription instance variable
   *
   * @param fileDescription, the description of the XML configuration file currently being read.
   */
  public void setFileDescription(String fileDescription) {
    this.fileDescription = fileDescription;
  }

  /**
   * Retrieves displayDescription instance variable
   *
   * @return displayDescription, the description of the simulation currently being run.
   */
  public String getDisplayDescription() {
    return displayDescription;
  }

  /**
   * Updates displayDescription instance variable
   *
   * @param displayDescription, the description of the simulation currently being run.
   */
  public void setDisplayDescription(String displayDescription) {
    this.displayDescription = displayDescription;
  }

  /**
   * Retrieves width instance variable
   *
   * @return width, the number of rows in the grid used for the current simulation.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Updates width instance variable
   *
   * @param width, the number of rows in the grid used for the current simulation.
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Retrieves height instance variable
   *
   * @return height, the number of columns in the grid used for the current simulation.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Updates height instance variable
   *
   * @param height, the number of columns in the grid used for the current simulation.
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Retrieves neighborHoodType instance variable
   *
   * @return neighborhoodType, the type of neighborhood interaction (cardinal or adjacent) used by
   * the current simulation.
   */
  public String getNeighborhoodType() {
    return neighborhoodType;
  }

  /**
   * Updates neighborHoodType instance variable
   *
   * @param neighborhoodType, the type of neighborhood interaction (cardinal or adjacent) used by
   *                          the current simulation.
   */
  public void setNeighborhoodType(String neighborhoodType) {
    this.neighborhoodType = neighborhoodType;
  }

  public String getCellShape() {
    return cellShape;
  }

  public String getGridEdgeType() {
    return gridEdgeType;
  }

  /**
   * Retrieves states instance variable
   *
   * @return states, an ArrayList keeping track of the state of each cell in the simulation.
   */
  public List<Integer> getStates() {
    return states;
  }

  /**
   * Updates states instance variable
   *
   * @param states, an ArrayList keeping track of the state of each cell in the simulation.
   */
  public void setStates(ArrayList<Integer> states) {
    this.states = states;
  }

  public String getStateColor() {
    return stateColor;
  }

  /**
   * Retrieves parameters instance variable
   *
   * @return parameters, a HashMap mapping parameter names to their corresponding values
   */
  public Map<String, Double> getParameters() {
    return parameters;
  }

  /**
   * Updates parameters instance variable
   *
   * @param parameters, a HashMap mapping parameter names to their corresponding values
   */
  public void setParameters(Map<String, Double> parameters) {
    this.parameters = parameters;
  }
  public ResourceBundle getResourceBundle() {
    return resourceBundle;
  }

  public void setResourceBundle(ResourceBundle resourceBundle) {
    this.resourceBundle = resourceBundle;
  }

  public void setStateColor(String stateColor) {
    this.stateColor = stateColor;
  }

  public void setStates(List<Integer> states) {
    this.states = states;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setCellShape(String cellShape) {
    this.cellShape = cellShape;
  }

  public void setGridEdgeType(String gridEdgeType) {
    this.gridEdgeType = gridEdgeType;
  }

  public Map<String, Integer> getRandomConfigurationTotalStates() {
    return randomConfigurationTotalStates;
  }

  public void setRandomConfigurationTotalStates(
      Map<String, Integer> randomConfigurationTotalStates) {
    this.randomConfigurationTotalStates = randomConfigurationTotalStates;
  }

  /**
   * Read an XML configuration file, initializing all attributes in the XMLParser
   *
   * References: https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
   * https://www.edankert.com/validate.html
   *
   * @param path, the path to the XML configuration file being read
   * @throws InvalidFileFormatException, when the user loads a configuration file that has
   *                                     empty, badly formatted, or non-XML file,
   * @throws InputMissingParametersException, when missing essential parameters or height and width in the configuration file
   * @throws InvalidGridBoundsException, when the user loads a configuration file that has
   *                                     cell locations specified outside the grid’s bounds
   */
  public void readXML(String path) throws InvalidFileFormatException, InputMissingParametersException, InvalidGridBoundsException {
    try {
      // create a new File object for the XML file
      File file = new File(path);
      if (!getFileExtension(file.getName()).equals("xml")) {
        throw new InvalidFileFormatException(
            String.format(resourceBundle.getString("InvalidFileType"), path));
      }

      // create a new instance of document builder factory that allows for a document builder
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      // checking for well-formatted XML
      dbf.setValidating(false);
      dbf.setNamespaceAware(true);

      // create an instance of document builder to parse the XML file
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      doc.getDocumentElement().normalize();

      // obtaining the simulation node containing all the configuration data
      Node simulationNode = doc.getElementsByTagName("simulation").item(0);
      // check if file is empty
      if (simulationNode == null) {
        throw new InvalidFileFormatException(
            String.format(resourceBundle.getString("EmptyXMLFile"), path));
      }

      // parse all configuration data
      Element eElement = (Element) simulationNode;
      parseSingleFields(eElement);

      // check for empty essential inputs
      if (type.isEmpty() || title.isEmpty() || author.isEmpty() || fileDescription.isEmpty() || displayDescription.isEmpty() || stateColor.isEmpty()) {
        throw new InputMissingParametersException(String.format(resourceBundle.getString("MissingParameter"), path));
      }

      // check if both width and height are not given
      if (width == 0 && height == 0) {
        throw new InputMissingParametersException(String.format(resourceBundle.getString("MissingWidthAndHeight"), path));
      }

      // parse initial states
      String rawStates = eElement.getElementsByTagName("initial_states").item(0).getTextContent();
      parseStates(rawStates);
      int totalNumStates = states.size();


      // parse parameters
      Node parametersNode = eElement.getElementsByTagName("parameters").item(0);
      Element parameterElement = (Element) parametersNode;
      NodeList parametersNodeList = parameterElement.getElementsByTagName("*");
      parseParameters(parametersNodeList);

      // parse random configuration states
      Node randomConfigNode = eElement.getElementsByTagName("random_configuration_by_total_states")
          .item(0);
      Element randomConfigElement = (Element) randomConfigNode;
      NodeList randomConfigNodeList = randomConfigElement.getElementsByTagName("*");
      if (randomConfigNodeList.getLength() != 0) {
        parseRandomConfig(randomConfigNodeList);
        for (Integer value: randomConfigurationTotalStates.values()) {
          totalNumStates += value;
        }
      }

      // check if only width or height is given. If so, update the other based on the total number of states read
      if (width == 0) {
        width = totalNumStates / height;
      }
      else if (height == 0) {
        height = totalNumStates / width;
      }

      // Check if grid dimension is valid, throw exception otherwise
      if (randomConfigurationTotalStates.isEmpty() && width * height != states.size()) {
        throw new InvalidGridBoundsException(
            String.format(resourceBundle.getString("InvalidGridBounds"), width, height,
                states.size()));
      }

    } catch (NullPointerException e) {
      throw new InvalidFileFormatException(
          String.format(resourceBundle.getString("FileNotFound"), path), e);
    } catch (ParserConfigurationException e) {
      throw new InvalidFileFormatException(
          String.format(resourceBundle.getString("ParserCreationError"), path), e);
    } catch (IOException e) {
      throw new InvalidFileFormatException(String.format(resourceBundle.getString("IOError"), path),
          e);
    } catch (SAXException e) {
      throw new InvalidFileFormatException(
          String.format(resourceBundle.getString("ParserError"), path), e);
    }

  }

  /**
   * Obtain a file's extension for checking whether it is an XML file
   *
   * @param fileName, name of file
   * @return file extension
   */
  public String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    return fileName.substring(dotIndex + 1);
  }

  /**
   * Parse all single data fields read from the XML file that set up the simulation
   *
   * @param eElement, simulation element from the XML file
   */
  private void parseSingleFields(Element eElement) {
    type = eElement.getElementsByTagName("type").item(0).getTextContent();
    title = eElement.getElementsByTagName("title").item(0).getTextContent();
    author = eElement.getElementsByTagName("author").item(0).getTextContent();
    fileDescription = eElement.getElementsByTagName("file_description").item(0).getTextContent();
    displayDescription = eElement.getElementsByTagName("display_description").item(0)
        .getTextContent();
    stateColor = eElement.getElementsByTagName("state_colors").item(0).getTextContent();
    neighborhoodType = eElement.getElementsByTagName("neighborhood_type").item(0).getTextContent();
    String widthString = eElement.getElementsByTagName("width").item(0).getTextContent();
    String heightString = eElement.getElementsByTagName("height").item(0).getTextContent();
    if (!widthString.isEmpty()) {
      width = Integer.parseInt(widthString);
    }
    if (!heightString.isEmpty()) {
      height = Integer.parseInt(heightString);
    }
    gridEdgeType = eElement.getElementsByTagName("grid_edge_type").item(0).getTextContent();
    cellShape = eElement.getElementsByTagName("cell_shape").item(0).getTextContent();

  }

  /**
   * Parse the parameters in the XML file, populating the parameters hashmap that maps parameter
   * names to values.
   *
   * @param parametersNodeList, the list of parameters read from the XML file
   */
  private void parseParameters(NodeList parametersNodeList) {
    // iterate through the parameters node list to obtain the value for each parameter and create new entries in the parameters hashmap
    for (int i = 0; i < parametersNodeList.getLength(); i++) {
      Node parameterNode = parametersNodeList.item(i);
      String name = parameterNode.getNodeName();
      String valueString = parameterNode.getTextContent();
      // check if parameter value is not given (empty). If yes, assign default value.
      if (valueString.isEmpty()) {
        valueString = assignDefaultValueToParameter(name);
      }
      Double value = Double.parseDouble(valueString);
      parameters.put(name, value);
    }
  }

  /**
   * Parse the random configuration states in the XML file, populating the
   * randomConfigurationTotalStates hashmap that maps states to their predefined number.
   *
   * @param randomConfigNodeList, the list of random configuration states read from the XML file
   */
  private void parseRandomConfig(NodeList randomConfigNodeList) {
    // iterate through the parameters node list to obtain the value for each parameter and create new entries in the parameters hashmap
    for (int i = 0; i < randomConfigNodeList.getLength(); i++) {
      Node randomConfigNode = randomConfigNodeList.item(i);
      String name = randomConfigNode.getNodeName();
      Integer value = Integer.parseInt(randomConfigNode.getTextContent());
      randomConfigurationTotalStates.put(name, value);
    }
  }

  /**
   * Return the default value of a given parameter
   *
   * @param name, name of the parameter
   * @return default value of the parameter
   */
  private String assignDefaultValueToParameter(String name) {
    ResourceBundle defaultParametersResourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "DefaultParameters");
    switch (this.type) {
      case "Fire":
        if (name.equals("probTreeIgnites")) return defaultParametersResourceBundle.getString("probTreeIgnites");
        else if (name.equals("probTreeCreated")) return defaultParametersResourceBundle.getString("probTreeCreated");
        else return defaultParametersResourceBundle.getString("neighborsToIgnite");

      case "GameOfLife":
        if (name.equals("aliveToAliveMin")) return defaultParametersResourceBundle.getString("aliveToAliveMin");
        else if (name.equals("aliveToAliveMax")) return defaultParametersResourceBundle.getString("aliveToAliveMax");
        else if (name.equals("deadToAliveMin")) return defaultParametersResourceBundle.getString("deadToAliveMin");
        else return defaultParametersResourceBundle.getString("deadToAliveMax");

      case "Percolation":
        return defaultParametersResourceBundle.getString("percolatedNeighbors");

      case "Schnelling":
        return defaultParametersResourceBundle.getString("proportionNeededToStay");

      case "Wator":
        if (name.equals("fishAgeOfReproduction")) return defaultParametersResourceBundle.getString("fishAgeOfReproduction");
        else if (name.equals("sharkAgeOfReproduction")) return defaultParametersResourceBundle.getString("sharkAgeOfReproduction");
        else if (name.equals("energyBoost")) return defaultParametersResourceBundle.getString("energyBoost");
        else return defaultParametersResourceBundle.getString("initialEnergy");
    }
    return "";
  }

  /**
   * Convert the states data read in as a single String from the XML configuration file to an
   * ArrayList of Integers.
   *
   * @param rawStates, single String indicating states for each cell on the simulation grid
   */
  private void parseStates(String rawStates) {
    this.states.clear();
    String[] states = rawStates.split(" ");
    for (String state : states) {
      this.states.add(Integer.parseInt(state));
    }
  }

  /**
   * Create a new XML file, saving all information and the current cell states of the simulation
   * currently being run.
   * <p>
   * Reference: https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
   *
   * @param filename,   name of the new XML file
   * @param folderName, folder in which the new XML file will be stored
   * @throws ParserConfigurationException, exception handling potential errors from parsing
   *                                       configuration
   * @throws TransformerException,         exception handling potential errors from converting to
   *                                       XML file format
   */
  public void createXML(String filename, String folderName)
      throws ParserConfigurationException, TransformerException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // create root element simulation
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("simulation");
    doc.appendChild(rootElement);

    // add all single data fields
    addElementsToXMLFile(doc, rootElement);

    // Add initial states field
    // Convert Integer ArrayList recording the current states of cell to a single String
    ArrayList<String> states = new ArrayList<>();
    for (Integer cell : this.states) {
      states.add(String.valueOf(cell));
    }
    String convertedStatesData = String.join(" ", states);
    addElement(doc, rootElement, "initial_states", convertedStatesData);

    // Add parameters field
    addElement(doc, rootElement, "parameters", null);

    // Add random configuration states field
    addElement(doc, rootElement, "random_configuration_by_total_states", null);

    // write document to a new file
    try (FileOutputStream output =
        new FileOutputStream("data/" + folderName + "/" + filename + ".xml")) {
      writeXml(doc, output);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Add all single data fields to XML document
   *
   * @param doc,         XML document being written to
   * @param rootElement, root simulation element
   */
  private void addElementsToXMLFile(Document doc, Element rootElement) {
    addElement(doc, rootElement, "type", type);
    addElement(doc, rootElement, "title", title);
    addElement(doc, rootElement, "author", author);
    addElement(doc, rootElement, "file_description", fileDescription);
    addElement(doc, rootElement, "display_description", displayDescription);
    addElement(doc, rootElement, "width", String.valueOf(width));
    addElement(doc, rootElement, "height", String.valueOf(height));
    addElement(doc, rootElement, "neighborhood_type", neighborhoodType);
    addElement(doc, rootElement, "grid_edge_type", gridEdgeType);
    addElement(doc, rootElement, "cell_shape", cellShape);
    addElement(doc, rootElement, "state_colors", stateColor);
  }

  /**
   * Add a single data field (element) under a root element when creating a new XML file
   *
   * @param doc,         XML document being written to
   * @param rootElement, root simulation element
   * @param name,        name of single data field
   * @param content,     content of single data field
   */
  private void addElement(Document doc, Element rootElement, String name, String content) {
    Element element = doc.createElement(name);
    if (name.equals("parameters") && !parameters.isEmpty()) {
      createParameters(doc, element);
    } else if (name.equals("random_configuration_by_total_states")
        && !randomConfigurationTotalStates.isEmpty()) {
      createRandomConfigStates(doc, element);
    } else {
      element.setTextContent(content);
    }
    rootElement.appendChild(element);
  }

  /**
   * Create the parameter element under the root simulation element in the XML file that contains a
   * list of individual parameters each with their specified name and value.
   *
   * @param doc,              Document object being written to
   * @param parameterElement, the element object representing the parameters field under which
   *                          specific parameters will be written
   */
  private void createParameters(Document doc, Element parameterElement) {
    for (String parameterName : parameters.keySet()) {
      addElement(doc, parameterElement, parameterName,
          String.valueOf(parameters.get(parameterName)));
    }
  }

  /**
   * Create the random configuration states element under the root simulation element in the XML
   * file that contains a list of individual states each with their specified number.
   *
   * @param doc,                 Document object being written to
   * @param randomConfigElement, the element object representing the random_configuration_states
   *                             field under which each state and its total number of cells will be
   *                             written
   */
  private void createRandomConfigStates(Document doc, Element randomConfigElement) {
    for (String randomConfigName : randomConfigurationTotalStates.keySet()) {
      addElement(doc, randomConfigElement, randomConfigName,
          String.valueOf(randomConfigurationTotalStates.get(randomConfigName)));
    }
  }

  /**
   * Write XML document to output stream, saving as a file
   *
   * @param doc,    Document object being written to
   * @param output, output stream used by the new file
   * @throws TransformerException, exception handling potential errors from converting to XML file
   *                               format
   */
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
