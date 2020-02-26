
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;


public class Yahtzee extends Application {

    ImageView img1 = new ImageView();
    ImageView img2 = new ImageView();
    ImageView img3 = new ImageView();
    ImageView img4 = new ImageView();
    ImageView img5 = new ImageView();
    ImageView img6 = new ImageView();


    public ImageView getDice(int roll)
    {
        //Get initial image of dice
        if(roll == 1)
        {
            img1 = new ImageView("images/dice1.png");

            return img1;
        }
        if(roll == 2)
        {
            img2 = new ImageView("images/dice2.png");

            return img2;
        }
        if(roll == 3)
        {
            img3 = new ImageView("images/dice3.png");

            return img3;
        }
        if(roll == 4)
        {
            img4 = new ImageView("images/dice4.png");

            return img4;
        }
        if(roll == 5)
        {
            img5 = new ImageView("images/dice5.png");

            return img5;
        }
        if(roll == 6)
        {
            img6 = new ImageView("images/dice6.png");

            return img6;
        }
        return null;

    }

    int NUM = 5;
    List<Integer> dice = new ArrayList<>();
    List<ImageView> diceNum = new ArrayList<>();
    int rollNum = 0;
    List<ImageView> rolled = new ArrayList<>();

    //Setting up the layout of the Yahtzee GUI
    @Override
    public void start(Stage primaryStage) {
        //Title for Yahtzee
        int setDie = 0;
        while(setDie < 6)
        {
            diceNum.add(getDice(setDie+1));
            System.out.println(setDie+1);
            setDie++;
        }

       HBox titleBox = new HBox();
       titleBox.setAlignment(Pos.CENTER);
       titleBox.setPadding(new Insets(50));
       Text titleTxt = new Text("Let's Play Yahtzee");
       titleTxt.setFont(Font.font("Comic Sans MS", 40));
       titleBox.getChildren().add(titleTxt);

        //Heading for dice rolled
       HBox diceTitleBox = new HBox();
       diceTitleBox.setAlignment(Pos.CENTER);
       diceTitleBox.setPadding(new Insets(0,0,15,0));
       Text dTitleTxt = new Text();
       dTitleTxt.setText("Dice Rolled");
       dTitleTxt.setFont(Font.font("Comic Sans MS", 25));
       dTitleTxt.setUnderline(true);
       diceTitleBox.getChildren().add(dTitleTxt);

        //Display for dice that were rolled
       GridPane diceBox = new GridPane();
       diceBox.setAlignment(Pos.CENTER);
       diceBox.setPadding(new Insets(0,0,25,0));
       //diceBox.setSpacing(25);
       int count = 0;
       while(count < NUM)
       {
           dice.add(count,(int)( Math.random()*6+1));
           System.out.println(dice.get(count));
           count++;
       }

       System.out.println("From Dice Box");
       System.out.println("-------------");
       for(int i = 0; i < NUM; i++)
       {
           rolled.add(i, getDice(dice.get(i)));
           diceBox.add(rolled.get(i), i, 2);
           System.out.print("Dice #" + i + ") " + dice.get(i) + " ");
       }



        //Heading for dice held
       HBox holdTitleBox = new HBox();
       holdTitleBox.setAlignment(Pos.CENTER);
       holdTitleBox.setPadding(new Insets(0,0,15,0));
       Text hTitleTxt = new Text();
       hTitleTxt.setText("Dice Held");
       hTitleTxt.setFont(Font.font("Comic Sans MS", 25));
       hTitleTxt.setUnderline(true);
       holdTitleBox.getChildren().add(hTitleTxt);

        //Display for dice being held
        HBox holdBox = new HBox();
        diceBox.setAlignment(Pos.CENTER);
        holdBox.setPadding(new Insets(0,0,25,0));
        holdBox.setSpacing(25);
        holdBox.getChildren().addAll();

        //Display for buttons
       HBox btnBox = new HBox();
       btnBox.setAlignment(Pos.CENTER);
       btnBox.setSpacing(50);
       Button rollBtn = new Button("Roll");
       Button holdBtn = new Button("Hold");
       rollBtn.setDisable(true);
       rollBtn.setOnMouseClicked(e->{
           rollNum++;
           System.out.println("RollNum: " + rollNum);
           diceBox.getChildren().clear();
           System.out.println("\nFrom Roll Button");
           System.out.println("-------------");
           for(int numHeld = holdBox.getChildren().size(); numHeld < NUM; numHeld++)
           {
               dice.set(numHeld, (int)( Math.random()*6+1));
               rolled.set(numHeld, (getDice(dice.get(numHeld))));
               diceBox.add(rolled.get(numHeld), numHeld, 2);
               System.out.print("Dice #" + numHeld + ") " + dice.get(numHeld) + " ");
           }
           rollBtn.setDisable(true);
           holdBtn.setDisable(false);

       });

       holdBtn.setOnMouseClicked(e->{
           rollBtn.setDisable(false);
           holdBtn.setDisable(true);
           for(int i = 0; i < NUM; i++)
           {
               if(rolled.get(i).getOpacity() == .5)
               {
                   rolled.get(i).setOpacity(1);
                   diceBox.getChildren().remove(rolled.get(i));
                   holdBox.getChildren().add(rolled.get(i));
               }
           }
       });

       Button noHold = new Button("Don't Hold");
       noHold.setOnMouseClicked(e->{
           rollBtn.setDisable(false);
       });

       Button exitBtn = new Button("Exit");
       exitBtn.setOnMouseClicked(e->{
           HelloWorld hw = new HelloWorld();
           try {
               hw.start(primaryStage);
           } catch (Exception e1) {
               e1.printStackTrace();
           }
       });
       btnBox.getChildren().addAll(rollBtn, holdBtn, noHold, exitBtn);


        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(titleBox, diceTitleBox, diceBox, holdTitleBox, holdBox, btnBox);
        GridPane.setConstraints(titleBox,0,0);
        GridPane.setConstraints(diceTitleBox,0,1);
        GridPane.setConstraints(diceBox,0,2);
        GridPane.setConstraints(holdTitleBox,0,3);
        GridPane.setConstraints(holdBox,0,4);
        GridPane.setConstraints(btnBox,0,5);

        
        rolled.get(0).setOnMouseClicked(e->{

            holdBox.setAlignment(Pos.CENTER);
            if(rolled.get(0).getOpacity() == 1)
            {
                rolled.get(0).setOpacity(.5);
            }
            else
                rolled.get(0).setOpacity(1);


        });
        rolled.get(1).setOnMouseClicked(e->{

            holdBox.setAlignment(Pos.CENTER);
            if(rolled.get(1).getOpacity() == 1)
            {
                rolled.get(1).setOpacity(.5);
            }
            else
                rolled.get(1).setOpacity(1);


        });
        rolled.get(2).setOnMouseClicked(e->{

            holdBox.setAlignment(Pos.CENTER);
            if(rolled.get(2).getOpacity() == 1)
            {
                rolled.get(2).setOpacity(.5);
            }
            else
                rolled.get(2).setOpacity(1);


        });
        rolled.get(3).setOnMouseClicked(e->{

            holdBox.setAlignment(Pos.CENTER);
            if(rolled.get(3).getOpacity() == 1)
            {
                rolled.get(3).setOpacity(.5);
            }
            else
                rolled.get(3).setOpacity(1);


        });
        rolled.get(4).setOnMouseClicked(e->{

            holdBox.setAlignment(Pos.CENTER);
            if(rolled.get(4).getOpacity() == 1)
            {
                rolled.get(4).setOpacity(.5);
            }
            else
                rolled.get(4).setOpacity(1);


        });

       Scene scene = new Scene(pane, 600, 600);
       primaryStage.setScene(scene);
       primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}