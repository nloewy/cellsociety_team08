package cellsociety.view;

import cellsociety.configuration.XMLParser;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.annotation.Retention;

public class CellView extends Region {
    private Rectangle r;
    XMLParser parser;

    public CellView(int xPos, int yPos) {
        parser = new XMLParser(); //TODO: add parameters after it's implemented
        r = new Rectangle(xPos,yPos,10,10); //TODO: change xy to the arguments
        r.setFill(Color.BLACK);
        r.setStroke(Color.WHITE); //TODO: read color from XML parser
        r.setStrokeWidth(1);
    }

    public void updateColor(int state){
        r.setFill(Color.BLUE);  //TODO: read color from XML parser
    }

    public Rectangle getCellGraphic(){
        return r;
    }
}
