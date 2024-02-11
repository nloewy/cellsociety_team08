package cellsociety.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

  private final Stage settingsPanel;
  private final Scene scene;
  private final VBox root;
  private final Map<String, Double> parameters;
  private final Button saveParametersButton;
  private List<String> avaliableEdgeTypes;
  private List<String> avaliableCellOutlines;

  private ComboBox<String> edgeTypeComboBox;
  private ComboBox<String> outlineTypeComboBox;

  private String edge;
  private String outline;


  public Settings(String defaultEdge, Map<String, Double> parameters,
      EventHandler<ActionEvent> applyButtonHandler) {
    settingsPanel = new Stage();
    settingsPanel.setTitle("Parameter Settings");

    this.edge = defaultEdge;
    this.outline = "On";
    this.parameters = parameters;
    initializeEdgeTypes();
    initializeOutlineTypes();

    root = new VBox(10);
    root.setPadding(new Insets(10));
    scene = new Scene(root, 350, 400);
    settingsPanel.setScene(scene);

    setPanelFields();
    setEditEdge(defaultEdge);
    setEditOutline(outline);

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

  private void setEditEdge(String defaultEdge) {
    HBox edgeTypeBox = new HBox(10); // Create an HBox to contain the label and the ComboBox
    Label edgeTypeLabel = new Label("Edge Type");

    edgeTypeComboBox = new ComboBox<>(FXCollections.observableList(avaliableEdgeTypes));
    edgeTypeComboBox.getSelectionModel().select(defaultEdge); // Set default selection

    edgeTypeBox.getChildren().addAll(edgeTypeLabel, edgeTypeComboBox);

    edgeTypeComboBox.setOnAction(event -> {
      String selectedEdgeType = edgeTypeComboBox.getSelectionModel().getSelectedItem();
      System.out.println("Selected edge type: " + selectedEdgeType);
      // You can associate the selected edge type with a label here
    });
    root.getChildren().add(edgeTypeBox);
  }

  private void setEditOutline(String defaultOutline) {
    HBox outlineTypeBox = new HBox(10); // Create an HBox to contain the label and the ComboBox
    Label outlineTypeLabel = new Label("Outline Type");

    outlineTypeComboBox = new ComboBox<>(FXCollections.observableList(avaliableCellOutlines));
    outlineTypeComboBox.getSelectionModel().select(defaultOutline); // Set default selection

    outlineTypeBox.getChildren().addAll(outlineTypeLabel, outlineTypeComboBox);

    outlineTypeComboBox.setOnAction(event -> {
      String selectedOutlineType = outlineTypeComboBox.getSelectionModel().getSelectedItem();
      System.out.println("Selected outline type: " + selectedOutlineType);
      // You can associate the selected edge type with a label here
    });
    root.getChildren().add(outlineTypeBox);
  }

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
            }
          }
        }
      }
    }
    // Do something with the updated parameters map
    System.out.println("Updated parameters: " + parameters);
  }

  public boolean getOutlineType() {
    return outline == "On";
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

}

