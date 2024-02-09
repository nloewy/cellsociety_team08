package cellsociety.configuration;

import cellsociety.exception.InputMissingParametersException;
import cellsociety.exception.InvalidCellStateException;
import cellsociety.exception.InvalidFileFormatException;
import cellsociety.exception.InvalidGridBoundsException;
import cellsociety.exception.InvalidValueException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
 * The XmlParser reads and stores all data from a given XML configuration file, handling all
 * potential error by throwing exceptions, calculating certain values or assigning default values.
 * Using the values stored in its attributes, the XmlParser may also write and save a new XML
 * configuration file.
 *
 * @author Judy He
 */

public class XmlParser {

  public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.configuration.";

  // define name for each simulation type
  public static final String FIRE_NAME = "Fire";
  public static final String GAMEOFLIFE_NAME = "GameOfLife";
  public static final String PERCOLATION_NAME = "Percolation";
  public static final String SCHELLING_NAME = "Schelling";
  public static final String WATOR_NAME = "Wator";
  public static final String SUGAR_NAME = "Sugar";
  public static final Set<String> SIMULATION_TYPES = new HashSet<>(
      Arrays.asList(FIRE_NAME, GAMEOFLIFE_NAME, PERCOLATION_NAME, SCHELLING_NAME, WATOR_NAME));

  // define valid neighborhood types
  public static final Set<String> NEIGHBORHOOD_TYPES = new HashSet<>(
      Arrays.asList("adjacent", "cardinal", "Moore", "VonNeumann"));
  // define valid cell shapes
  public static final Set<String> CELL_SHAPES = new HashSet<>(
      Arrays.asList("square", "hexagon"));
  // define valid grid edge types
  public static final Set<String> GRID_EDGE_TYPES = new HashSet<>(
      Arrays.asList("Normal", "Warped"));
  // define valid cell states for Fire simulation

  public static final Set<String> FIRE_CELL_STATES = new HashSet<>(
      Arrays.asList("0", "1", "2"));
  // define valid cell states for GameOfLife simulation
  public static final Set<String> GAMEOFLIFE_CELL_STATES = new HashSet<>(
      Arrays.asList("0", "1"));
  // define valid cell states for Percolation simulation
  public static final Set<String> PERCOLATION_CELL_STATES = new HashSet<>(
      Arrays.asList("0", "1", "2"));
  // define valid cell states for Schelling simulation
  public static final Set<String> SCHELLING_CELL_STATES = new HashSet<>(
      Arrays.asList("0", "1", "2"));
  // define valid cell states for Wator simulation
  public static final Set<String> WATOR_CELL_STATES = new HashSet<>(
      Arrays.asList("0", "1", "2"));
  public static final Map<String, Set<String>> SIMULATION_CELL_STATES = new HashMap<>() {{
      put(FIRE_NAME, FIRE_CELL_STATES);
      put(GAMEOFLIFE_NAME, GAMEOFLIFE_CELL_STATES);
      put(PERCOLATION_NAME, PERCOLATION_CELL_STATES);
      put(SCHELLING_NAME, SCHELLING_CELL_STATES);
      put(WATOR_NAME, WATOR_CELL_STATES);
    }};

  // define names for the field parameters, random configuration,
  // and initial states as written in the XML configuration files
  public static final String PARAMETERS_FIELD_NAME = "parameters";
  public static final String RANDOM_CONFIG_FIELD_NAME = "random_configuration_by_total_states";
  public static final String INITIAL_STATES_FIELD_NAME = "initial_states";

  private ResourceBundle resourceBundle; // resource bundle for error handling messages
  private String type; // simulation type
  private String title; // simulation title
  private String author; // author of configuration file
  private String fileDescription; // description of configuration file
  private String displayDescription; // description to be displayed on GUI

  // states and corresponding colors of the simulation to be displayed on GUI
  private String stateColor;
  private int width; // number of columns
  private int height; // number of rows
  private String neighborhoodType; // adjacent or cardinal
  private List<Integer> states; // the state of each cell
  private Map<String, Double> parameters; // Hashmap mapping parameter names to their values
  private String language;
  private String cellShape;
  private String gridEdgeType;
  private Map<String, Integer> randomConfigurationTotalStates;
  private int totalNumStates;

  /**
   * Constructor for initializing the states ArrayList and parameters HashMap
   */
  public XmlParser() {
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

  /**
   * Retrieves cellShape instance variable
   *
   * @return cell shape, the shape of the cells in the given simulation
   */
  public String getCellShape() {
    return cellShape;
  }

  /**
   * Updates cellShape instance variable
   *
   * @param cellShape, the shape of the cells in the given simulation
   */
  public void setCellShape(String cellShape) {
    this.cellShape = cellShape;
  }

  /**
   * Retrieves gridEdgeType instance variable
   *
   * @return gridEdgeType, the grid edge type used by the given simulation
   */
  public String getGridEdgeType() {
    return gridEdgeType;
  }

  /**
   * Updates gridEdgeType instance variable
   *
   * @param gridEdgeType, the grid edge type used by the given simulation
   */
  public void setGridEdgeType(String gridEdgeType) {
    this.gridEdgeType = gridEdgeType;
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
  public void setStates(List<Integer> states) {
    this.states = states;
  }

  /**
   * Retrieves stateColor instance variable
   *
   * @return stateColor, a description for which color corresponds to which color for the given
   * simulation
   */
  public String getStateColor() {
    return stateColor;
  }

  /**
   * Update stateColor instance variable
   *
   * @param stateColor, a description for which color corresponds to which color for the given
   *                    simulation
   */
  public void setStateColor(String stateColor) {
    this.stateColor = stateColor;
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

  /**
   * Retrieve resource bundle containing the error messages in given language
   *
   * @return ResourceBundle, the resource bundle used by the current simulation
   */
  public ResourceBundle getResourceBundle() {
    return resourceBundle;
  }

  /**
   * Updates resourceBundle instance variable
   *
   * @param resourceBundle, the resource bundle used by the current simulation
   */
  public void setResourceBundle(ResourceBundle resourceBundle) {
    this.resourceBundle = resourceBundle;
  }

  /**
   * Retrieves language instance variable
   *
   * @return language, the language the given simulation is in
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Updates language instance variable
   *
   * @param language, the language the given simulation is in
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Retrieves randomConfigurationTotalStates instance variable
   *
   * @return randomConfigurationTotalStates, a Hashmap mapping a state to the number of cells with
   * that state in the given simulation
   */
  public Map<String, Integer> getRandomConfigurationTotalStates() {
    return randomConfigurationTotalStates;
  }

  /**
   * Updates randomConfigurationTotalStates instance variable
   *
   * @param randomConfigurationTotalStates, a Hashmap mapping a state to the number of cells with
   *                                        that state in the given simulation
   */
  public void setRandomConfigurationTotalStates(
      Map<String, Integer> randomConfigurationTotalStates) {
    this.randomConfigurationTotalStates = randomConfigurationTotalStates;
  }

  /**
   * Read an XML configuration file, initializing all attributes in the XmlParser
   *
   * @param path, the path to the XML configuration file being read
   * @throws InvalidFileFormatException,      when the user loads a configuration file that has
   *                                          empty, badly formatted, or non-XML file,
   * @throws InputMissingParametersException, when missing essential parameters or height and width
   *                                          in the configuration file
   * @throws InvalidGridBoundsException,      when the user loads a configuration file that has cell
   *                                          locations specified outside the grid’s bounds
   * @throws InvalidValueException,           when a value defining the simulation is negative or
   *                                          does not exist
   * @throws InvalidCellStateException,       when a cell's state is invalid for given simulation
   */
  public void readXml(String path) throws InvalidFileFormatException,
      InputMissingParametersException,
      InvalidValueException,
      InvalidCellStateException,
      InvalidGridBoundsException {

    // parse file to Document object
    Document doc = parseFile(path);

    // parse simulation parameters
    parseSimulation(doc);

    // validate simulation, handling any potential errors
    validateSimulation();

  }

  /**
   * Parse file and convert file to Document object given file path
   *
   * @param path, path to the file being read
   * @return Document object representing the file
   * @throws InvalidFileFormatException when file is not found or file type is not xml
   */
  private Document parseFile(String path) throws InvalidFileFormatException {
    try {
      // create a new File object for the XML file
      File file = new File(path);

      // check file extension (only accepts .xml files)
      if (!getFileExtension(file.getName()).equals("xml")) {
        throw new InvalidFileFormatException(
            String.format(resourceBundle.getString("InvalidFileType"), path));
      }
      return createDocument(file, path);
    } catch (NullPointerException e) {
      throw new InvalidFileFormatException(
          String.format(resourceBundle.getString("FileNotFound"), path), e);
    }
  }

  /**
   * Create Document object given parsed File object and path to file in case errors occur
   *
   * @param file, file to be represented by Document object
   * @param path, path to file
   * @return Document object representing the file
   * @throws InvalidFileFormatException, when XML file is empty, badly formatted or not found
   */
  private Document createDocument(File file, String path) throws InvalidFileFormatException {
    try {
      // create a new instance of document builder factory that allows for a document builder
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      // checking for well-formatted XML
      dbf.setValidating(false);
      dbf.setNamespaceAware(true);

      // create an instance of document builder to parse the XML file
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      doc.getDocumentElement().normalize();

      return doc;

    } catch (FileNotFoundException e) {
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
   * Parse all simulation parameters, assigning values to attributes of the XmlParser
   *
   * @param doc Document object from which parameter values will be read
   * @throws InputMissingParametersException when missing essential parameters or height and width
   *                                         in the configuration file
   * @throws InvalidValueException           when a value defining the simulation is negative or
   *                                         does not exist
   * @throws InvalidGridBoundsException      when the user loads a configuration file that has cell
   *                                         locations specified outside the grid’s bounds
   */
  private void parseSimulation(Document doc) throws InputMissingParametersException,
      InvalidValueException,
      InvalidGridBoundsException {

    // obtaining the simulation node containing all the configuration data
    Node simulationNode = doc.getElementsByTagName("simulation").item(0);

    Element element = (Element) simulationNode;

    // check if simulation node is empty
    if (element.getElementsByTagName("*").getLength() == 0) {
      throw new InvalidFileFormatException(
          String.format(resourceBundle.getString("EmptyXMLFile")));
    }

    // parse all configuration data presented as single (not nested) fields
    parseSingleFields(element);

    // parse initial states
    parseStates(element.getElementsByTagName(INITIAL_STATES_FIELD_NAME).item(0));
    totalNumStates = states.size();

    // parse parameters
    parseParameters(element.getElementsByTagName(PARAMETERS_FIELD_NAME).item(0));

    // parse random configuration states
    parseRandomConfig(element.getElementsByTagName(RANDOM_CONFIG_FIELD_NAME)
        .item(0));
    for (Integer value : randomConfigurationTotalStates.values()) {
      totalNumStates += value;
    }

  }

  /**
   * Validate the simulation, handling any potential errors
   *
   * @throws InputMissingParametersException when missing essential parameters or height and width
   *                                         in the configuration file
   * @throws InvalidValueException           when value is negative or does not exist
   * @throws InvalidCellStateException,      when a cell's state is invalid for given simulation
   * @throws InvalidGridBoundsException      when the user loads a configuration file that has cell
   *                                         locations specified outside the grid’s bounds
   */
  private void validateSimulation() throws InputMissingParametersException,
      InvalidValueException,
      InvalidCellStateException,
      InvalidGridBoundsException {
    // check for empty essential inputs
    checkEmptyInputs();

    // validate essential input parameters that define the simulation
    validateEssentialInputs();

    // check if only width or height is given.
    // If so, update the other based on the total number of states read
    if (width == 0) {
      width = totalNumStates / height;
    } else if (height == 0) {
      height = totalNumStates / width;
    }

    // Check if grid dimension is valid, throw exception otherwise
    if (randomConfigurationTotalStates.isEmpty() && width * height != states.size()
        || !randomConfigurationTotalStates.isEmpty() && width * height != totalNumStates) {
      throw new InvalidGridBoundsException(
          String.format(resourceBundle.getString("InvalidGridBounds"), width, height,
              states.size()));
    }
  }

  /**
   * Check for empty inputs for the essential simulation parameters
   *
   * @throws InputMissingParametersException when missing essential parameters or height and width
   *                                         in the configuration file
   */
  private void checkEmptyInputs() throws InputMissingParametersException {
    // check for empty essential inputs
    if (type.isEmpty() || title.isEmpty() || author.isEmpty() || fileDescription.isEmpty()
        || displayDescription.isEmpty() || stateColor.isEmpty()) {
      throw new InputMissingParametersException(
          String.format(resourceBundle.getString("MissingParameter")));
    }

    // check if both width and height are not given
    if (width == 0 && height == 0) {
      throw new InputMissingParametersException(
          String.format(resourceBundle.getString("MissingWidthAndHeight")));
    }

  }

  /**
   * Validate inputs for the essential simulation parameters
   *
   * @throws InvalidValueException when value is negative or does not exist
   */
  private void validateEssentialInputs() throws InvalidValueException {
    // check if width or height is negative
    if (width < 0 || height < 0) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("NegativeValueError"), "Width or Height"));
    }

    // check if simulation type exists
    if (!SIMULATION_TYPES.contains(type)) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("NonExistentSimulationType"), type));
    }

    // check if neighborhood type exists
    if (!NEIGHBORHOOD_TYPES.contains(neighborhoodType)) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("NonExistentNeighborhoodType"),
              neighborhoodType));
    }

    // check if cell shape exists
    if (!CELL_SHAPES.contains(cellShape)) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("NonExistentCellShape"), cellShape));
    }

    // check if grid edge type exists
    if (!GRID_EDGE_TYPES.contains(gridEdgeType)) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("NonExistentGridEdgeType"), gridEdgeType));
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
   * @param element, simulation element from the XML file
   */
  private void parseSingleFields(Element element) {
    type = element.getElementsByTagName("type").item(0).getTextContent();
    title = element.getElementsByTagName("title").item(0).getTextContent();
    author = element.getElementsByTagName("author").item(0).getTextContent();
    fileDescription = element.getElementsByTagName("file_description").item(0).getTextContent();
    displayDescription = element.getElementsByTagName("display_description").item(0)
        .getTextContent();
    stateColor = element.getElementsByTagName("state_colors").item(0).getTextContent();
    neighborhoodType = element.getElementsByTagName("neighborhood_type").item(0).getTextContent();
    String widthString = element.getElementsByTagName("width").item(0).getTextContent();
    String heightString = element.getElementsByTagName("height").item(0).getTextContent();
    if (!widthString.isEmpty()) {
      width = Integer.parseInt(widthString);
    }
    if (!heightString.isEmpty()) {
      height = Integer.parseInt(heightString);
    }
    gridEdgeType = element.getElementsByTagName("grid_edge_type").item(0).getTextContent();
    cellShape = element.getElementsByTagName("cell_shape").item(0).getTextContent();

  }

  /**
   * Parse the parameters in the XML file, populating the parameters hashmap that maps parameter
   * names to values.
   *
   * @param parametersNode, the node containing the list of parameters read from the XML file
   * @throws InvalidValueException when a parameter value is negative
   */
  private void parseParameters(Node parametersNode) throws InvalidValueException {
    Element parameterElement = (Element) parametersNode;
    NodeList parametersNodeList = parameterElement.getElementsByTagName("*");

    // iterate through the parameters node list to obtain the value for each parameter
    // and create new entries in the parameters hashmap
    for (int i = 0; i < parametersNodeList.getLength(); i++) {
      Node parameterNode = parametersNodeList.item(i);
      String name = parameterNode.getNodeName();
      String valueString = parameterNode.getTextContent();
      // check if parameter value is not given (empty). If yes, assign default value.
      if (valueString.isEmpty()) {
        valueString = assignDefaultValueToParameter(name);
      }
      Double value = Double.parseDouble(valueString);
      if (value < 0) {
        throw new InvalidValueException(
            String.format(resourceBundle.getString("NegativeParameterValueError"), name));
      }
      parameters.put(name, value);
    }
  }

  /**
   * Parse the random configuration states in the XML file, populating the
   * randomConfigurationTotalStates hashmap that maps states to their predefined number.
   *
   * @param randomConfigNode, the node containing the list of random configuration states read from
   *                          the XML file
   * @throws InvalidValueException when a random configuration value is negative
   */
  private void parseRandomConfig(Node randomConfigNode) throws InvalidValueException {
    Element randomConfigElement = (Element) randomConfigNode;
    NodeList randomConfigNodeList = randomConfigElement.getElementsByTagName("*");
    if (randomConfigNodeList.getLength() == 0) {
      return;
    }

    // iterate through the parameters node list to obtain the value for each parameter
    // and create new entries in the parameters hashmap
    for (int i = 0; i < randomConfigNodeList.getLength(); i++) {
      Node currRandConfigNode = randomConfigNodeList.item(i);
      String name = currRandConfigNode.getNodeName();
      Integer value = Integer.parseInt(currRandConfigNode.getTextContent());
      if (value < 0) {
        throw new InvalidValueException(
            String.format(resourceBundle.getString("NegativeRandConfigValueError"), name));
      }
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
    ResourceBundle defaultParametersResourceBundle = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + "DefaultParameters");

    return switch (this.type) {
      case FIRE_NAME -> {
        if (name.equals("probTreeIgnites")) {
          yield defaultParametersResourceBundle.getString("probTreeIgnites");
        } else if (name.equals("probTreeCreated")) {
          yield defaultParametersResourceBundle.getString("probTreeCreated");
        }
        yield defaultParametersResourceBundle.getString("neighborsToIgnite");
      }
      case GAMEOFLIFE_NAME -> {
        if (name.equals("aliveToAliveMin")) {
          yield defaultParametersResourceBundle.getString("aliveToAliveMin");
        } else if (name.equals("aliveToAliveMax")) {
          yield defaultParametersResourceBundle.getString("aliveToAliveMax");
        } else if (name.equals("deadToAliveMin")) {
          yield defaultParametersResourceBundle.getString("deadToAliveMin");
        }
        yield defaultParametersResourceBundle.getString("deadToAliveMax");
      }
      case PERCOLATION_NAME -> defaultParametersResourceBundle.getString("percolatedNeighbors");
      case SCHELLING_NAME -> defaultParametersResourceBundle.getString("proportionNeededToStay");
      case WATOR_NAME -> {
        if (name.equals("fishAgeOfReproduction")) {
          yield defaultParametersResourceBundle.getString("fishAgeOfReproduction");
        } else if (name.equals("sharkAgeOfReproduction")) {
          yield defaultParametersResourceBundle.getString("sharkAgeOfReproduction");
        } else if (name.equals("energyBoost")) {
          yield defaultParametersResourceBundle.getString("energyBoost");
        }
        yield defaultParametersResourceBundle.getString("initialEnergy");
      }
      default -> "";
    };
  }

  /**
   * Convert the states data read in as a single String from the XML configuration file to an
   * ArrayList of Integers.
   *
   * @param statesNode, node containing the single String indicating states for each cell in the
   *                    simulation
   * @throws InvalidCellStateException, when a cell's state is invalid for given simulation
   */
  private void parseStates(Node statesNode) throws InvalidCellStateException {
    String rawStates = statesNode.getTextContent();
    if (rawStates.isEmpty()) {
      return;
    }
    this.states.clear();
    String[] states = rawStates.split(" ");
    for (String state : states) {
      // check for invalid cell state for given simulation type
      if (!SIMULATION_CELL_STATES.get(type).contains(state)) {
        throw new InvalidCellStateException(
            String.format(resourceBundle.getString("InvalidCellState"), state, type));
      }
      this.states.add(Integer.parseInt(state));
    }
  }

  /**
   * Create a new XML file, saving all information and the current cell states of the simulation
   * currently being run.
   *
   * @param filename,   name of the new XML file
   * @param folderName, folder in which the new XML file will be stored
   * @throws ParserConfigurationException, exception handling potential errors from parsing
   *                                       configuration
   * @throws TransformerException,         exception handling potential errors from converting to
   *                                       XML file format
   */
  public void createXml(String filename, String folderName)
      throws ParserConfigurationException, TransformerException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // create root element simulation
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("simulation");
    doc.appendChild(rootElement);

    // add all single data fields
    addElementsToXmlFile(doc, rootElement);

    // Add initial states field
    // Convert Integer ArrayList recording the current states of cell to a single String
    ArrayList<String> states = new ArrayList<>();
    for (Integer cell : this.states) {
      states.add(String.valueOf(cell));
    }
    String convertedStatesData = String.join(" ", states);
    addElement(doc, rootElement, INITIAL_STATES_FIELD_NAME, convertedStatesData);

    // Add parameters field
    addElement(doc, rootElement, PARAMETERS_FIELD_NAME, null);

    // Add random configuration states field
    addElement(doc, rootElement, RANDOM_CONFIG_FIELD_NAME, null);

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
  private void addElementsToXmlFile(Document doc, Element rootElement) {
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
    if (name.equals(PARAMETERS_FIELD_NAME) && !parameters.isEmpty()) {
      createParameters(doc, element);
    } else if (name.equals(RANDOM_CONFIG_FIELD_NAME)
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
