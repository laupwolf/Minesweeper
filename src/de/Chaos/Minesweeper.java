package de.Chaos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Laupwolf on 4/1/15.
 */
public class Minesweeper extends Application {
    public static MineButton[][] button = new MineButton[10][10];
    int bombsNumber;
    Text bombsLeftText;
    StackPane root;
    double windowsizeY;
    double windowsizeX;
    Button again;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double buttonsize = 30;
        windowsizeY = 360;
        windowsizeX = 310;
        bombsNumber = 10;
        root = new StackPane();
        boolean[][] hasBomb = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                hasBomb[i][j] = false;
            }
        }
        //Set some Bombs
        int x, y;
        int counter = 0;
        //setting up the Bombs
        while (counter < bombsNumber) {
            x = (int)(10*Math.random());
            y = (int)(10*Math.random());
            if (!hasBomb[x][y]) {
                hasBomb[x][y] = true;
                counter++;
            }
        }

        //Calculate NumberOfBombs
        int numberOfBombs[][] = calcNumberOfBombs(hasBomb);

        bombsLeftText = new Text("Bombs left: " + bombsNumber);
        bombsLeftText.setTranslateX(-windowsizeX / 2 + 15 + 40);
        bombsLeftText.setTranslateY(-windowsizeY / 2 + 15);
        //Falsch!
        again = new Button();
        again.setText("Ja");
        again.setVisible(false);
        root.getChildren().add(again);


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <10; j++) {
                button[i][j] = new MineButton(hasBomb[i][j], i, j, numberOfBombs[i][j]);
                button[i][j].setText(" ");
                button[i][j].setMinSize(buttonsize, buttonsize);
                button[i][j].setPrefSize(buttonsize, buttonsize);
                button[i][j].setMaxSize(buttonsize, buttonsize);
                button[i][j].setTranslateX(i * buttonsize - windowsizeX / 2 + buttonsize / 2 + 5);
                button[i][j].setTranslateY(j * buttonsize - windowsizeY / 2 + windowsizeY-windowsizeX + buttonsize / 2 + 5);
                button[i][j].setOnMouseClicked(event -> {
                    MineButton button1 = (MineButton) (event.getSource());
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (button1.bomb) {
                            button1.setText("X");
                            //lose game
                            openAllBombs();
                        } else {
                            if (!button1.open) checkSurroundingBombs(button1);
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        if (button1.getText().equals("!")) {
                            button1.setText(" ");
                            button1.setStyle("");
                            changeBombNumber("+");
                        } else if (button1.getText().equals(" ")){
                            button1.setText("!");
                            button1.setStyle("-fx-background-color: #00FF00");
                            changeBombNumber("-");
                        }
                    }

                });

                root.getChildren().add(button[i][j]);
            }
        }


        root.getChildren().add(bombsLeftText);



        Scene scene = new Scene(root, windowsizeX, windowsizeY);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAllBombs() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (button[i][j].bomb) {
                    button[i][j].setText("X");
                    button[i][j].setStyle("-fx-background-color: #FF0000; -fx-background-radius: 20");
                }
            }
        }
        bombsLeftText.setText("Nochmal?");
        again.setVisible(true);
    }

    private void checkSurroundingBombs(MineButton tButton) {
        int x = tButton.x;
        int y = tButton.y;
        for (int i = x-1; i < x+2; i++) {
            for (int j = y-1; j < y+2; j++) {
                if (i >= 0 && i <= 9 && j >= 0 && j <= 9) {
                    if (button[i][j].numberOfBombs < 9 && !button[i][j].open) {
                        button[i][j].setText("" + button[i][j].numberOfBombs);
                        button[i][j].open = true;
                        if (button[i][j].numberOfBombs == 0) {
                            checkSurroundingBombs(button[i][j]);
                        }
                    }
                }
            }
        }
    }

    private int[][] calcNumberOfBombs(boolean hasBomb[][]) {
        int numberOfBombs[][] = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                numberOfBombs[i][j] = 0;
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //all except borders
                if (hasBomb[i][j]) {
                    numberOfBombs[i][j] = 9;
                } else {
                    if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
                        for (int k = i - 1; k < i + 2; k++) {
                            for (int l = j - 1; l < j + 2; l++) {
                                if (hasBomb[k][l]) numberOfBombs[i][j]++;
                            }
                        }
                    } else if (i == 0) {
                        for (int k = 0; k < 2; k++) {
                            //edges
                            if (j==0) {
                                for (int l = 0; l < 2; l++) {
                                    if (hasBomb[k][l]) numberOfBombs[i][j]++;
                                }
                            } else if (j==9) {
                                for (int l = 8; l< 10; l++) {
                                    if (hasBomb[k][l]) numberOfBombs[i][j]++;
                                }
                                //Borders
                            } else {
                                for (int l = j-1; l< j+2; l++) {
                                    if (hasBomb[k][l]) numberOfBombs[i][j]++;
                                }
                            }
                        }
                    } else if (i==9) {
                        for (int k = 8; k < 10; k++) {
                            if (j==0) {
                                for (int l = 0; l < 2; l++) {
                                    if (hasBomb[k][l]) numberOfBombs[i][j]++;
                                }
                            } else if (j==9) {
                                for (int l = 8; l < 10; l++) {
                                    if (hasBomb[k][l]) numberOfBombs[i][j]++;
                                }
                            } else {
                                for (int l = j-1; l < j+2; l++) {
                                    if (hasBomb[k][l]) numberOfBombs[i][j]++;
                                }
                            }
                        }
                    } else if (j==0) {
                        for (int k = i-1; k < i+2; k++) {
                            for (int l = 0; l < 2; l++) {
                                if (hasBomb[k][l]) numberOfBombs[i][j]++;
                            }
                        }
                    } else if (j==9) {
                        for (int k = i-1; k < i+2; k++) {
                            for (int l = 8; l < 10; l++) {
                                if (hasBomb[k][l]) numberOfBombs[i][j]++;
                            }
                        }
                    }
                }
            }
        }

        return numberOfBombs;
    }

    private void changeBombNumber(String s) {
        if (s.equals("+")) {
            bombsNumber++;
        } else if (s.equals("-")) {
            bombsNumber--;
        }
        bombsLeftText.setText("Bombs left: " + bombsNumber);
    }
}