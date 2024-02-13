package cellsociety.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Save {

  public static final int SETTINGS_BOX_SPACING = 10;
  public static final int SETTINGS_SCENE_WIDTH = 350;
  public static final int SETTINGS_SCENE_HEIGHT = 400;
  private final Scene scene;
  private final VBox root;

  private Stage savePanel;
  private TextField titleTextField;
  private TextField authorTextField;
  private TextField descriptionTextField;
  private TextField saveLocationTextField;
  private String title;
  private String author;
  private String description;
  private String saveLocation;


  public Save(String title, String author, String description, String saveLocation,
      EventHandler<ActionEvent> applyButtonHandler) {
    this.title = title;
    this.author = author;
    this.description = description;
    this.saveLocation = saveLocation;
    savePanel = new Stage();

    root = new VBox(SETTINGS_BOX_SPACING);
    root.setPadding(new Insets(10));
    scene = new Scene(root, SETTINGS_SCENE_WIDTH, SETTINGS_SCENE_HEIGHT);
    savePanel.setScene(scene);
    savePanel.setTitle("Save Simulation");

    titleTextField = createTextField("Title", title);
    authorTextField = createTextField("Author", author);
    descriptionTextField = createTextField("Description", description);
    saveLocationTextField = createTextField("Save Location", saveLocation);

    Button saveButton = new Button("Save");

    saveButton.setOnAction(applyButtonHandler);
    root.getChildren().addAll(
        new Label("Title: "), titleTextField,
        new Label("Author: "), authorTextField,
        new Label("Description: "), descriptionTextField,
        new Label("Save Location: "), saveLocationTextField,
        saveButton
    );
  }

  public void showSavePanel() {
    savePanel.show();
  }

  public void hideSavePanel() {
    savePanel.hide();
  }

  private TextField createTextField(String labelText, String defaultValue) {
    TextField textField = new TextField(defaultValue);
    textField.setPromptText(labelText);
    return textField;
  }

  public void updateValues() {
    title = titleTextField.getText();
    author = authorTextField.getText();
    description = authorTextField.getText();
    saveLocation = saveLocationTextField.getText();
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getDescription() {
    return description;
  }

  public String getSaveLocation() {
    return saveLocation;
  }

}

