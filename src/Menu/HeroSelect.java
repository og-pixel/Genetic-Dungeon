package Menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HeroSelect extends Application {

    @FXML
    private ImageView image;

    @FXML
    private Text heroDescriptionText;

    @FXML
    private Label strengthLabel,agilityLabel,intelligenceLabel,nameLabel;


    //TODO THIS ORDER HAS TO BE KEPT or just delete it, i dunno
    final int WARRIOR = 0;
    final int HUNTER = 1;
    final int MAGE = 2;

    private int currentSelect = WARRIOR;

    //Info database
    //TODO I don't know if this is the best way to store data
    private String heroName[] = {"Warrior", "Hunter", "Mage"};


    private String heroDescription[] = {"Noble warrior, excellent in melee combat and able to take a lot of damage",
            "Swift and agile hero, able to fight in distance with bow or stay in shadows and strike it enemies from behind",
            "Powerful mages can summon powerful spells to aid them in their quest, wearing light clothes and no real weapon" +
                    " makes him fragile but powerful."};

    private String heroStartingStrength[] = {"25", "15", "13"};
    private String heroStartingAgility[] = {"13", "22", "8"};
    private String heroStartingIntelligence[] = {"9", "13", "27"};

    private String heroStrengthGain[] = {"2.3", "1.5", "1"};
    private String heroAgilityGain[] = {"1.5", "3", "1.7"};
    private String heroIntelligenceGain[] = {"1.2", "1.9", "3"};



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HeroSelect.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Dungeon_GA!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    void initialize(){
        displayHeroStats();
    }


    public void displayHeroStats() {
        if(currentSelect == 0)image.setImage(AssetDrawing.warriorImage);
        else if (currentSelect == 1)image.setImage(AssetDrawing.hunterImage);
        else if(currentSelect == 2)image.setImage(AssetDrawing.mageImage);

        nameLabel.setText(heroName[currentSelect]);
        heroDescriptionText.setText(heroDescription[currentSelect]);
        strengthLabel.setText(heroStartingStrength[currentSelect] + " + " + heroStrengthGain[currentSelect] + " Per Level");
        agilityLabel.setText(heroStartingAgility[currentSelect] + " + " + heroAgilityGain[currentSelect] + " Per Level");
        intelligenceLabel.setText(heroStartingIntelligence[currentSelect] + " + " +heroIntelligenceGain[currentSelect] + " Per Level");
    }


    public void callNextHero(ActionEvent actionEvent) {
        currentSelect++;
        if(currentSelect > 2)currentSelect = 0;
        displayHeroStats();
    }

    public void callPreviousHero(ActionEvent actionEvent) {
        currentSelect--;
        if(currentSelect < 0)currentSelect = 2;
        displayHeroStats();
    }
}
