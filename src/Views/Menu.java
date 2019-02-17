package Views;

import Dungeon.*;
import Dungeon.Dungeon_Element.Rocks;
import Dungeon.Tile.*;
import Rendering.AssetDrawing;
import Unit.Goblin;
import Unit.Hero;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends Application{

    public static int TILE_SIZE = 32;

    @FXML
    private Canvas myCanvas;

    private Dungeon dungeon;

    //TODO its just testing music
//    Media hit = new Media(new File("src/Sound/jugg.mp3").toURI().toString());
//    MediaPlayer mediaPlayer = new MediaPlayer(hit);


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(850);

        primaryStage.setTitle("Dungeon!");
        primaryStage.setScene(scene);


        primaryStage.show();
    }

    @FXML
    void initialize() {
        dungeon = new Dungeon(200,200);
        new DungeonGenerator(dungeon).generateDungeon();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }

    //TODO I will not use this class
    private void draw(GraphicsContext gc) {
//        gc.clearRect(0,0, dungeon.getDungeonWidth() * TILE_SIZE, dungeon.getDungeonHeight() * TILE_SIZE);
//
//        int y = -1;
//            for(int i = 0; i < dungeon.getDungeonHeight(); i++){ //todo dungeonGenerator.dungeonHeight
//                y++;
//                int x = 0;
//                    for(int j = 0; j < dungeon.getDungeonWidth(); j++){ // //todo dungeonGenerator.dungeonWidth
//
//                        if(dungeon.getDungeonMatrix().get(i).get(j) instanceof Room){
//                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.roomImage);
//                        }
//                        else if(dungeon.getDungeonMatrix().get(i).get(j) instanceof Wall){
//                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.wallImage);
//                        }
//                        else if(dungeon.getDungeonMatrix().get(i).get(j) instanceof Entrance){
//                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.corridorImage);
//                            AssetDrawing.drawPictureSmaller(gc, x, y, AssetDrawing.doorImage, 0.2);
//                        }
//                        else if(dungeon.getDungeonMatrix().get(i).get(j) instanceof Corridor){
//                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.corridorImage);
//                        }
//                        else {
//                            gc.setFill(Color.rgb(10, 106, 114));
//                            gc.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
//                        }
//
//
//
//                        //TODO i make extra IF!
//                        if(dungeon.getDungeonMatrix().get(i).get(j).getBasicUnit() instanceof Hero){//todo its is just for testing
//                            AssetDrawing.drawPictureSmaller(gc, x, y, dungeon.getDungeonMatrix().get(i).get(j).getBasicUnit().getBaseCharacterImage(), 0.2);
//                        }
//                        if(dungeon.getDungeonMatrix().get(i).get(j).getBasicUnit() instanceof Goblin){//todo its is just for testing
//                            AssetDrawing.drawPictureSmaller(gc, x, y, AssetDrawing.gnollAvatar, 0.2);
//                        }
//                        if(dungeon.getDungeonMatrix().get(i).get(j).getBaseElement() instanceof Rocks){//todo its is just for testing
//                            AssetDrawing.drawPictureSmaller(gc, x, y, AssetDrawing.rubbleImage, 0.2);
//                        }
//                        x++;
//                    }
//        }
    }
    @FXML
    public void callSave(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            try {
                WritableImage writableImage = new WritableImage(dungeon.getDungeonWidth() * TILE_SIZE, dungeon.getDungeonHeight() * TILE_SIZE - 1);
                myCanvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "PNG", file);
            } catch (IOException ex) {
                //  ¯\_(ツ)_/¯
            }
        }
    }
}

