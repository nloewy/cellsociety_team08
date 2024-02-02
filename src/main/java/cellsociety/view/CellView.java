package cellsociety.view;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class CellView extends Region {
    private Rectangle r;
//    XMLParser parser;

    public CellView(int xPos, int yPos, int state) {
//        parser = new XMLParser(); //TODO: add parameters after it's implemented
        r = new Rectangle(xPos,yPos,10,10); //TODO: read width and height from css
//        r.setFill(Color.BLACK);

        //testing grid iterator in simualtion page
        if (state == 0){
            r.setFill(Color.BLACK);
        } else {
            r.setFill(Color.PINK);
        }

        r.setStroke(Color.WHITE); //TODO: read color from css file
        r.setStrokeWidth(1);
    }

    public void updateState(int state){

        if (state == 0){
            r.setFill(Color.BLACK);
        } else {
            r.setFill(Color.PINK);
        }

        //TODO: read color from css file
    }

    public Rectangle getCellGraphic(){
        return r;
    }
}
