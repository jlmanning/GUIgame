/*
 * This project builds a GUI of a word-scramble game, retrieving words from a .txt
 * file and interacting with the user to unscramble all 10 words to complete the 
 * game.
 */
package cs1181.project2.manning;

import javafx.scene.layout.BackgroundImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author jessicamanning
 */
public class CS1181Project2Manning extends Application {

    private int count = 0;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        // scans words from text file
        File file = new File("wordList.txt");
        Scanner in = new Scanner(file);

        // arraylist in which words will be added to
        ArrayList<String> wordList = new ArrayList<>();

        // loop to iterate until all words have been added
        while (in.hasNextLine()) {
            wordList.add(in.nextLine());
        }

        String word = "";

        // creates an arraylist for words that are used during game
        ArrayList<String> usedWords = new ArrayList<>();

        // boolean to flag whether a word exists in usedWord arraylist
        boolean inList = false;

        
        while (true) {
            int rnd = new Random().nextInt(wordList.size());

            word = wordList.get(rnd);
            for (int i = 0; i < usedWords.size(); i++) {
                if (usedWords.get(i).equals(word)) {
                    inList = true;
                    break;
                }
            }
            if (!inList) {
                usedWords.add(word);
                break;
            }
        }

        String scramble = word;
        char[] chars = scramble.toCharArray();
        Arrays.sort(chars);
        String randomize = new String(chars);

        Label scrambledWord = new Label(randomize);

        // move counter
        HBox counter = new HBox();
        Label countText = new Label("Moves: " + count);

        // if user clicks enter/hint, there will be a graphic and text displayed in response
        HBox interact = new HBox();
        Label gameResponse = new Label();
        gameResponse.setFont(new Font("Arial Bold", 15));
        Label gameGraphic = new Label();
        interact.getChildren().addAll(gameResponse, gameGraphic);

        // image for correct guesses
        Image correct = new Image("file:happy.png", 25, 25, false, true);
        ImageView happy = new ImageView(correct);

        // image for incorrect guesses
        Image incorrect = new Image("file:thinking.png", 25, 25, false, true);
        ImageView thinking = new ImageView(incorrect);

        // image for hints
        Image lightbulb = new Image("file:lightbulb.jpg", 25, 25, false, true);
        ImageView hintGraphic = new ImageView(lightbulb);

        // got help from http://tutorials.jenkov.com/javafx/label.html#:~:text=Set%20Label%20Font,set.%22)%3B%20label.
        scrambledWord.setFont(new Font("Arial Bold", 20));

        TextField userGuess = new TextField();
        userGuess.setMaxWidth(145);

        userGuess.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox endGame = new VBox();
        Image congrats = new Image("file:congratulations.png");
        ImageView winner = new ImageView(congrats);

        Button enter = new Button();
        enter.setText("Enter");
        enter.setFont(new Font("Arial Bold", 12));
        // got help w/ border from https://stackoverflow.com/questions/27712213/how-do-i-make-a-simple-solid-border-around-a-flowpane-in-javafx
        enter.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        
        enter.setOnAction((ActionEvent e) -> {
            count++;
            // gets user input after clicking enter
            String guess = userGuess.getText();
            // every attempt increases the count
            countText.setText("Moves: " + count);
            // compares to word that appears scrambled
            if (guess.equals(scramble)) {
                // responds with new text and graphic to correct guess
                gameResponse.setText("Nice work!");
                gameGraphic.setGraphic(happy);
                for (int i = 0; i < wordList.size(); i++) {
                    
                }
                scrambledWord.setText(randomize);
                
                

            }

        });
        char[] hintchars = word.toCharArray();
        Button hint = new Button();
        hint.setText("Hint");
        hint.setFont(new Font("Arial Bold", 12));
        hint.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        hint.setOnAction((ActionEvent giveHint) -> {
            count++;
            gameResponse.setText("Here's a hint!");
            gameGraphic.setGraphic(hintGraphic);
            String characters = "";
            for (int i = 0; i <= count; i++) {
                // gives hint in the text field
                characters += hintchars[i];
                userGuess.setText(characters);
            
            // every hint increases the count
            countText.setText("Moves: " + count);}

        });

        // creates button to leave the game, button function defined after primaryStage set
        Button quit = new Button();
        quit.setText("Quit");
        quit.setFont(new Font("Arial Bold", 12));
        quit.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox root = new VBox(15);

        counter.getChildren().addAll(countText);

        VBox wordFields = new VBox();

        wordFields.getChildren().addAll(scrambledWord, userGuess);

        HBox buttons = new HBox(6);
        buttons.getChildren().addAll(enter, hint, quit);

        Image background = new Image("file:background.jpg", 350, 250, false, true);

        // got help from https://csharp.developreference.com/article/24910809/JavaFX+How+to+set+scene+background+image
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        root.setBackground(new Background(backgroundImage));

        root.setPadding(new Insets(70, 20, 20, 100));
        root.getChildren().addAll(wordFields, buttons, counter, interact);

        Scene scene = new Scene(root, 350, 250);

        primaryStage.setTitle("Word Scramble");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // gives use to button to quit
        quit.setOnAction((ActionEvent quitGame) -> {
            primaryStage.close();
        });

        //shows rules before game begins, user must click "OK" to start
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Word Scramble Rules");
        alert.setHeaderText(null);
        alert.setContentText("Unscramble all 10 words to win! You may use a hint, \n"
                + "but that will add to your 'Moves' count. How many \n"
                + "moves can you finish in? \n\n"
                + "Good Luck!");
        alert.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
