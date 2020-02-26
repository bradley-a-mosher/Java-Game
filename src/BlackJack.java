import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackJack extends Application{

    private int NUM_CARDS = 52;
    private int COM_CARDS = 52;
    int count = 0;
    Image[] getDeck = new Image[NUM_CARDS];
    Image[] comDeck = new Image[COM_CARDS];

    private int spade = 0;
    private int heart = 0;
    private int club = 0;
    private int diamond = 0;

    int hit = 2;
    int pScore = 0;
    int cScore = 0;
    int rValue1 = getValue();
    int rValue2 = getValue();
    int cValue1 = getValue();
    List<Integer> usedValue = new ArrayList<>();

    ImageView img1 = new ImageView();
    ImageView img2 = new ImageView();
    ImageView cImg1 = new ImageView("images/blue_back.jpg");
    ImageView cImg2 = new ImageView();
    Text setScore = new Text();
    Text comSetScore = new Text();
    Text loser = new Text();
    Text winner = new Text();



    public void start(Stage primaryStage) throws Exception{
        startGame();

        //Set Header
        HBox header = new HBox();
        header.setPadding(new Insets(25, 0, 25, 0));
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER);
        Text headerTxt = new Text();
        headerTxt.setText("BlackJack");
        headerTxt.setFont(Font.font("Times New Roman", 50));
        headerTxt.setFill(Color.NAVY);
        ImageView headerImg = new ImageView("images/back_cards-07.jpg");
        headerImg.setFitHeight(75);
        headerImg.setFitWidth(100);
        header.getChildren().addAll(headerTxt, headerImg);

        GridPane body = new GridPane();
        body.setPadding(new Insets(15));
        body.setHgap(15);
        body.setVgap(15);
        cImg1.setFitHeight(100);
        cImg1.setFitWidth(75);
        cImg2.setFitHeight(100);
        cImg2.setFitWidth(75);
        Text playerScore = new Text("Player Score: ");
        Text comScore = new Text("Computer Score:");
        body.setAlignment(Pos.CENTER);

        body.getChildren().addAll(img1, img2, playerScore, setScore, cImg1, cImg2, comScore, comSetScore);
        GridPane.setConstraints(img1, 0, 0);
        GridPane.setConstraints(img2, 1, 0);
        GridPane.setConstraints(playerScore, 0, 1);
        GridPane.setConstraints(setScore, 1, 1);
        GridPane.setConstraints(cImg1, 0, 2);
        GridPane.setConstraints(cImg2, 1, 2);
        GridPane.setConstraints(comScore, 0, 3);
        GridPane.setConstraints(comSetScore, 1, 3);


        HBox footer = new HBox();
        footer.setSpacing(10);
        footer.setPadding(new Insets(25, 0, 25, 0));
        footer.setAlignment(Pos.CENTER);
        Button hitBtn = new Button("Hit me");
        Button standBtn = new Button("Stand");
        Button paBtn = new Button("Play Again?");
        Button exitBtn = new Button("Exit");
        footer.getChildren().addAll(hitBtn, standBtn, paBtn, exitBtn);

        if(pScore >= 21)
        {
            hitBtn.setDisable(true);
        }

        BorderPane bp = new BorderPane();
        bp.setTop(header);
        bp.setCenter(body);
        bp.setBottom(footer);

        hitBtn.setOnMouseClicked(e->{

            ImageView newImage = new ImageView();
            int random = getValue();
            usedValue.add(random);
            newImage.setImage(getDeck[random]);
            newImage.setFitHeight(100);
            newImage.setFitWidth(75);
            body.getChildren().addAll(newImage);
            random = getScore(random);
            pScore = checkAndSet(pScore, random);
            body.getChildren().remove(setScore);
            setScore.setText("" + pScore);
            body.add(setScore, 1, 1);
            GridPane.setConstraints(newImage, hit, 0);
            hit++;

            if(pScore > 21)
            {
                hitBtn.setDisable(true);
                standBtn.setDisable(true);
                loser = new Text("You lose!");
                int cValue2 = getValue();
                ImageView img = getCard(cValue2);
                img.setFitHeight(100);
                img.setFitWidth(75);
                body.getChildren().remove(cImg1);
                body.add(img, 0, 2);
                int temp = getScore(cValue2);
                cScore = checkAndSet(cScore, temp);
                comSetScore.setText("" + cScore);
                body.getChildren().remove(comSetScore);
                body.add(comSetScore, 1, 3);
                body.add(loser, 1, 4);
                writeData(pScore, false);
            }


        });

        standBtn.setOnAction(e->{
            hitBtn.setDisable(true);
            standBtn.setDisable(true);

            int cValue2 = getValue();
            cImg1 = getCard(cValue2);

            body.getChildren().remove(cImg1);
            body.add(cImg1, 0, 2);
            int temp = getScore(cValue2);
            cScore = checkAndSet(cScore, temp);
            comSetScore.setText("" + cScore);
            boolean pVictory = true;
            pVictory = getWinner(cScore, pScore);
            body.getChildren().remove(comSetScore);
            body.add(comSetScore, 1, 3);
            if(!pVictory)
            {
                loser = new Text("You lose!");
                body.add(loser, 1, 4);
            }
            else
            {
                winner = new Text("You won!!!");
                body.add(winner, 1, 4);
            }
            writeData(pScore, pVictory);

        });

        paBtn.setOnAction(e->{

           hitBtn.setDisable(false);
           standBtn.setDisable(false);
           rValue1 = getValue();
           rValue2 = getValue();
           cValue1 = getValue();
           int pTemp1 = getScore(rValue1);
           int pTemp2 = getScore(rValue2);
           pScore = checkAndSet(pTemp1, pTemp2);

           img1 = getCard(rValue1);
           img2 = getCard(rValue2);
           playerScore.setText("Player Score:");
           setScore.setText("" + pScore);
           cImg1 = new ImageView("images/blue_back.jpg");
           cImg1.setFitHeight(100);
           cImg1.setFitWidth(75);
           cImg2 = getCard(cValue1);
           comScore.setText("Computer Score:");
           cScore = getScore(cValue1);
           comSetScore.setText("" + cScore);
           body.getChildren().clear();


           body.add(img1,0,0);
           body.add(img2,1,0);
           body.add(playerScore, 0, 1);
           body.add(setScore, 1,1);
           body.add(cImg1, 0, 2 );
           body.add(cImg2, 1, 2);
           body.add(comScore, 0, 3);
           body.add(comSetScore, 1, 3);
        });

        exitBtn.setOnAction(e->{
           HelloWorld hw = new HelloWorld();
            try {
                hw.start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(bp, 600, 600);
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public Image[] setDeck(){

        while(count < getDeck.length)
        {
            if(count < 13 && spade < 13)
            {
                getDeck[count] = setCards(getDeck, spade, heart, club, diamond, count);
                spade++;
            }
            else if(count < 26 && heart < 13)
            {
                getDeck[count] = setCards(getDeck, spade, heart, club, diamond, count);
                heart++;
            }
            else if(count < 39 && club < 13)
            {
                getDeck[count] = setCards(getDeck, spade, heart, club, diamond, count);
                club++;
            }
            else
            {
                getDeck[count] = setCards(getDeck, spade, heart, club, diamond, count);
                diamond++;
            }

            count++;
        }

        return getDeck;
    }

    void startGame()
    {
        getDeck = setDeck();
        System.out.println(getDeck.length + "\n" + comDeck.length);

        img1 = getCard(rValue1);
        img2 = getCard(rValue2);
        cImg2 = getCard(cValue1);

        int temp1 = getScore(rValue1);
        usedValue.add(rValue1);
        int temp2 = getScore(rValue2);
        usedValue.add(rValue2);
        int tempC = getScore(cValue1);
        cScore = tempC;
        pScore = checkAndSet(temp1, temp2);
        System.out.println(tempC);
        comSetScore.setText("" + cScore);
        setScore.setText("" + pScore);
    }


    public Image setCards(Image[] getDeck,int spade,int heart,int club,int diamond, int count)
    {
        if(count < 13 && spade < 13)
        {
            getDeck[count] = new Image("images/" + (spade+1) + "S.jpg");

            return getDeck[count];
        }
        else if(count >= 13 && count < 26 && heart < 13)
        {
            getDeck[count] = new Image("images/" + (heart+1) + "H.jpg");

            return getDeck[count];
        }
        else if(count >= 26 && count < 39 && club < 13)
        {
            getDeck[count] = new Image("images/" + (club+1) + "C.jpg");

            return getDeck[count];
        }
        else
        {
            getDeck[count] = new Image("images/" + (diamond+1) + "D.jpg");

            return getDeck[count];
        }

    }


    private int getValue()
    {
        int random = ThreadLocalRandom.current().nextInt(0, 52);
        return random;
    }

    private int getScore(int random)
    {
        if(random > 0 && random < 10)
        {
            return random+1;
        }
        else if(random > 13 && random < 23)
        {
            return random - 12;
        }
        else if(random > 26 && random < 36)
        {
            return random - 25;
        }
        else if(random > 39 && random < 49)
        {
            return random - 38;
        }
        else if((random >= 10 && random <=12) || (random >= 23 && random <= 25)||(random >= 36 && random <= 38) || (random >= 49 && random <= 51) )
        {
            return 10;
        }
        else{
            return 11;
        }
    }

    private ImageView getCard(int value)
    {
        ImageView image = new ImageView();
        image.setImage(getDeck[value]);
        image.setFitHeight(100);
        image.setFitWidth(75);
        return image;
    }

    private boolean getWinner(int cScore, int pScore)
    {
        if(pScore > cScore)
        {
            return true;
        }
        else
            return false;
    }

    private int checkAndSet(int t1, int t2)
    {
        int temp = t1 + t2;
        if(temp > 21 && t1 == 11)
        {
            t1 = 1;
            temp = t1 + t2;
            return temp;
        }else if(temp > 21 && t2 == 11)
        {
            t2 = 1;
            temp = t1 + t2;
            return temp;
        }
        else
            return temp;
    }

    private static void writeData(int pScore, boolean pVictory)
    {
        File gameStats =new File("playerStats.txt");
        try {
            FileWriter writer = new FileWriter(gameStats, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            PrintWriter pw = new PrintWriter(buffer);
            pw.println("Player Score: " + pScore);
            if(pVictory == true)
            {
                pw.println("Player won round");
            }
            else
                pw.println("Player lost round");
            pw.println();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        launch(args);
    }

}