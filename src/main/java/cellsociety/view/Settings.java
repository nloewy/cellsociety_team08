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

public class Settings {

  public static final int SETTINGS_BOX_SPACING = 10;
  public static final int SETTINGS_SCENE_WIDTH = 350;
  public static final int SETTINGS_SCENE_HEIGHT = 400;
  private static final String OPTIONS_PACKAGE = "cellsociety.avaliableOptions";
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


  public Settings(String defaultLanguage, String defaultEdge, Map<String, Double> parameters,
      String simulationType, EventHandler<ActionEvent> applyButtonHandler) {

    avaliableOptions = ResourceBundle.getBundle(OPTIONS_PACKAGE);
    settingsPanel = new Stage();
    settingsPanel.setTitle("Parameter Settings");

    this.simulationType = simulationType;
    this.edge = defaultEdge;
    this.outline = "On";
    this.parameters = parameters;
    this.language = defaultLanguage;

    root = new VBox(SETTINGS_BOX_SPACING);
    root.setPadding(new Insets(10));
    scene = new Scene(root, SETTINGS_SCENE_WIDTH, SETTINGS_SCENE_HEIGHT);
    settingsPanel.setScene(scene);
    setPanelFields();
    createComboBoxes();

    saveParametersButton = new Button("Apply");
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
    setComboBox(edge, "Edge Type: ", edgeTypeComboBox);
    setComboBox(outline, "Outline Type: ", outlineTypeComboBox);
    setComboBox(language, "Select Language: ", languageComboBox);
  }


  private void initializeEdgeTypes() {
    avaliableEdgeTypes = new ArrayList<>();
    avaliableEdgeTypes.add("Normal");
    avaliableEdgeTypes.add("Warped");
  }

  private void initializeOutlineTypes() {
    avaliableCellOutlines = new ArrayList<>();
    avaliableCellOutlines.add("On");
    avaliableCellOutlines.add("Off");
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
            0,
            100, entry.getValue(), 0.1);
        numberSpinner.setValueFactory(valueFactory);
        numberSpinner.setPrefWidth(80);
        numberSpinner.setUserData(entry.getKey());
        HBox hbox = new HBox(SETTINGS_BOX_SPACING);
        hbox.getChildren().addAll(spinnerLabel, numberSpinner);
        numberSpinners.add(numberSpinner);
        root.getChildren().add(hbox);
      }
    }
  }

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

  public boolean getOutlineType() {
    return outline.equals("On");
  }

  public void showSettingsPanel() {
    settingsPanel.show();
  }

  public void closeSettingsPanel() {
    settingsPanel.hide();
  }

  public Map<String, Double> getNewParameters() {
    return parameters;
  }

  public String getNewEdgeType() {
    return edge;
  }

  public String getNewLanguage() {
    return language;
  }

}

