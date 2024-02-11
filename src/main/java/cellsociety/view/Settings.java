package cellsociety.view;

import java.util.Map;
import java.util.Map.Entry;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Settings {
  private Stage settingsPanel;
  private Scene scene;
  private VBox root;
  private Map<String, Double> parameters;
  private Button saveParametersButton;

  public Settings(Map<String, Double> parameters) {
    settingsPanel = new Stage();
    settingsPanel.setTitle("Parameter Settings");

    this.parameters = parameters;

    root = new VBox(10);
    root.setPadding(new Insets(10));
    scene = new Scene(root, 350,400);
    settingsPanel.setScene(scene);

    setPanelFields();

    saveParametersButton = new Button("Apply");
    saveParametersButton.setOnAction(event -> saveParameters());
    root.getChildren().add(saveParametersButton);
  }

  private void setPanelFields() {
    for (Entry<String, Double> entry : parameters.entrySet()){
      Label spinnerLabel = new Label(entry.getKey());
      Spinner<Double> numberSpinner = new Spinner<>();
      numberSpinner.setEditable(true);
      SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new DoubleSpinnerValueFactory(0,100,entry.getValue(),0.1);
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

  public void saveParameters() {
    for (Node node : root.getChildren()) {
      if (node instanceof HBox) {
        HBox hbox = (HBox) node;
        for (Node child : hbox.getChildren()) {
          if (child instanceof Spinner) {
            Spinner<Double> spinner = (Spinner<Double>) child;
            String label = (String) spinner.getUserData();
            double value = spinner.getValue();
            parameters.put(label, value);
          }
        }
      }
    }
    // Do something with the updated parameters map
    System.out.println("Updated parameters: " + parameters);
  }

  public void showSettingsPanel(){
    settingsPanel.show();
  }

  public void hideSettingsPanel(){
    settingsPanel.hide();
  }

}

