package cellsociety.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The settings class controls the settings panel of the simulation
 * Contains language, edge type, grid/cell outline, and parameter customization
 */

public class Settings {

  public static final int SETTINGS_BOX_SPACING = 10;
  public static final int SETTINGS_SCENE_WIDTH = 350;
  public static final int SETTINGS_SCENE_HEIGHT = 400;
  private static final String OPTIONS_PACKAGE = "cellsociety.avaliableOptions";
  private static final String VALUE_FACTORY_MIN_KEY = "valueFactoryMin";
  private static final String VALUE_FACTORY_MAX_KEY = "valueFactoryMax";
  private static final String AMOUNT_STEP_BY_KEY = "amountStepBy";
  private static final String VALUE_FACTORY_WIDTH = "valueFactoryWidth";
  private static final String PADDING_KEY = "padding";
  private static final String EDGE_TYPE_KEY = "edgeType";
  private static final String OUTLINE_TYPE_KEY = "outlineType";
  private static final String SELECT_LANGUAGE_KEY = "selectLanguage";
  private static final String SETTINGS_TITLE_KEY = "settingsTitle";
  private static final String EDGE_NORMAL_KEY = "edgeNormal";
  private static final String EDGE_WARPED_KEY = "edgeWarped";
  private static final String OUTLINE_ON_KEY = "outlineOn";
  private static final String OUTLINE_OFF_KEY = "outlineOff";
  private static final String APPLY_BUTTON_KEY = "ApplyButton";
  private final ResourceBundle avaliableOptions;
  private final Stage settingsPanel;
  private final Scene scene;
  private final VBox root;
  private final Map<String, Double> parameters;
  private final Button saveParametersButton;
  private List<String> avaliableEdgeTypes;
  private List<String> avaliableCellOutlines;
  private List<String> avaliableLanguages;
  private ComboBox<String> edgeTypeComboBox;
  private ComboBox<String> outlineTypeComboBox;
  private ComboBox<String> languageComboBox;
  private String edge;
  private final List<Spinner<Double>> numberSpinners = new ArrayList<>();
  private final List<ComboBox<String>> comboBoxes = new ArrayList<>();
  private String outline;
  private String language;
  private String simulationType;
  private ResourceBundle bundle;
  private ResourceBundle text;
  private ResourceBundle button;
  /**
   *
   * @param defaultLanguage String, default language from config
   * @param defaultEdge String, default edge from config
   * @param parameters Map, all parameters of the current simulation
   * @param simulationType String, type of simulation
   * @param applyButtonHandler Event handler, sets the hook for the apply button to let controller
   *                           handle save the changes settings
   */
  public Settings(String defaultLanguage, String defaultEdge, Map<String, Double> parameters,
      String simulationType, EventHandler<ActionEvent> applyButtonHandler) {

    button = ResourceBundle.getBundle(SimulationPage.DEFAULT_RESOURCE_PACKAGE+SimulationPage.ENGLISH_BUTTON);
    bundle = ResourceBundle.getBundle(SimulationPage.DEFAULT_RESOURCE_PACKAGE+SimulationPage.CONFIG_RESOURCE_FILE);
    text = ResourceBundle.getBundle(Controller.TEXT_CONFIGURATION);

    avaliableOptions = ResourceBundle.getBundle(OPTIONS_PACKAGE);
    settingsPanel = new Stage();
    settingsPanel.setTitle(text.getString(SETTINGS_TITLE_KEY));

    this.simulationType = simulationType;
    this.edge = defaultEdge;
    this.outline = "On";
    this.parameters = parameters;
    this.language = defaultLanguage;

    root = new VBox(SETTINGS_BOX_SPACING);
    root.setPadding(new Insets(Integer.parseInt(bundle.getString(PADDING_KEY))));
    scene = new Scene(root, SETTINGS_SCENE_WIDTH, SETTINGS_SCENE_HEIGHT);
    settingsPanel.setScene(scene);
    setPanelFields();
    createComboBoxes();

    saveParametersButton = new Button(button.getString(APPLY_BUTTON_KEY));
    saveParametersButton.setOnAction(applyButtonHandler);
    root.getChildren().add(saveParametersButton);
  }

  private void createComboBoxes() {
    initializeEdgeTypes();
    initializeOutlineTypes();
    initializeLanguages();
    edgeTypeComboBox = new ComboBox<>(FXCollections.observableList(avaliableEdgeTypes));
    outlineTypeComboBox = new ComboBox<>(FXCollections.observableList(avaliableCellOutlines));
    languageComboBox = new ComboBox<>(FXCollections.observableList(avaliableLanguages));
    setComboBox(edge, text.getString(EDGE_TYPE_KEY), edgeTypeComboBox);
    setComboBox(outline, text.getString(OUTLINE_TYPE_KEY), outlineTypeComboBox);
    setComboBox(language, text.getString(SELECT_LANGUAGE_KEY), languageComboBox);
  }


  private void initializeEdgeTypes() {
    avaliableEdgeTypes = new ArrayList<>();
    avaliableEdgeTypes.add(avaliableOptions.getString(EDGE_NORMAL_KEY));
    avaliableEdgeTypes.add(avaliableOptions.getString(EDGE_WARPED_KEY));
  }

  private void initializeOutlineTypes() {
    avaliableCellOutlines = new ArrayList<>();
    avaliableCellOutlines.add(avaliableOptions.getString(OUTLINE_ON_KEY));
    avaliableCellOutlines.add(avaliableOptions.getString(OUTLINE_OFF_KEY));
  }

  private void initializeLanguages() {
    avaliableLanguages = new ArrayList<>();
    String languages = avaliableOptions.getString("languages");
    avaliableLanguages.addAll(Arrays.stream(languages.split(" ")).toList());
  }

  private void setComboBox(String defaultValue, String label, ComboBox<String> dropdown) {
    HBox box = new HBox();
    Label comboBoxLabel = new Label(label);
    dropdown.getSelectionModel().select(defaultValue); // Set default selection
    box.getChildren().addAll(comboBoxLabel, dropdown);
    ComboBox<String> finalDropdown = dropdown;
    root.getChildren().add(box);
    comboBoxes.add(finalDropdown);
  }


  private void setPanelFields() {
    for (Entry<String, Double> entry : parameters.entrySet()) {
      if (!simulationType.equals(Controller.SUGAR) || entry.getKey().equals("growBackRate")) {
        Label spinnerLabel = new Label(entry.getKey());
        Spinner<Double> numberSpinner = new Spinner<>();
        numberSpinner.setEditable(true);
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new DoubleSpinnerValueFactory(
            Integer.parseInt(bundle.getString(VALUE_FACTORY_MIN_KEY)),
            Integer.parseInt(bundle.getString(VALUE_FACTORY_MAX_KEY)), entry.getValue(), Double.parseDouble(bundle.getString(AMOUNT_STEP_BY_KEY)));
        numberSpinner.setValueFactory(valueFactory);
        numberSpinner.setPrefWidth(Integer.parseInt(bundle.getString(VALUE_FACTORY_WIDTH)));
        numberSpinner.setUserData(entry.getKey());
        HBox hbox = new HBox(SETTINGS_BOX_SPACING);
        hbox.getChildren().addAll(spinnerLabel, numberSpinner);
        numberSpinners.add(numberSpinner);
        root.getChildren().add(hbox);
      }
    }
  }

  /**
   * gets the data from the spinner and dropdown boxes and save it in the instance variables in
   * settings
   */
  public void saveChanges() {
    extractSpinnerData();
    extractComboBoxData();
  }

  private void extractComboBoxData() {
    for (ComboBox<String> comboBox : comboBoxes) {
      if (comboBox.equals(outlineTypeComboBox)) {
        outline = comboBox.getValue();
      } else if (comboBox.equals(edgeTypeComboBox)) {
        edge = comboBox.getValue();
      } else if (comboBox.equals(languageComboBox)) {
        language = comboBox.getValue();
      }
    }
  }

  private void extractSpinnerData() {
    for (Spinner<Double> spinner : numberSpinners) {
      String label = (String) spinner.getUserData();
      double value = spinner.getValue();
      parameters.put(label, value);
    }
  }

  /**
   * gets the current outline type
   * @return boolean flag (on/off) of the outline
   */
  public boolean getOutlineType() {
    return outline.equals("On");
  }

  /**
   * displays the settings panel
   */
  public void showSettingsPanel() {
    settingsPanel.show();
  }

  /**
   * hides the settings panel
   */
  public void closeSettingsPanel() {
    settingsPanel.hide();
  }

  /**
   * gets the current parameters
   * @return Map of parameters
   */
  public Map<String, Double> getNewParameters() {
    return parameters;
  }

  /**
   * get current edge type
   * @return String, edge type
   */
  public String getNewEdgeType() {
    return edge;
  }

  /**
   * gets the current language
   * @return String, language
   */
  public String getNewLanguage() {
    return language;
  }

}

