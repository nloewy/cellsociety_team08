package cellsociety.view;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public abstract class CellView extends Region {
    private Rectangle r;

    public CellView(int state, double width, double height) {
        r = new Rectangle(width,height); //TODO: read width and height from css

        //testing grid iterator in simualtion page
        setColors(state);

        r.setStroke(Color.BLACK); //TODO: read color from css file
        r.setStrokeWidth(1);
        r.setStrokeType(StrokeType.OUTSIDE);
    }

    public abstract void setColors(int state);


    public void updateState(int state){
        setColors(state);
        System.out.println(state);
    }

    public void getCSS(String idName){
        r.setId(idName);
        r.getStyleClass().add(idName);
    }

    public Rectangle getCellGraphic(){
        return r;
    }

    //TODO: add the r's to the gridpane not the cellviews, avoid extending the region because you don't want to add a bunch of unnecesary packages when extending.
}
