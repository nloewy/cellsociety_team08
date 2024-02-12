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
import javafx.scene.Node;
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
  private HBox edgeBox;
  private ComboBox<String> outlineTypeComboBox;
  private HBox outlineBox;
  private ComboBox<String> languageComboBox;
  private HBox languageBox;
  private String edge;
  private String outline;
  private String language;


  public Settings(String defaultLanguage, String defaultEdge, Map<String, Double> parameters,
      EventHandler<ActionEvent> applyButtonHandler) {

    avaliableOptions = ResourceBundle.getBundle(OPTIONS_PACKAGE);
    settingsPanel = new Stage();
    settingsPanel.setTitle("Parameter Settings");

    this.edge = defaultEdge;
    this.outline = "On";
    this.parameters = parameters;
    this.language = defaultLanguage;
    initializeEdgeTypes();
    initializeOutlineTypes();
    initializeLanguages();

    root = new VBox(10);
    root.setPadding(new Insets(10));
    scene = new Scene(root, 350, 400);
    settingsPanel.setScene(scene);

    setPanelFields();
//    setComboBox(defaultEdge, "Edge Type: ", avaliableEdgeTypes, edgeTypeComboBox, edgeBox);
//    setComboBox(outline, "Outline Type: ", avaliableCellOutlines, outlineTypeComboBox, outlineBox);
    setEditEdge(defaultEdge);
    setEditOutline(outline);
    setEditLanguage(defaultLanguage);

    saveParametersButton = new Button("Apply");
    saveParametersButton.setOnAction(applyButtonHandler);
    root.getChildren().add(saveParametersButton);
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

  private void setEditEdge(String defaultEdge) {
    HBox edgeTypeBox = new HBox(10); // Create an HBox to contain the label and the ComboBox
    Label edgeTypeLabel = new Label("Edge Type");

    edgeTypeComboBox = new ComboBox<>(FXCollections.observableList(avaliableEdgeTypes));
    edgeTypeComboBox.getSelectionModel().select(defaultEdge); // Set default selection

    edgeTypeBox.getChildren().addAll(edgeTypeLabel, edgeTypeComboBox);

    root.getChildren().add(edgeTypeBox);
  }

  private void setEditOutline(String defaultOutline) {
    HBox outlineTypeBox = new HBox(10); // Create an HBox to contain the label and the ComboBox
    Label outlineTypeLabel = new Label("Outline Type");

    outlineTypeComboBox = new ComboBox<>(FXCollections.observableList(avaliableCellOutlines));
    outlineTypeComboBox.getSelectionModel().select(defaultOutline); // Set default selection

    outlineTypeBox.getChildren().addAll(outlineTypeLabel, outlineTypeComboBox);

    root.getChildren().add(outlineTypeBox);
  }

  private void setEditLanguage(String defaultLanguage) {
    HBox languageBox = new HBox(10); // Create an HBox to contain the label and the ComboBox
    Label languageLabel = new Label("Language: ");

    languageComboBox = new ComboBox<>(FXCollections.observableList(avaliableLanguages));
    languageComboBox.getSelectionModel().select(defaultLanguage); // Set default selection

    languageBox.getChildren().addAll(languageLabel, languageComboBox);

    root.getChildren().add(languageBox);
  }
//
//  private void setComboBox(String defaultValue, String label, List<String> options, ComboBox<String> dropdown, HBox box) {
//    box = new HBox(10); // Create an HBox to contain the label and the ComboBox
//    Label comboBoxLabel = new Label(label);
//
//    dropdown = new ComboBox<>(FXCollections.observableList(options));
//    dropdown.getSelectionModel().select(defaultValue); // Set default selection
//
//    box.getChildren().addAll(comboBoxLabel, dropdown);
//
//    ComboBox<String> finalDropdown = dropdown;
//    dropdown.setOnAction(event -> {
//      String selectedValue = finalDropdown.getSelectionModel().getSelectedItem();
//      System.out.println("Selected " + label + ": " + selectedValue);
//      // You can associate the selected value with a label here
//    });
//    root.getChildren().add(box);
//  }


  private void setPanelFields() {
    for (Entry<String, Double> entry : parameters.entrySet()) {
      Label spinnerLabel = new Label(entry.getKey());
      Spinner<Double> numberSpinner = new Spinner<>();
      numberSpinner.setEditable(true);
      SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new DoubleSpinnerValueFactory(0,
          100, entry.getValue(), 0.1);
      numberSpinner.setValueFactory(valueFactory);
      numberSpinner.setPrefWidth(80);

      numberSpinner.setUserData(entry.getKey());

      numberSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
        String fieldIdentifier = (numberSpinner.getUserData()).toString();
      });

      HBox hbox = new HBox(10);
      hbox.getChildren().add(spinnerLabel);
      hbox.getChildren().add(numberSpinner);

      root.getChildren().add(hbox);
    }
  }

  public void saveChanges() {
    for (Node node : root.getChildren()) {
      if (node instanceof HBox hbox) {
        for (Node child : hbox.getChildren()) {
          if (child instanceof Spinner) {
            Spinner<Double> spinner = (Spinner<Double>) child;
            String label = (String) spinner.getUserData();
            double value = spinner.getValue();
            parameters.put(label, value);
          }
          if (child instanceof ComboBox) {
            if (child.equals(outlineTypeComboBox)) {
              ComboBox<String> outlineDropdown = (ComboBox<String>) child;
              outline = outlineDropdown.getValue();
            } else if (child.equals(edgeTypeComboBox)) {
              ComboBox<String> edgeDropdown = (ComboBox<String>) child;
              edge = edgeDropdown.getValue();
            } else if (child.equals(languageComboBox)) {
              ComboBox<String> languageDropdown = (ComboBox<String>) child;
              language = languageDropdown.getValue();
            }
          }
        }
      }
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

