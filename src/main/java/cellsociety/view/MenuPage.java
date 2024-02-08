//package cellsociety.view;
//
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//
//public class MenuPage {
//
//  private Group root;
//  private Scene menu;
//
//  static final int FONT_SIZE = 20;
//
//  public MenuPage(double width, double height) {
//    root = new Group();
//    menu = new Scene(root, width, height);
//
//    Text cellSocietyTitle = new Text("Cell Society Simulation");
//    cellSocietyTitle.setY(height / 4 - cellSocietyTitle.getLayoutBounds().getHeight());
//    cellSocietyTitle.setX(width / 2 - (cellSocietyTitle.getLayoutBounds().getWidth() / 2));
//    cellSocietyTitle.setFont(new Font(FONT_SIZE));
//
//    Button chooseDataFile = createButton("Choose Data File");
//
////        Button gameOfLife = createButton("Conway’s Game of Life");
////        Button fire = createButton("Spreading of Fire");
////        Button segregation = createButton("Schelling’s Model of Segregation");
////        Button world = createButton("Wa-Tor World");
////        Button percolation = createButton("Percolation");
////
////        root.getChildren().addAll(cellSocietyTitle, gameOfLife, fire, segregation, world,
//                                    percolation);
//  }
//
//  private Button createButton(String buttonText) {
//    Button b = new Button(buttonText);
//    b.setMaxSize(100, 50);
//    b.setLayoutX(300);
//    b.setLayoutY(400);
////        b.setOnAction(new EventHandler<ActionEvent>() {
////            @Override
////            public void handle(ActionEvent event) {
////
////            }
////        });
//    return b;
//  }
//
//  public Scene getMenu() {
//    return menu;
//  }
//}
