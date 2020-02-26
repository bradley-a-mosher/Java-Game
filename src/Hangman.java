
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Hangman extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        final int[] wrongGuess = {0};
        final int[] wins = {0};
        final int[] losses = {0};

        //Brad wrote most of the GUI. Cory added the name textfield and play again button.
        GridPane header = new GridPane();
        header.setAlignment(Pos.CENTER);
        Label label1 = new Label();
        label1.setText("Let's Play Hangman");
        Label label3 = new Label("Name:");
        TextField nameField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label3, nameField);
        hb.setSpacing(10);
        header.add(label1, 0,0);
        header.add(hb, 0,1);
        File wordfile = load_words();
        File statfile = statistics();
        ArrayList<String> wordlist = word_list(wordfile);
        StringBuilder hiddenword = new StringBuilder(hidden_word(wordlist));

        System.out.println(wordlist.size());
        StringBuilder guess = new StringBuilder();
//        Scanner input = new Scanner(System.in);
        TextField text = new TextField();
        TextField namefield = new TextField();
        text.setPrefColumnCount(1);
        Button btn = new Button();
        Button playagain = new Button("Play again?");
        Button exit = new Button("Exit");
        btn.setText("Guess");
        Text hidden = new Text("Hidden Word: ");
        Text wrong = new Text("Guesses left: ");
        Text wrongLetter = new Text();

        GridPane body = new GridPane();
        body.setAlignment(Pos.CENTER);
        ImageView hangmanImage = new ImageView("images/hangman1.jpg");
        body.getChildren().add(hangmanImage);

        //Set Footer
        GridPane footer = new GridPane();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(15));
        footer.setHgap(5);
        footer.setVgap(5);
        Label label2 = new Label();
        /*(for(int i = 0; i < hiddenword.length(); i++) {
            guess.append("*");
        }*/
        final boolean[] gameOver = {false};
        label2.setText("Hi");//guess.toString());
        label2.setFocusTraversable(false);
        footer.getChildren().addAll(namefield, text, hidden, btn, playagain, exit, wrong, wrongLetter);
        GridPane.setConstraints(text, 0, 0);
        GridPane.setConstraints(namefield, 0, 0);
        GridPane.setConstraints(btn, 1, 0);
        GridPane.setConstraints(playagain, 2, 0);
        GridPane.setConstraints(exit, 3, 0);
        GridPane.setConstraints(hidden, 0, 1);
        GridPane.setConstraints(label2, 1, 1);
        GridPane.setConstraints(wrong, 0, 2);
        GridPane.setConstraints(wrongLetter, 1, 2);
        System.out.println(hiddenword);

        /*
        Brad wrote most of the btn.setOnAction method. Cory added the if condition checking, wrong letter counter
        and the if statements to check if the game is over, whether it was a win, or a loss, and error handling for empty input.
        */
        btn.setOnAction(e -> {
            String s = text.getText();
            if(s.isEmpty()){
                return;
            }
            else {
                char letter = s.charAt(0);
                if (guess.toString().contains(Character.toString(letter))) {
                    label3.setText(letter + " already exists");
                    footer.getChildren().remove(label3);
                    footer.getChildren().add(label3);
                    wrongLetter.setText(Integer.toString((hiddenword.length() + 2) - wrongGuess[0]));
                    footer.getChildren().remove(wrongLetter);
                    footer.getChildren().add(wrongLetter);
                    wrongGuess[0]++;

                } else if (!hiddenword.toString().contains(Character.toString(letter))) {
                    wrongLetter.setText(Integer.toString((hiddenword.length() + 2) - wrongGuess[0]));
                    footer.getChildren().remove(wrongLetter);
                    footer.getChildren().add(wrongLetter);
                    wrongGuess[0]++;
                } else {
                    int letterIndex = hiddenword.indexOf(Character.toString(letter));

                    while (letterIndex >= 0) {
                        guess.setCharAt(letterIndex, letter);
                        label2.setText(guess.toString());
                        footer.getChildren().remove(label2);
                        footer.getChildren().add(label2);
                        letterIndex = hiddenword.indexOf(Character.toString(letter), letterIndex + 1);
                    }
                }
                text.clear();
                if (!guess.toString().contains("*")) {
                    System.out.println("win");
                    wins[0]++;
                    label1.setText("You won! Click play again");
                    btn.setDisable(true);

                }
                if (wrongGuess[0] >= hiddenword.length() + 3) {
                    losses[0]++;
                    label1.setText("game over, click play again");
                    btn.setDisable(true);

                }
                //Notify user they have one more guess
                if (wrongGuess[0] == hiddenword.length() + 2) {
                    label1.setText("Last Guess!!!");
                }
            }

        });
        //Cory added this to allow the user to restart the game using the play again button and gives a
        //pop up to notify the user that they have played all the words
        playagain.setOnAction(ev -> {
            new_game(label2, label3, wrongGuess, hiddenword, guess, footer, wordlist, label1, btn);
            if(wordlist.isEmpty()){
                label1.setText("Out of words. Thanks for playing!!!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Out of Words!");
                alert.setHeaderText("No More Words...");
                alert.setContentText("You guessed all the words! Good Bye!");
                alert.showAndWait();


                HelloWorld hw = new HelloWorld();
                try {
                    hw.start(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });
        //Cory added this to allow the user ot exit, but it updates the file with user stats
        exit.setOnAction(ev -> {
            try {
                HelloWorld hw = new HelloWorld();
                update(statfile, nameField.getText(), wins, losses);
                hw.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Brad wrote these for GUI display
        BorderPane bp = new BorderPane();
        bp.setTop(header);
        bp.setCenter(body);
        bp.setBottom(footer);

        primaryStage.setTitle("Hangman");
        primaryStage.setScene(new Scene(bp, 600, 600));
        primaryStage.show();



    }
    //Cory: this creates a file if one does not exist and loads it with the words from the assignment.
    private static File load_words() throws Exception{
        File f = new File("words.txt");
        if(!f.exists()){
            PrintWriter writer = new PrintWriter(f);
            String[] init_values = {"wizard", "zombie", "dwarf", "dragon", "elven", "chimera", "hobgoblin",
                    "weretiger", "halfling", "selkie", "skeleton"};
            for (String s : init_values) {
                writer.println(s);
            }
            writer.close();
        }
        return f;

    }
    //Cory: this creates a stat file if one does not exist.
    private static File statistics(){
        return new File("stats.txt");
    }
    //Cory: this updates the stat file with the user's stats
    private static void update(File f, String name, final int[] wins, final int losses[])throws Exception{
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
        if(name.isEmpty()){
            name = "Bob";
        }
        out.println(String.format("%-15s %-2s %-2s", name, Integer.toString(wins[0]), Integer.toString(losses[0])));
        out.close();

    }
    //Cory: This creates a word list from the file
    private static ArrayList<String> word_list(File file) throws Exception{
        Scanner scan = new Scanner(file);
        ArrayList<String> words = new ArrayList<>();
        int count = 0;
        while (scan.hasNext()) {
            words.add(count, scan.next());
            count++;
        }
        scan.close();
        return words;
    }
    //Cory: this assigns a new random hidden word and ensures that the random number is in range.
    private static String hidden_word(ArrayList<String> list){
        if(list.size() == 1){
            return list.get(0);
        }
        else{
            return list.get(new Random().nextInt(list.size()-1));
        }

    }
    //Cory: This initializes a new game with all applicable fields
    private void new_game(Label label2, Label label3, int[] wrongGuess, StringBuilder hiddenword, StringBuilder guess, GridPane footer, ArrayList wordlist, Label label1, Button btn){
        wrongGuess[0] = 0;
        wordlist.remove(hiddenword.toString());
        hiddenword.setLength(0);
        hiddenword.append(hidden_word(wordlist));
        guess.setLength(0);
        for(int i = 0; i < hiddenword.length(); i++) {
            guess.append("*");
        }
        label2.setText(guess.toString());
        footer.getChildren().remove(label2);
        footer.getChildren().add(label2);
        label3.setText("");
        wrongGuess[0] = 0;
        label1.setText("Let's play hangman!");
        btn.setDisable(false);
    }
}
