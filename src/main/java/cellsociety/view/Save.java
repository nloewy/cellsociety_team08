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

/**
 * this class is the saving simulation screen object
 */
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


  /**
   * initializes the saving screen
   * @param title String, title of simulation being saved
   * @param author String, author of simulation being saved
   * @param description String, description of simulation being saved
   * @param saveLocation String, location to save to
   * @param applyButtonHandler, event handler, handles the apply button click
   */
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

  /**
   * displays the edit saving screen
   */
  public void showSavePanel() {
    savePanel.show();
  }

  /**
   * hides the edit saving screen
   */
  public void hideSavePanel() {
    savePanel.hide();
  }


  private TextField createTextField(String labelText, String defaultValue) {
    TextField textField = new TextField(defaultValue);
    textField.setPromptText(labelText);
    return textField;
  }

  /**
   * changes the title, author, description and location to the new input value
   */
  public void updateValues() {
    title = titleTextField.getText();
    author = authorTextField.getText();
    description = authorTextField.getText();
    saveLocation = saveLocationTextField.getText();
  }

  /**
   * gets the current title
   * @return String, title of the config file
   */
  public String getTitle() {
    return title;
  }

  /**
   * gets the current author
   * @return String, name of the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * gets the description of the simulation
   * @return String, current description
   */
  public String getDescription() {
    return description;
  }

  /**
   * gets the location for the file to be saved to
   * @return String, the location
   */
  public String getSaveLocation() {
    return saveLocation;
  }

}

