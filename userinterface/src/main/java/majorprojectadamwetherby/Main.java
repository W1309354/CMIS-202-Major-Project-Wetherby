package majorprojectadamwetherby;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    // Scene variable
    private static Scene scene;

    // Root of the application
    private final Pane root = new Pane();

    // Create a name generator instance
    private static NameGenerator nameGenerator = new NameGenerator();
    // ArrayList of generated names
    private static ArrayList<String> generatedNames;

    // The background to show generated names
    private final Pane showNamesPane = new Pane();
    // The vbox of the show names pane
    private final VBox showNamesVBox = new VBox();
    // The scrolling pane of the show names pane
    private final ScrollPane showNamesScrollPane = new ScrollPane();

    // The background to generate names
    final Pane generatePane = new Pane();
    // The button used to detect user input
    final Button generateButton = new Button("Generate New Names");

    // Initialize the background to show generated names
    private void initializeShowGeneratedNamesPane() {
        // Add the children to the pane
        showNamesPane.getChildren().addAll(showNamesVBox, showNamesScrollPane);

        // Set the preferred size of the background
        showNamesPane.setPrefSize(480, 875);

        // VBox size and spacing to make it look good
        showNamesVBox.setSpacing(5.0);
        showNamesVBox.setPrefWidth(460);

        // Insets to make scroll pane look good and size
        showNamesScrollPane.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        showNamesScrollPane.setPrefSize(480, 875);

        // Set scroll pane color to dark grey and border to grey
        showNamesScrollPane.setStyle("-fx-background: #E8E8E8; -fx-border-color: #BFBFBF;");

        showNamesScrollPane.setContent(showNamesVBox);
    }

    // Add a generated name as a label to show that it was added
    private void addGeneratedNameLabel(String generatedName) {
        // Create the label
        Label generatedNameLabel = new Label(generatedName);
        // Set the mind width to the vbox pref width
        generatedNameLabel.setMinWidth(showNamesVBox.getPrefWidth());
        // Add it to the vbox
        showNamesVBox.getChildren().add(generatedNameLabel);
    }

    // Initialize the button to generate names
    private void initializeGeneratePane() {
        // Add the button to the pane
        generatePane.getChildren().add(generateButton);

        // Set the size of the pane to take up the whole screen
        generatePane.setPrefSize(480, 45);
        // Set the size of the button to have some padding
        generateButton.setPrefSize(470, 35);

        // Set the y position of the pane to be at the bottom
        generatePane.setLayoutY(875);
        // Set the x position of the button to be in the center of the pane
        generateButton.setLayoutX(5);
        // Set the y position of the button to be in the center of the pane
        generateButton.setLayoutY(5);

        // Set scroll pane color to dark grey and border to grey
        generatePane.setStyle("-fx-background: #E8E8E8; -fx-border-color: #BFBFBF;");

        // Create an event for when the button is clicked
        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent r) {
                String generatedName = nameGenerator.generateName();
                generatedNames.add(generatedName);
                addGeneratedNameLabel(generatedName);
            }
        });
    }

    // Read the generated names from a file
    private static void readGeneratedNamesFromFile() throws Exception {
        // Initialize the generated names array list
        generatedNames = new ArrayList<String>();

        // Get the file
        File generatedNamesFile = new File("./src/GeneratedNames.txt");

        // Check if the file is valid
        if (!generatedNamesFile.exists() || generatedNamesFile.length() == 0) {
            return;
        }

        // Create a scanner
        Scanner generatedNamesScanner = new Scanner(generatedNamesFile);
        // Get the one line everything is stored on
        String generatedNamesInFile = generatedNamesScanner.nextLine();
        // Close the scanner
        generatedNamesScanner.close();

        // Get the split string
        String[] generatedNamesSplit = generatedNamesInFile.split(",");
        // Set the generated names arraylist to the new array list
        generatedNames = new ArrayList<String>(Arrays.asList(generatedNamesSplit));
    }

    // Use a quicksort to sort the generated names alphabetically
    // Efficient because it sepereates the array into two partitions, and seperates those partitions, and so on
    // It sorts the individual partitions making it efficient
    private static void sortGeneratedNames(String[] namesToSort, int lo, int hi) {
        // Get the i and j for the sorting
        int i = lo;
        int j = hi;

        // Get the pivot for sorting
        String pivot = namesToSort[lo + (hi - lo) / 2];

        // Scan between the two indexes until they meet
        while (i <= j) {
            // From the left, if the current element is less than the pivot point then move
            // on. Stop advancing the counter when the right element is reached or an
            // element is greater than the pivot string
            while (namesToSort[i].compareToIgnoreCase(pivot) < 0) {
                i++;
            }

            // From the right, if the current element is greater than the pivot point move
            // on. Stop advancing the counter when the right element is reached or an
            // element less than the pivot string
            while (namesToSort[j].compareToIgnoreCase(pivot) > 0) {
                j--;
            }

            // Last comparison
            if (i <= j) {
                // Swap the two indexes
                swapGeneratedNames(namesToSort, i, j);
                // Represent the swap
                i++;
                j--;
            }
        }

        // Call quicksort recursively
        if (lo < j) {
            sortGeneratedNames(namesToSort, lo, j);
        }
        if (i < hi) {
            sortGeneratedNames(namesToSort, i, hi);
        }
    }

    // Swap two elements in the generated name arraylist
    private static void swapGeneratedNames(String[] namesToSort, int i, int j) {
        // Get the string to swap
        String temp = namesToSort[i];
        // Swap the first index string to the other index
        namesToSort[i] = namesToSort[j];
        // Swap the other index to the first index
        namesToSort[j] = temp;
    }

    // Save generated names to file
    private static void saveGeneratedNamesToFile() throws Exception {
        // Make sure the generated names arraylist has more than 2 elements
        if (generatedNames.size() < 2) {
            return;
        }
        // Get the generated names arraylist as an array
        String[] namesToSort = new String[generatedNames.size()];
        namesToSort = generatedNames.toArray(namesToSort);
        // Sort the generated names array
        sortGeneratedNames(namesToSort, 0, generatedNames.size() - 1);

        // This holds all the information to be saved
        String saveInformation = String.join(",", namesToSort);

        // Create a new file
        File generatedNamesFile = new File("./src/GeneratedNames.txt");
        // Create a new print writer
        PrintWriter fileWriter = new PrintWriter(generatedNamesFile);
        // Print the save information into the file
        fileWriter.print(saveInformation);
        // Cloe the file
        fileWriter.close();
    }

    // Application.start method
    @Override
    public void start(Stage stage) throws IOException {
        // New scene
        scene = new Scene(root, 480, 920);
        // Stop the scene from being resized
        stage.setResizable(false);
        // Set the scene
        stage.setScene(scene);
        // Set the title
        stage.setTitle("Name Generator");

        // Add the elements to the scene
        root.getChildren().addAll(showNamesPane, generatePane);

        // Initialize all elements through methods
        initializeShowGeneratedNamesPane();
        initializeGeneratePane();

        // Make sure the platform can't exit without saving
        Platform.setImplicitExit(false);

        // When the app closes this will fire
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // Create a bool to make sure the saving was successful
                Boolean savedCorrectly = false;
                // Try to save
                try {
                    // Call the method
                    saveGeneratedNamesToFile();
                    // Set the bool to true
                    savedCorrectly = true;
                } catch (Exception e) {
                    // Catch and print any exceptions
                    System.out.println(e.getMessage());
                }

                // If it didn't save correctly then ignore this and don't close
                if (!savedCorrectly) {
                    event.consume();
                    // If it did save then close the application
                } else {
                    Platform.exit();
                }
            }
        });

        // Try and get the generated names from file
        try {
            readGeneratedNamesFromFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Initialize the generated name labels
        for (int i = 0; i < generatedNames.size(); i++) {
            addGeneratedNameLabel(generatedNames.get(i));
        }

        // Show the scene
        stage.show();
    }

    // Main method
    public static void main(String[] args) {
        launch();
    }
}
