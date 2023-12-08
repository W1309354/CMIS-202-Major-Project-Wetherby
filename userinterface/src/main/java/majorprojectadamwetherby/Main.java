package majorprojectadamwetherby;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application implements GeneratedNamesPath {
    // Scene variable
    private static Scene scene;

    // Root of the application
    private final Pane root = new Pane();

    // Create a name generator instance
    private static NameGenerator nameGenerator = new NameGenerator();
    // ArrayList of generated names
    private static ArrayList<String> generatedNames;
    // BST of initialized names
    private static BinarySearchTree initialNames;

    // The background to show generated names
    private final Pane showNamesPane = new Pane();
    // The vbox of the show names pane
    private final VBox showNamesVBox = new VBox();
    // The scrolling pane of the show names pane
    private final ScrollPane showNamesScrollPane = new ScrollPane();

    // The background of the user input pane
    private final Pane userInputPane = new Pane();
    // The button used to detect user input
    final Button generateButton = new Button("Generate New Name");
    // The labels used to tell the user what setting the slider is
    final Label characterLength = new Label("Max Character Length");
    final Label generateAmount = new Label("Names Generated");
    // The sliders for each setting
    final Slider characterLengthSlider = new Slider(4, 23, 1);
    final Slider generateAmountSlider = new Slider(1, 10, 1);

    // Initialize the background to show generated names
    private void initializeShowGeneratedNamesPane() {
        // Add the children to the pane
        showNamesPane.getChildren().addAll(showNamesVBox, showNamesScrollPane);

        // Set the preferred size of the background
        showNamesPane.setPrefSize(240, 920);

        // VBox size and spacing to make it look good
        showNamesVBox.setSpacing(5.0);
        showNamesVBox.setPrefWidth(220);

        // Insets to make scroll pane look good and size
        showNamesScrollPane.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        showNamesScrollPane.setPrefSize(240, 920);

        // Set scroll pane color to dark grey and border to grey
        showNamesScrollPane.setStyle("-fx-background: #E8E8E8; -fx-border-color: #BFBFBF;");

        showNamesScrollPane.setContent(showNamesVBox);
    }

    // Initialize the background for user inputs
    private void initializeUserInputPane() {
        // Add the children to the pane
        userInputPane.getChildren().addAll(generateButton, characterLength, generateAmount, characterLengthSlider,
                generateAmountSlider);

        // Set the settings for each slider
        characterLengthSlider.setShowTickLabels(true);
        generateAmountSlider.setShowTickLabels(true);
        characterLengthSlider.setValue(10);

        // Set up listeners for each slider to make sure it can only increment by 1s
        final ChangeListener<Number> characterLengthChangeListener = (obs, old, val) -> {
            final double roundedValue = Math.floor(val.doubleValue());
            characterLengthSlider.valueProperty().set(roundedValue);
        };

        characterLengthSlider.valueProperty().addListener(characterLengthChangeListener);

        final ChangeListener<Number> generateAmountChangeListener = (obs, old, val) -> {
            final double roundedValue = Math.floor(val.doubleValue());
            generateAmountSlider.valueProperty().set(roundedValue);
        };

        generateAmountSlider.valueProperty().addListener(generateAmountChangeListener);

        // Set the prefered size of the background
        userInputPane.setPrefSize(240, 920);
        // Move the pane to be to the size
        userInputPane.setLayoutX(240);

        // Move the labels & sliders in the middle of the pane
        characterLength.setLayoutX(67);
        generateAmount.setLayoutX(75);
        characterLengthSlider.setLayoutX(55);
        generateAmountSlider.setLayoutX(55);

        // Move labels & sliders down
        characterLength.setLayoutY(5);
        characterLengthSlider.setLayoutY(25);
        generateAmount.setLayoutY(80);
        generateAmountSlider.setLayoutY(100);

        // Set the size of the button to have some padding
        generateButton.setPrefSize(230, 35);
        // Move the generate button to the bottom of the pane
        generateButton.setTranslateY(875);

        // Set the x position of the button to be in the center of the pane
        generateButton.setLayoutX(5);
        // Set the y position of the button to be in the center of the pane
        generateButton.setLayoutY(5);

        // Set pane color to dark grey and border to grey
        userInputPane.setStyle("-fx-background: #E8E8E8; -fx-border-color: #BFBFBF;");

        // Create an event for when the button is clicked
        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent r) {
                // Max name length for generation
                int maxNameLength = (int)characterLengthSlider.getValue();
                // Generate names for however many times the user has specified
                for (int i = 0; i < generateAmountSlider.getValue(); i++) {
                    // Create the name generation thread
                    NameGenerationThread newThread = new NameGenerationThread(nameGenerator, maxNameLength);
                    // Create the thread
                    Thread thread = new Thread(newThread);
                    // Start the thread
                    thread.start();
                    // Join the thread
                    try {
                        thread.join();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    // Get the generated name
                    String generatedName = newThread.getGeneratedName();

                    // Make sure the name doesn't already exist in the initial names bst
                    if (initialNames.search(initialNames.getRoot(), generatedName) != null) {
                        return;
                    }

                    // Add it to the generated names arraylist
                    generatedNames.add(generatedName);
                    // Add it as a text label in the ui
                    addGeneratedNameLabel(generatedName);
                }
            }
        });
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

    // Read the generated names from a file
    private static void readGeneratedNamesFromFile() throws Exception {
        // Initialize the generated names array list
        generatedNames = new ArrayList<String>();
        // Initialize the initial names bst
        initialNames = new BinarySearchTree();

        // Get the file
        File generatedNamesFile = GENERATED_NAMES_PATH;

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

        // Loop through the names and add them to the bst
        for (int i = 0; i < generatedNames.size(); i++) {
            initialNames.insert(initialNames.getRoot(), generatedNames.get(i));
        }
    }

    // Use a quicksort to sort the generated names alphabetically
    // Efficient because it sepereates the array into two partitions, and seperates
    // those partitions, and so on
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

        // Create a stack for all the sorted names
        Stack<String> sortedNames = new Stack<String>();
        // This holds all the information to be saved

        String saveInformation = "";

        // Loop through each generated name in the array
        for (int i = 0; i < generatedNames.size(); i++) {
            // Add the name to the sorted names stack
            sortedNames.push(namesToSort[i]);
        }

        // Loop through the stack
        for (int i = 0; i < generatedNames.size(); i++) {
            // Add the name to the save information string
            if (i > 0 && i < generatedNames.size()) {
                saveInformation += "," + sortedNames.pop();
            } else {
                saveInformation += sortedNames.pop();
            }
        }

        // Create a new file
        File generatedNamesFile = GENERATED_NAMES_PATH;
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
        root.getChildren().addAll(showNamesPane, userInputPane);

        // Initialize all elements through methods
        initializeShowGeneratedNamesPane();
        initializeUserInputPane();

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
