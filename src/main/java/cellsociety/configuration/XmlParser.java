package cellsociety.configuration;

import cellsociety.exception.InputMissingParametersException;
import cellsociety.exception.InvalidCellStateException;
import cellsociety.exception.InvalidFileFormatException;
import cellsociety.exception.InvalidGridBoundsException;
import cellsociety.exception.InvalidValueException;
import cellsociety.exception.SavingFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
  public static final String FALLING_NAME = "Falling";
  public static final Set<String> SIMULATION_TYPES = new HashSet<>(
      Arrays.asList(FIRE_NAME, GAMEOFLIFE_NAME, PERCOLATION_NAME, SCHELLING_NAME, WATOR_NAME,
          SUGAR_NAME, FALLING_NAME));

  // define valid parameters for each simulation
  public static final Set<String> FIRE_PARAMETERS = new HashSet<>(
      Arrays.asList("probTreeIgnites", "probTreeCreated", "neighborsToIgnite"));
  public static final Set<String> GAMEOFLIFE_PARAMETERS = new HashSet<>(
      Arrays.asList("aliveToAliveMin", "aliveToAliveMax", "deadToAliveMin", "deadToAliveMax"));
  public static final Set<String> PERCOLATION_PARAMETERS = new HashSet<>(
      List.of("percolatedNeighbors"));
  public static final Set<String> SCHELLING_PARAMETERS = new HashSet<>(
      List.of("proportionNeededToStay"));
  public static final Set<String> WATOR_PARAMETERS = new HashSet<>(
      Arrays.asList("fishAgeOfReproduction", "sharkAgeOfReproduction", "energyBoost",
          "initialEnergy"));
  public static final Set<String> SUGAR_PARAMETERS = new HashSet<>(
      Arrays.asList("minVision", "maxVision", "minMetabolism", "maxMetabolism", "minInitialSugar",
          "maxInitialSugar", "growBackRate", "numAgents"));

  // define valid neighborhood types
  public static final Set<String> NEIGHBORHOOD_TYPES = new HashSet<>(
      Arrays.asList("ExtendedMoore", "Moore", "VonNeumann"));
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

  public static final Set<String> SUGAR_CELL_STATES = new HashSet<>(
      Arrays.asList("0", "1", "2", "3", "4"));

  public static final Set<String> FALLING_CELL_STATES = new HashSet<>(
      Arrays.asList("0", "1", "2", "3"));

  public static final Map<String, Set<String>> SIMULATION_CELL_STATES = new HashMap<>() {{
    put(FIRE_NAME, FIRE_CELL_STATES);
    put(GAMEOFLIFE_NAME, GAMEOFLIFE_CELL_STATES);
    put(PERCOLATION_NAME, PERCOLATION_CELL_STATES);
    put(SCHELLING_NAME, SCHELLING_CELL_STATES);
    put(WATOR_NAME, WATOR_CELL_STATES);
    put(SUGAR_NAME, SUGAR_CELL_STATES);
    put(FALLING_NAME, FALLING_CELL_STATES);
  }};

  // define names for the field as written in the XML configuration files
  public static final String TYPE_FIELD_NAME = "type";
  public static final String TITLE_FIELD_NAME = "title";
  public static final String AUTHOR_FIELD_NAME = "author";
  public static final String FILE_DES_FIELD_NAME = "file_description";
  public static final String DISPLAY_DES_FIELD_NAME = "display_description";
  public static final String WIDTH_FIELD_NAME = "width";
  public static final String HEIGHT_FIELD_NAME = "height";
  public static final String NEIGHBORHOOD_TYPE_FIELD_NAME = "neighborhood_type";
  public static final String LANGUAGE_FIELD_NAME = "language";
  public static final String CELL_SHAPE_FIELD_NAME = "cell_shape";
  public static final String GRID_EDGE_TYPE_FIELD_NAME = "grid_edge_type";
  public static final String PARAMETERS_FIELD_NAME = "parameters";
  public static final String RANDOM_CONFIG_FIELD_NAME = "random_configuration_by_total_states";
  public static final String INITIAL_STATES_FIELD_NAME = "initial_states";
  public static final String SLIDER_FIELD_NAME = "slider";

  private ResourceBundle resourceBundle; // resource bundle for error handling messages
  private String type; // simulation type
  private String title; // simulation title
  private String author; // author of configuration file
  private String fileDescription; // description of configuration file
  private String displayDescription; // description to be displayed on GUI

  // states and corresponding colors of the simulation to be displayed on GUI
  private int width; // number of columns
  private int height; // number of rows
  private String neighborhoodType; // adjacent or cardinal
  private List<Integer> states; // the state of each cell
  private Map<String, Double> parameters; // Hashmap mapping parameter names to their values
  private String language;
  private String cellShape;
  private String gridEdgeType;
  private String sliderInitial;
  private final Map<String, Integer> randomConfigurationTotalStates;
  private int totalNumCells;

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
   * Retrieves displayDescription instance variable
   *
   * @return displayDescription, the description of the simulation currently being run.
   */
  public String getDisplayDescription() {
    return displayDescription;
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

  public void setGridEdgeType(String edgeType){
    this.gridEdgeType = edgeType;
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

    // check if simulation type exists
    if (!SIMULATION_TYPES.contains(type)) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("NonExistentSimulationType"), type));
    }

    // parse initial states
    parseStates(element.getElementsByTagName(INITIAL_STATES_FIELD_NAME).item(0));

    // parse parameters
    parseParameters(element.getElementsByTagName(PARAMETERS_FIELD_NAME).item(0));

    // parse random configuration states
    parseRandomConfig(element.getElementsByTagName(RANDOM_CONFIG_FIELD_NAME)
        .item(0));

    totalNumCells = states.size();

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

    // validate parameters that define the dynamics of the simulation
    validateFunctionalParameters();
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
        || displayDescription.isEmpty()) {
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
   * Validate inputs for the parameters defining the simulation
   *
   * @throws InvalidValueException      when value is negative or does not exist
   * @throws InvalidGridBoundsException when the user loads a configuration file that has cell
   *                                    locations specified outside the grid’s bounds
   */
  private void validateEssentialInputs() throws InvalidValueException, InvalidGridBoundsException {
    // check if width or height is negative
    if (width < 0 || height < 0) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("NegativeValueError"), "Width or Height"));
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
   * Validate parameters that define the dynamics of the simulation
   */
  private void validateFunctionalParameters() {
    // check if only width or height is given.
    // If so, update the other based on the total number of cells read
    if (width == 0) {
      width = totalNumCells / height;
    } else if (height == 0) {
      height = totalNumCells / width;
    }

    // check if grid dimension is valid, throw exception otherwise
    if (randomConfigurationTotalStates.isEmpty() && width * height != states.size()
        || !randomConfigurationTotalStates.isEmpty() && width * height != totalNumCells) {
      throw new InvalidGridBoundsException(
          String.format(resourceBundle.getString("InvalidGridBounds"), width, height,
              totalNumCells));
    }

    // check if numAgents is larger than size of grid for Sugar Simulation
    String sugarCheckParam = "numAgents";
    if (type.equals(SUGAR_NAME) && parameters.get(sugarCheckParam) > totalNumCells) {
      throw new InvalidValueException(
          String.format(resourceBundle.getString("SugarSimulationParamValueError"),
              parameters.get(sugarCheckParam),
              totalNumCells));
    }

    // if random configurations exist, set the state of each cell by the random configurations
    if (!randomConfigurationTotalStates.isEmpty()) {
      setRandomlyConfiguredStates();
    }

  }

  /**
   * Obtain a file's extension for checking whether it is an XML file
   *
   * @param fileName, name of file
   * @return file extension
   */
  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    return fileName.substring(dotIndex + 1);
  }

  /**
   * Parse all single data fields read from the XML file that set up the simulation
   *
   * @param element, simulation element from the XML file
   */
  private void parseSingleFields(Element element) {
    type = element.getElementsByTagName(TYPE_FIELD_NAME).item(0).getTextContent();
    title = element.getElementsByTagName(TITLE_FIELD_NAME).item(0).getTextContent();
    author = element.getElementsByTagName(AUTHOR_FIELD_NAME).item(0).getTextContent();
    fileDescription = element.getElementsByTagName(FILE_DES_FIELD_NAME).item(0).getTextContent();
    displayDescription = element.getElementsByTagName(DISPLAY_DES_FIELD_NAME).item(0)
        .getTextContent();
    neighborhoodType = element.getElementsByTagName(NEIGHBORHOOD_TYPE_FIELD_NAME).item(0)
        .getTextContent();
    String widthString = element.getElementsByTagName(WIDTH_FIELD_NAME).item(0).getTextContent();
    String heightString = element.getElementsByTagName(HEIGHT_FIELD_NAME).item(0).getTextContent();
    if (!widthString.isEmpty()) {
      width = Integer.parseInt(widthString);
    }
    if (!heightString.isEmpty()) {
      height = Integer.parseInt(heightString);
    }
    gridEdgeType = element.getElementsByTagName(GRID_EDGE_TYPE_FIELD_NAME).item(0).getTextContent();
    cellShape = element.getElementsByTagName(CELL_SHAPE_FIELD_NAME).item(0).getTextContent();
    language = element.getElementsByTagName(LANGUAGE_FIELD_NAME).item(0).getTextContent();

    sliderInitial = element.getElementsByTagName(SLIDER_FIELD_NAME).item(0).getTextContent();

    // update resource bundle given language
    resourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Errors" + language);

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
      // check if parameter name is valid for given simulation

      String valueString = parameterNode.getTextContent();
      // check if parameter value is not given (empty). If yes, assign default value.
      if (valueString.isEmpty()) {
        valueString = assignDefaultValueToParameter(name);
      }
      double value = Double.parseDouble(valueString);
      // check for negative values
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
    for (int i = 0; i < randomConfigNodeList.getLength(); i++) {
      Node currRandConfigNode = randomConfigNodeList.item(i);
      String name = currRandConfigNode.getNodeName();
      int value = Integer.parseInt(currRandConfigNode.getTextContent());
      if (value < 0) {
        throw new InvalidValueException(
            String.format(resourceBundle.getString("NegativeRandConfigValueError"), name));
      }
      randomConfigurationTotalStates.put(name, value);
    }
  }


  public int getInitialSlider() {
    return Integer.parseInt(sliderInitial);
  }


  /**
   * Set the state for each cell in the simulation according to the random configuration parameters
   */
  private void setRandomlyConfiguredStates() {
    List<Integer> resultList = new ArrayList<>();
    Pattern pattern = Pattern.compile("num(\\d+)");
    for (Map.Entry<String, Integer> entry : randomConfigurationTotalStates.entrySet()) {
      String key = entry.getKey();
      Integer repeat = entry.getValue();
      Matcher matcher = pattern.matcher(key);
      if (matcher.find()) {
        int num = Integer.parseInt(matcher.group(1));
        for (int z = 0; z < repeat; z++) {
          resultList.add(num);
        }
      }
    }
    Collections.shuffle(resultList, new Random());
    states = resultList;
  }


  /**
   * Return the default value of a given parameter
   *
   * @param name, name of the parameter
   * @return default value of the parameter
   * @throws InvalidValueException when simulation type does not exist or a parameter does not exist
   *                               for given simulation
   */
  private String assignDefaultValueToParameter(String name) throws InvalidValueException {
    return switch (type) {
      case FIRE_NAME -> findDefaultParameter(FIRE_PARAMETERS, name);
      case GAMEOFLIFE_NAME -> findDefaultParameter(GAMEOFLIFE_PARAMETERS, name);
      case PERCOLATION_NAME -> findDefaultParameter(PERCOLATION_PARAMETERS, name);
      case SCHELLING_NAME -> findDefaultParameter(SCHELLING_PARAMETERS, name);
      case WATOR_NAME -> findDefaultParameter(WATOR_PARAMETERS, name);
      case SUGAR_NAME -> findDefaultParameter(SUGAR_PARAMETERS, name);
      default -> throw new InvalidValueException(
          String.format(resourceBundle.getString("NonExistentSimulationType"), type));
    };
  }

  /**
   * Find default value for a given parameter in the given simulation's set of parameters
   *
   * @param parameters, list of valid parameters for the give simulation
   * @param name,       name of the parameter with missing value
   * @return String default value for the given parameter
   * @throws InvalidValueException when the given parameter does not exist for given simulation
   */
  private String findDefaultParameter(Set<String> parameters, String name)
      throws InvalidValueException {
    ResourceBundle defaultParametersResourceBundle = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + "DefaultParameters");
    for (String parameter : parameters) {
      if (name.equals(parameter) && name.equals("agentProportion")) {
        int agentProportion = (int) Math.round(
            Double.parseDouble(defaultParametersResourceBundle.getString("agentProportion"))
                * getHeight() * getWidth());
        return Integer.toString(agentProportion);
      } else if (name.equals(parameter)) {
        return defaultParametersResourceBundle.getString(parameter);
      }
    }
    throw new InvalidValueException(
        String.format(resourceBundle.getString("NonExistentMissingParameter"), name, type));
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
   * @throws InvalidFileFormatException, when specified path to file is invalid
   * @throws SavingFileException,        when errors occur during conversion to XML file
   */
  public void createXml(String filename, String folderName) throws InvalidFileFormatException,
      SavingFileException {

    // create new Document object
    Document doc = createNewDoc();

    // create root element simulation
    Element rootElement = doc.createElement("simulation");
    doc.appendChild(rootElement);

    // add all data fields to root element
    addElementsToXmlFile(doc, rootElement);

    // write document to a new file
    String path = "data/" + folderName + "/" + filename + ".xml";
    writeXml(doc, path);
  }

  /**
   * Create new Document object for writing new XML file
   *
   * @return Document object to be written.
   */
  private Document createNewDoc() {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      return docBuilder.newDocument();
    } catch (ParserConfigurationException e) {
      throw new SavingFileException(resourceBundle.getString("DocumentCreationError"));
    }
  }

  /**
   * Add all data fields to XML document
   *
   * @param doc,         XML document being written to
   * @param rootElement, root simulation element
   */
  private void addElementsToXmlFile(Document doc, Element rootElement) {
    // Add all single elements
    addSingleElements(doc, rootElement);

    // Add initial states field
    addInitialStatesElement(doc, rootElement);

    // Add parameters field
    addElement(doc, rootElement, PARAMETERS_FIELD_NAME, null);

    // Add random configuration states field
    addElement(doc, rootElement, RANDOM_CONFIG_FIELD_NAME, null);
  }

  /**
   * Add all single data fields to XML document
   *
   * @param doc,         XML document being written to
   * @param rootElement, root simulation element
   */
  private void addSingleElements(Document doc, Element rootElement) {
    addElement(doc, rootElement, TYPE_FIELD_NAME, type);
    addElement(doc, rootElement, TITLE_FIELD_NAME, title);
    addElement(doc, rootElement, AUTHOR_FIELD_NAME, author);
    addElement(doc, rootElement, FILE_DES_FIELD_NAME, fileDescription);
    addElement(doc, rootElement, DISPLAY_DES_FIELD_NAME, displayDescription);
    addElement(doc, rootElement, WIDTH_FIELD_NAME, String.valueOf(width));
    addElement(doc, rootElement, HEIGHT_FIELD_NAME, String.valueOf(height));
    addElement(doc, rootElement, NEIGHBORHOOD_TYPE_FIELD_NAME, neighborhoodType);
    addElement(doc, rootElement, GRID_EDGE_TYPE_FIELD_NAME, gridEdgeType);
    addElement(doc, rootElement, CELL_SHAPE_FIELD_NAME, cellShape);
    addElement(doc, rootElement, LANGUAGE_FIELD_NAME, language);
    addElement(doc, rootElement, SLIDER_FIELD_NAME, sliderInitial);

  }

  /**
   * Add the initial states field to XML document
   *
   * @param doc,         XML document being written to
   * @param rootElement, root simulation element
   */
  private void addInitialStatesElement(Document doc, Element rootElement) {
    // Convert Integer ArrayList recording the current states of cell to a single String
    List<String> states = new ArrayList<>();
    for (Integer cell : this.states) {
      states.add(String.valueOf(cell));
    }
    String convertedStatesData = String.join(" ", states);
    addElement(doc, rootElement, INITIAL_STATES_FIELD_NAME, convertedStatesData);
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
   * @param doc,  Document object being written to
   * @param path, specified path to which the new file will be stored
   * @throws InvalidFileFormatException, when errors occur from converting to XML file format
   * @throws SavingFileException,        when errors occur during saving the XML file
   */
  private void writeXml(Document doc, String path) throws InvalidFileFormatException,
      SavingFileException {
    try {
      FileOutputStream output = new FileOutputStream(path);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();

      // formatting XML file
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(output);
      transformer.transform(source, result);

    } catch (FileNotFoundException e) {
      throw new InvalidFileFormatException(
          String.format(resourceBundle.getString("PathNotFound"), path), e);
    } catch (TransformerException e) {
      throw new SavingFileException(resourceBundle.getString("DocumentCreationError"), e);
    }

  }
}
