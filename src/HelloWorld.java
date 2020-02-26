import com.sun.javafx.stage.StageHelper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.awt.*;

import static javafx.application.Application.launch;

public class HelloWorld extends Application {
    public void start(Stage primaryStage) throws Exception{
        //Title for BlackJack
        HBox bjTitle = new HBox();
        bjTitle.setAlignment(Pos.CENTER);
        bjTitle.setPadding(new Insets(0, 0, 20, 0));
        Text bjTxt = new Text("BlackJack");
        bjTxt.setFont(Font.font("Comic Sans MS", 40));
        bjTxt.setStroke(Color.DARKBLUE);
        bjTxt.setFill(Color.ORANGERED);
        bjTitle.getChildren().add(bjTxt);

        //Button and Image for BlackJack
        HBox bjBox = new HBox();
        bjBox.setSpacing(15);
        bjBox.setAlignment(Pos.CENTER);
        ImageView bjImg = new ImageView("images/back_cards-07.jpg");
        bjImg.setFitWidth(100);
        bjImg.setFitHeight(75);
        Button playBJ = new Button("Play");
        playBJ.setPrefHeight(50);
        playBJ.setPrefWidth(75);
        playBJ.setFont(Font.font("Comic Sans MS", 15));
        bjBox.getChildren().addAll(bjImg, playBJ);
        playBJ.setOnMouseClicked(e->{
            BlackJack bj = new BlackJack();
            try {
                bj.start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //Title for Hangman
        HBox hmTitle = new HBox();
        hmTitle.setAlignment(Pos.CENTER);
        hmTitle.setPadding(new Insets(0, 0, 20, 0));
        Text hmTxt = new Text("Hangman");
        hmTxt.setFont(Font.font("Comic Sans MS", 40));
        hmTxt.setStroke(Color.DARKBLUE);
        hmTxt.setFill(Color.ORANGERED);
        hmTitle.getChildren().add(hmTxt);

        //Button and Image for Hangman
        HBox hmBox = new HBox();
        hmBox.setSpacing(15);
        hmBox.setAlignment(Pos.CENTER);
        ImageView hmImg = new ImageView("images/hangman1.jpg");
        hmImg.setFitWidth(100);
        hmImg.setFitHeight(75);
        Button playHM = new Button("Play");
        playHM.setPrefHeight(50);
        playHM.setPrefWidth(75);
        playHM.setFont(Font.font("Comic Sans MS", 15));
        hmBox.getChildren().addAll(hmImg, playHM);
        playHM.setOnMouseClicked(e->{
            Hangman hm = new Hangman();
            try {
                hm.start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //Title for Yahtzee
        HBox yTitle = new HBox();
        yTitle.setAlignment(Pos.CENTER);
        yTitle.setPadding(new Insets(0, 0, 20, 0));
        Text yTxt = new Text("Yahtzee");
        yTxt.setFont(Font.font("Comic Sans MS", 40));
        yTxt.setStroke(Color.DARKBLUE);
        yTxt.setFill(Color.ORANGERED);
        yTitle.getChildren().add(yTxt);

        //Button and Image for Yahtzee
        HBox yBox = new HBox();
        yBox.setSpacing(15);
        yBox.setAlignment(Pos.CENTER);
        ImageView yImg = new ImageView("images/yDice.png");
        yImg.setFitWidth(100);
        yImg.setFitHeight(75);
        Button playY = new Button("Play");
        playY.setPrefHeight(50);
        playY.setPrefWidth(75);
        playY.setFont(Font.font("Comic Sans MS", 15));
        yBox.getChildren().addAll(yImg, playY);
        playY.setOnMouseClicked(e->{
            Yahtzee y = new Yahtzee();
            try {
                y.start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        HBox eBox = new HBox();
        eBox.setAlignment(Pos.CENTER);
        eBox.setPadding(new Insets(25,0,0,0));
        Button exitGames = new Button();
        exitGames.setText("Exit");
        exitGames.setPrefWidth(75);
        exitGames.setPrefHeight(50);
        exitGames.setOnMouseClicked(e->{
            primaryStage.close();
        });
        eBox.getChildren().add(exitGames);


        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.getChildren().addAll(bjTitle, bjBox, hmTitle, hmBox, yTitle, yBox, eBox);
        GridPane.setConstraints(bjTitle, 0, 0);
        GridPane.setConstraints(bjBox,0, 1);
        GridPane.setConstraints(hmTitle, 0, 2);
        GridPane.setConstraints(hmBox, 0, 3);
        GridPane.setConstraints(yTitle, 0,4);
        GridPane.setConstraints(yBox,0,5);
        GridPane.setConstraints(eBox, 0, 6);

        Scene scene = new Scene(gp, 600,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
