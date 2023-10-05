package majorprojectadamwetherby;

// Name generator class
public class NameGenerator {
    // List of letters
    private Letter[] alphabet;

    // Empty constructor
    public NameGenerator() {
        // Construct the alphabet
        alphabet = new Letter[] { new Letter('a', 'A', true, false), new Letter('b', 'B', false, true),
                new Letter('c', 'C', false, true), new Letter('d', 'D', false, true), new Letter('e', 'E', true, false),
                new Letter('f', 'F', false, true), new Letter('g', 'G', false, true), new Letter('h', 'H', false, true),
                new Letter('i', 'I', true, false), new Letter('j', 'J', false, true), new Letter('k', 'K', false, true),
                new Letter('l', 'L', false, true), new Letter('m', 'M', false, true),
                new Letter('n', 'N', false, true), new Letter('o', 'O', true, false), new Letter('p', 'P', false, true),
                new Letter('q', 'Q', false, true), new Letter('r', 'R', false, true), new Letter('s', 'S', false, true),
                new Letter('t', 'T', false, true), new Letter('u', 'U', true, false),
                new Letter('v', 'V', false, true), new Letter('w', 'W', false, true), new Letter('x', 'X', false, true),
                new Letter('y', 'Y', true, true), new Letter('z', 'Z', false, true) };
    }

    // Generate a random int
    private int randomInt(int min, int max) {
        // Return an int between min and max based on Math.random
        return (int) (Math.random() * max + min);
    }

    // Generate a random letter
    private Letter getLetter(Boolean needConsonant, Boolean needVowel) {
        // Done is used to check if the obtaining of the letter is done
        Boolean done = false;
        // Initialize a variable for the letter
        Letter letter = new Letter();

        // Keep doing this until the letter is found
        while (!done) {
            // Get a random letter from the alphabet
            letter = alphabet[randomInt(0, 25)];
            // Check if the letter is what the request needs
            if ((needConsonant && letter.isVowel) || (needVowel && letter.isConsonant)) {
                // If not then don't stop searching
                done = false;
            } else {
                // If so then stop searching
                done = true;
            }
        }

        // Return the letter
        return letter;
    }

    // Generate a random name with no min/max of the name length
    public String generateName() {
        // Get a random length based on a preset min/max
        int nameLength = randomInt(3, 10);

        // Initialize a variable for the generated name
        String generatedName = "";

        // Check if the letter was previously a vowel
        Boolean previouslyVowel = false;
        // Check if the letter was previously a consonant
        Boolean previouslyConsonant = false;
        // Check if the letter was previously a vowel pt 2
        Boolean previouslyVowel2 = false;
        // Check if the letter was previously a consonant pt 2
        Boolean previouslyConsonant2 = false;

        // Keep adding letters to the name until the name ends
        for (int i = 0; i < nameLength; i++) {
            // Get a letter with the method using the previously was bools
            Letter letter = getLetter(previouslyConsonant2, previouslyVowel2);
            // Check if the letter is the first letter
            if (i == 0) {
                // If so use the upper character
                generatedName = generatedName + letter.upperChar;
            } else {
                // Otherwise use the lower character
                generatedName = generatedName + letter.lowerChar;
            }
            // Update all the previously was bools
            previouslyVowel2 = (letter.isVowel && previouslyVowel);
            previouslyConsonant2 = (letter.isConsonant && previouslyConsonant);
            previouslyVowel = letter.isVowel;
            previouslyConsonant = letter.isConsonant;
        }

        // Return the generated name
        return generatedName;
    }

    // Generate a name with a min/max for the name length
    public String generateName(int min, int max) {
        // Get a random length based on a min/max provided
        int nameLength = randomInt(min, max);

        // Initialize a variable for the generated name
        String generatedName = "";

        // Check if the letter was previously a vowel
        Boolean previouslyVowel = false;
        // Check if the letter was previously a consonant
        Boolean previouslyConsonant = false;
        // Check if the letter was previously a vowel pt 2
        Boolean previouslyVowel2 = false;
        // Check if the letter was previously a consonant pt 2
        Boolean previouslyConsonant2 = false;

        // Keep adding letters to the name until the name ends
        for (int i = 0; i < nameLength; i++) {
            // Get a letter with the method using the previously was bools
            Letter letter = getLetter(previouslyConsonant2, previouslyVowel2);
            // Check if the letter is the first letter
            if (i == 0) {
                // If so use the upper character
                generatedName = generatedName + letter.upperChar;
            } else {
                // Otherwise use the lower character
                generatedName = generatedName + letter.lowerChar;
            }
            // Update all the previously was bools
            previouslyVowel2 = (letter.isVowel && previouslyVowel);
            previouslyConsonant2 = (letter.isConsonant && previouslyConsonant);
            previouslyVowel = letter.isVowel;
            previouslyConsonant = letter.isConsonant;
        }

        // Return the generated name
        return generatedName;
    }
}
