package cellsociety;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import cellsociety.model.simulation.FireSimulation;
import cellsociety.view.Controller;
import cellsociety.view.SceneManager;
import cellsociety.view.SimulationPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {

  /**
   * @see Application#start(Stage)
   */
  @Override
  public void start(Stage primaryStage) {
    Controller game = new Controller();
  }

    /**
     * Returns number of blocks needed to cover the width and height given in the data file.
     */
    public int calculateNumBlocks(File xmlFile) {
        try {
            Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            Element root = xmlDocument.getDocumentElement();
            int width = Integer.parseInt(getTextValue(root, "width"));
            int height = Integer.parseInt(getTextValue(root, "height"));
            return width * height;
        }
        catch (NumberFormatException e) {
            showMessage(AlertType.ERROR, "Invalid number given in data");
            return 0;
        }
        catch (ParserConfigurationException e) {
            showMessage(AlertType.ERROR, "Invalid XML Configuration");
            return 0;
        }
        catch (SAXException | IOException e) {
            showMessage(AlertType.ERROR, "Invalid XML Data");
            return 0;
        }
    }

    /**
     * A method to test getting internal resources.
     */
    public double getVersion () {
        ResourceBundle resources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);
        return Double.parseDouble(resources.getString("Version"));
    }

    // get value of Element's text
    private String getTextValue (Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or exception? In some cases it may be an error to not find any text
            return "";
        }
    }

    // display given message to user using the given type of Alert dialog box
    void showMessage (AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }

    // set some sensible defaults when the FileChooser is created
    private static FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(DATA_FILE_FOLDER));
        result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Data Files", extensionAccepted));
        return result;
    }

    /**
     * Start the program, give complete control to JavaFX.
     *
     * Default version of main() is actually included within JavaFX, so this is not technically necessary!
     */
    public static void main (String[] args) {
        launch(args);
    }
}
