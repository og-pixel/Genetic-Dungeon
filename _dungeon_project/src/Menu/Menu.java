package Menu;

import Element.Rocks;
import Errors.SumErrorException;
import Tile.*;

import Unit.*;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends Application{

    static int TILE_SIZE = 32;

    @FXML
    private Canvas myCanvas;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView2;

    private Dungeon dungeon;
    private int mapWidth, mapHeight = 0;




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
        //        dungeon = new Dungeon(350,150);
        dungeon = new Dungeon(350,200, 500,6,65,15);
        dungeon.createPlayerDebug();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }

    //TODO SPECIAL I think in the end this special method will either compare just to class (if it always the same picture)
    //OR other object holds info about itself to be retreived, for hero can have different avatar
    //todo work on how this code is read, its kinda confusing
    private void draw(GraphicsContext gc) {
        float currentTime = System.nanoTime();
        gc.clearRect(0,0,dungeon.dungeonWidth * TILE_SIZE,dungeon.dungeonHeight * TILE_SIZE);

        BasicUnit player = dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit();

        //TODO THIS IS TEMP
        imageView.setImage(player.passives.get(0).getPassiveImage());
//        imageView2.setImage(player.passives.get(1).getPassiveImage());

        int y = -1;
            for(int i = mapHeight; i < mapHeight + dungeon.dungeonHeight; i++){ //todo dungeon.dungeonHeight
                y++;
                int x = 0;
                    for(int j = mapWidth; j < mapWidth + dungeon.dungeonWidth; j++){ // //todo dungeon.dungeonWidth

                        if(dungeon.dungeonMatrix.get(i).get(j) instanceof Room){
                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.roomImage);
                        }
                        else if(dungeon.dungeonMatrix.get(i).get(j) instanceof Wall){
                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.wallImage);
                        }
                        else if(dungeon.dungeonMatrix.get(i).get(j) instanceof Entrance){
                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.corridorImage);
                            AssetDrawing.drawPictureSmaller(gc, x, y, AssetDrawing.doorImage, 0.2);
                        }
                        else if(dungeon.dungeonMatrix.get(i).get(j) instanceof Corridor){
                            AssetDrawing.drawPicture(gc, x, y, AssetDrawing.corridorImage);
                        }
                        else {
                            gc.setFill(Color.rgb(10, 106, 114));
                            gc.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
                        }



                        //TODO i make extra IF!
                        if(dungeon.dungeonMatrix.get(i).get(j).getBasicUnit() instanceof Hero){//todo its is just for testing
                            AssetDrawing.drawPictureSmaller(gc, x, y, dungeon.dungeonMatrix.get(i).get(j).getBasicUnit().getBaseCharacterImage(), 0.2);
                        }
                        if(dungeon.dungeonMatrix.get(i).get(j).getBasicUnit() instanceof Goblin){//todo its is just for testing
                            AssetDrawing.drawPictureSmaller(gc, x, y, AssetDrawing.gnollAvatar, 0.2);
                        }
                        if(dungeon.dungeonMatrix.get(i).get(j).getBaseElement() instanceof Rocks){//todo its is just for testing
                            AssetDrawing.drawPictureSmaller(gc, x, y, AssetDrawing.rubbleImage, 0.2);
                        }
                        x++;
                    }
        }
    }

    //TODO this method should redraw only the important part of the dungeon, otherwise it takes too long!
    public void littleDraw(GraphicsContext gc, int xPos, int yPos){

        if(dungeon.dungeonMatrix.get(yPos).get(xPos) instanceof Room){
            AssetDrawing.drawPicture(gc, xPos, yPos, AssetDrawing.roomImage);
        }
        else if(dungeon.dungeonMatrix.get(yPos).get(xPos) instanceof Wall){
            AssetDrawing.drawPicture(gc, xPos, yPos, AssetDrawing.wallImage);
        }
        else if(dungeon.dungeonMatrix.get(yPos).get(xPos) instanceof Entrance){
            AssetDrawing.drawPicture(gc, xPos, yPos, AssetDrawing.corridorImage);
            AssetDrawing.drawPictureSmaller(gc, xPos, yPos, AssetDrawing.doorImage, 0.2);
        }
        else if(dungeon.dungeonMatrix.get(yPos).get(xPos) instanceof Corridor){
            AssetDrawing.drawPicture(gc, xPos, yPos, AssetDrawing.corridorImage);
        }
        else {
            gc.setFill(Color.rgb(10, 106, 114));
            gc.fillRect(xPos*TILE_SIZE,yPos*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        }

        //TODO i make extra IF!
        if(dungeon.dungeonMatrix.get(yPos).get(xPos).getBasicUnit() instanceof Hero){
            AssetDrawing.drawPictureSmaller(gc, xPos, yPos, dungeon.dungeonMatrix.get(yPos).get(xPos).getBasicUnit().getBaseCharacterImage(), 0.2);
        }
        if(dungeon.dungeonMatrix.get(yPos).get(xPos).getBasicUnit() instanceof Goblin){
            AssetDrawing.drawPictureSmaller(gc, xPos, yPos, AssetDrawing.gnollAvatar, 0.2);
        }
        if(dungeon.dungeonMatrix.get(yPos).get(xPos).getBaseElement() instanceof Rocks){
            AssetDrawing.drawPictureSmaller(gc, xPos, yPos, AssetDrawing.rubbleImage, 0.2);
        }
    }




    //Buttons
    @FXML
    public void callRemoveCorner() {
        dungeon.removeCorner(15);
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }
    @FXML
    public void callCreateMaze() {
        System.out.println("Maze creation took " + dungeon.createMaze() + " seconds");
        System.out.println(dungeon.directionStack.size());
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }
    @FXML
    public void callAddWalls() {
        dungeon.fillEmptyTiles();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }
    @FXML
    public void callRemoveTeeth(){
        dungeon.removeDot();
        dungeon.removeTeeth();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }
    @FXML
    public void callGenerateDoors() {
        dungeon.generateDoors(1);
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }
    @FXML
    public void callDraw() {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }
    @FXML
    public void callUp() {
        BasicUnit player = dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit();


        if(dungeon.dungeonMatrix.get(dungeon.heroLocationY - 1).get(dungeon.heroLocationX) instanceof Corridor
                || dungeon.dungeonMatrix.get(dungeon.heroLocationY - 1).get(dungeon.heroLocationX) instanceof Room
                || dungeon.dungeonMatrix.get(dungeon.heroLocationY - 1).get(dungeon.heroLocationX) instanceof Entrance) {
            player.setNoOfMoves(player.getNoOfMoves() - 1);

            dungeon.dungeonMatrix.get(dungeon.heroLocationY - 1).get(dungeon.heroLocationX).setBasicUnit
                    (dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit());

            dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).setBasicUnit(null);
            dungeon.heroLocationY--;

            GraphicsContext gc = myCanvas.getGraphicsContext2D();
            littleDraw(gc, dungeon.heroLocationX, dungeon.heroLocationY);
            littleDraw(gc, dungeon.heroLocationX, dungeon.heroLocationY + 1);

            if(player.getNoOfMoves() < 1) {
                dungeon.notifyTurnCycle();//setState(player.getNoOfMoves());
                player.notifyPassive();
                player.setNoOfMoves(3);
            }
        }

    }
    @FXML
    public void callDown() {
        BasicUnit player = dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit();

        if(dungeon.dungeonMatrix.get(dungeon.heroLocationY + 1).get(dungeon.heroLocationX) instanceof Corridor
                || dungeon.dungeonMatrix.get(dungeon.heroLocationY + 1).get(dungeon.heroLocationX) instanceof Room
                || dungeon.dungeonMatrix.get(dungeon.heroLocationY + 1).get(dungeon.heroLocationX) instanceof Entrance) {
            player.setNoOfMoves(player.getNoOfMoves() - 1);


            dungeon.dungeonMatrix.get(dungeon.heroLocationY + 1).get(dungeon.heroLocationX).setBasicUnit
                    (dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit());

            dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).setBasicUnit(null);
            dungeon.heroLocationY++;


            GraphicsContext gc = myCanvas.getGraphicsContext2D();
            littleDraw(gc, dungeon.heroLocationX, dungeon.heroLocationY);
            littleDraw(gc, dungeon.heroLocationX, dungeon.heroLocationY - 1);

            if(player.getNoOfMoves() < 1) {
                //TODO these are observsers that notify of changes
                dungeon.notifyTurnCycle();
                player.notifyPassive();
                player.setNoOfMoves(3);
            }
        }

    }
    @FXML
    public void callRight() {
        BasicUnit player = dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit();


        if(dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX + 1) instanceof Corridor
                || dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX + 1) instanceof Room
                || dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX + 1) instanceof Entrance){
            player.setNoOfMoves(player.getNoOfMoves() - 1);

            dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX + 1).setBasicUnit
                    (dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit());

            dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).setBasicUnit(null);
            dungeon.heroLocationX++;

            GraphicsContext gc = myCanvas.getGraphicsContext2D();
            littleDraw(gc, dungeon.heroLocationX, dungeon.heroLocationY);
            littleDraw(gc, dungeon.heroLocationX - 1, dungeon.heroLocationY);

            if(player.getNoOfMoves() < 1) {
                dungeon.notifyTurnCycle();//dungeon.setState(player.getNoOfMoves());
                player.notifyPassive();
                player.setNoOfMoves(3);
            }
        }
    }
    @FXML
    public void callLeft() {
        BasicUnit player = dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit();


        if(dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX - 1) instanceof Corridor
            || dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX - 1) instanceof Room
                || dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX - 1) instanceof Entrance){

            player.setNoOfMoves(player.getNoOfMoves() - 1);
            dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX - 1).setBasicUnit
                    (dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit());

            dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).setBasicUnit(null);
            dungeon.heroLocationX--;

            GraphicsContext gc = myCanvas.getGraphicsContext2D();
            littleDraw(gc, dungeon.heroLocationX ,dungeon.heroLocationY);
            littleDraw(gc, dungeon.heroLocationX + 1,dungeon.heroLocationY);


            if(player.getNoOfMoves() < 1){
                dungeon.notifyTurnCycle();
                player.notifyPassive();
                player.setNoOfMoves(3);
            }
        }
    }
    @FXML
    public void callRegionInfo() {
        dungeon.removeSmallRegions();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
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
                WritableImage writableImage = new WritableImage(dungeon.dungeonWidth * TILE_SIZE, dungeon.dungeonHeight * TILE_SIZE - 1);
                myCanvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "PNG", file);
            } catch (IOException ex) {
                //  ¯\_(ツ)_/¯
            }
        }
    }

    public void callZoomIn( ) {
        TILE_SIZE = TILE_SIZE + 2;

//        if(TILE_SIZE > 64) TILE_SIZE = 64;


        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);

    }

    public void callZoomOut( ) {
        TILE_SIZE = TILE_SIZE - 2;

//        if(TILE_SIZE < 8) TILE_SIZE = 8;

        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        draw(gc);
    }


    //todo it  works, i love it and i want to make a movement based on it!
    public void callCheckMousePositionOnCanvas(MouseEvent mouseEvent) {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
//        draw(gc);

//        dungeon.dungeonMatrix.get((int)mouseEvent.getY()).get((int)mouseEvent.getX())

//        AssetDrawing.drawPictureSmaller(gc, (int)mouseEvent.getX()/TILE_SIZE, (int)mouseEvent.getY()/TILE_SIZE, AssetDrawing.dotImage, 0.35);


//        myCanvas.


    }

    public void callMove(KeyEvent keyEvent) {
        String chara = keyEvent.getText().toLowerCase();
        switch (chara){
            case "w": callUp();
                break;
            case "s": callDown();
                break;
            case "a": callLeft();
                break;
            case "d": callRight();
                break;
            case "x": callTest();
                break;

        }
    }


    //todo delete
    public void callTest() {

        //TODO I don't know if I want all of it to throw exceptions or if there is a better way
        Hero player = null;
        try {
            player = new Hero(22,12,9,2.4,1.2,1);
        } catch (SumErrorException e) {
            e.printStackTrace();
        }


        System.out.println(player.rightHand.getInfo());
    }

    public void callScroll(ScrollEvent scrollEvent) {

        if(scrollEvent.getDeltaY() < 0) callZoomOut();
            else callZoomIn();

    }
}

