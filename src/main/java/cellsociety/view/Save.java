package cellsociety.view;

import java.util.ResourceBundle;
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

  public static final String SETTINGS_BOX_SPACING_KEY = "SETTINGS_BOX_SPACING";
  public static final String SETTINGS_SCENE_WIDTH_KEY = "SETTINGS_SCENE_WIDTH";
  public static final String SETTINGS_SCENE_HEIGHT_KEY = "SETTINGS_SCENE_HEIGHT";
  public static final String SAVE_BUTTON_KEY = "SaveButton";
  public static final String SAVE_TITLE_KEY = "saveTitle";
  public static final String SAVE_AUTHOR_KEY = "saveAuthor";
  public static final String SAVE_DES_KEY = "saveDes";
  public static final String SAVE_LOCATION_KEY = "saveLocation";
  public static final String DEFAULT_TITLE_KEY = "defaultTitle";
  public static final String DEFAULT_AUTHOR_KEY = "defaultAuthor";
  public static final String DEFAULT_DES_KEY = "defaultDes";
  public static final String DEFAULT_LOCATION_KEY = "defaultLocation";
  private final Scene scene;
  private final VBox root;

  private Stage savePanel;
  private TextField titleTextField;
  private TextField authorTextField;
  private TextField descriptionTextField;
  private TextField saveLocationTextField;
  private String title;
  private ResourceBundle buttons;
  private String author;
  private ResourceBundle config;
  private String description;
  private String saveLocation;
  private ResourceBundle textbundle;


  /**
   * initializes the saving screen
   *
   * @param title               String, title of simulation being saved
   * @param author              String, author of simulation being saved
   * @param description         String, description of simulation being saved
   * @param saveLocation        String, location to save to
   * @param applyButtonHandler, event handler, handles the apply button click
   */
  public Save(String title, String author, String description, String saveLocation,
      EventHandler<ActionEvent> applyButtonHandler) {
    this.config = ResourceBundle.getBundle(
        SimulationPage.DEFAULT_RESOURCE_PACKAGE + SimulationPage.CONFIG_RESOURCE_FILE);
    this.buttons = ResourceBundle.getBundle(
        SimulationPage.DEFAULT_RESOURCE_PACKAGE + SimulationPage.ENGLISH_BUTTON);
    this.textbundle = ResourceBundle.getBundle(Controller.TEXT_CONFIGURATION);

    this.title = title;
    this.author = author;
    this.description = description;
    this.saveLocation = saveLocation;
    savePanel = new Stage();

    root = new VBox(Integer.parseInt(config.getString(SETTINGS_BOX_SPACING_KEY)));
    root.setPadding(new Insets(Integer.parseInt(config.getString(Settings.PADDING_KEY))));
    scene = new Scene(root, Integer.parseInt(config.getString(SETTINGS_SCENE_WIDTH_KEY)),
        Integer.parseInt(config.getString(SETTINGS_SCENE_HEIGHT_KEY)));
    savePanel.setScene(scene);
    savePanel.setTitle(buttons.getString(SimulationPage.SAVE_BUTTON_KEY));

    titleTextField = createTextField(textbundle.getString(DEFAULT_TITLE_KEY), title);
    authorTextField = createTextField(textbundle.getString(DEFAULT_AUTHOR_KEY), author);
    descriptionTextField = createTextField(textbundle.getString(DEFAULT_DES_KEY), description);
    saveLocationTextField = createTextField(textbundle.getString(DEFAULT_LOCATION_KEY),
        saveLocation);

    Button saveButton = new Button(buttons.getString(SAVE_BUTTON_KEY));

    saveButton.setOnAction(applyButtonHandler);
    root.getChildren().addAll(
        new Label(textbundle.getString(SAVE_TITLE_KEY)), titleTextField,
        new Label(textbundle.getString(SAVE_AUTHOR_KEY)), authorTextField,
        new Label(textbundle.getString(SAVE_DES_KEY)), descriptionTextField,
        new Label(textbundle.getString(SAVE_LOCATION_KEY)), saveLocationTextField,
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
   *
   * @return String, title of the config file
   */
  public String getTitle() {
    return title;
  }

  /**
   * gets the current author
   *
   * @return String, name of the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * gets the description of the simulation
   *
   * @return String, current description
   */
  public String getDescription() {
    return description;
  }

  /**
   * gets the location for the file to be saved to
   *
   * @return String, the location
   */
  public String getSaveLocation() {
    return saveLocation;
  }

}

