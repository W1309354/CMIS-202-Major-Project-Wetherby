package majorprojectadamwetherby;

// Name generator class
public class NameGenerator {
    // List of letters
    private Letter[] alphabet;
    private CustomHashMap<Integer, CustomHashMap<Integer, Double>> prob;

    // Empty constructor
    public NameGenerator() {
        // Construct the alphabet
        alphabet = new Letter[] { new Letter('a', 'A', true, false, 0), new Letter('b', 'B', false, true, 1),
                new Letter('c', 'C', false, true, 2), new Letter('d', 'D', false, true, 3),
                new Letter('e', 'E', true, false, 4),
                new Letter('f', 'F', false, true, 5), new Letter('g', 'G', false, true, 6),
                new Letter('h', 'H', false, true, 7),
                new Letter('i', 'I', true, false, 8), new Letter('j', 'J', false, true, 9),
                new Letter('k', 'K', false, true, 10),
                new Letter('l', 'L', false, true, 11), new Letter('m', 'M', false, true, 12),
                new Letter('n', 'N', false, true, 13), new Letter('o', 'O', true, false, 14),
                new Letter('p', 'P', false, true, 15),
                new Letter('q', 'Q', false, true, 16), new Letter('r', 'R', false, true, 17),
                new Letter('s', 'S', false, true, 18),
                new Letter('t', 'T', false, true, 19), new Letter('u', 'U', true, false, 20),
                new Letter('v', 'V', false, true, 21), new Letter('w', 'W', false, true, 22),
                new Letter('x', 'X', false, true, 23),
                new Letter('y', 'Y', true, true, 24), new Letter('z', 'Z', false, true, 25) };

        // Define a probablity matrix
        // prob[i][j] = the probability that letter j comes after i

        // int prob[][] = new int[25][25];
        // // Loop through all the letters and add them to the probability matrix
        // for (int i = 0; i < alphabet.length; i++) {
        // int letters[] = new int[25];
        // for (int j = 0; j < alphabet.length; i++) {
        // letters[j] = 1;
        // }

        // prob[alphabet[i]] = letters;
        // }

        // System.out.println(prob);

        // Define a probablity matrix
        // prob[i][j] = the probability that letter j comes after i

        // Create a custom hash map for the probabilities
        this.prob = new CustomHashMap<Integer, CustomHashMap<Integer, Double>>(alphabet.length);

        // Loop through all the alphabet's letters
        for (int i = 0; i < alphabet.length; i++) {
            // Create a second custom hash map for the probably that a letter comes after
            // the current one
            CustomHashMap<Integer, Double> letters = new CustomHashMap<Integer, Double>(alphabet.length);

            // Loop through all the letters again and add them to the new probability custom
            // hash map
            for (int j = 0; j < alphabet.length; j++) {
                letters.put(j, 1.0);
            }

            // Put the new probability custom hash map in the larger one
            this.prob.put(i, letters);
        }

        // Set up variables for rarity so changing them later on will be easier
        Double rare = 0.1;

        // SET UP PROBABILITY RULES
        this.prob.get(23).set(23, 0.0); // No xx
        this.prob.get(12).set(3, rare); // Rare chance for md
        this.prob.get(16).set(17, 0.0); // No qr
        this.prob.get(23).set(16, 0.0); // No xq
        this.prob.get(23).set(12, 0.0); // No xm
        this.prob.get(12).set(23, 0.0); // No mx

        // Normalize the probability matrix
        for (int i = 0; i < alphabet.length; i++) {
            // Set up a total variable
            Double total = 0.0;

            // Loop through the row of probability rules
            for (int j = 0; j < alphabet.length; j++) {
                // Add the probability to the total
                total += this.prob.get(i).get(j);
            }

            // Loop through the row again
            for (int j = 0; j < alphabet.length; j++) {
                // Set the probability of the letter to be normalized by dividing it by the
                // total
                this.prob.get(i).set(j, this.prob.get(i).get(j) / total);
            }
        }
    }

    // Generate a random int
    private int randomInt(int min, int max) {
        // Return an int between min and max based on Math.random
        return (int) (Math.random() * max + min);
    }

    private Letter pickLetter(Integer i) {
        // Get a random number 0 - 1, since the probability matrix is normalized
        Double random = Math.random();

        // Set up the total variable
        Double total = 0.0;

        // Loop through the alphabet
        for (int j = 0; j < alphabet.length; j++) {
            total += this.prob.get(i).get(j);
            if (random <= total || j == alphabet.length) {
                return alphabet[j];
            }
        }

        // This shouldn't happen as the above loop should returning always
        System.out.println("This shouldn't happen");
        // Return the last letter in case this does
        return alphabet[alphabet.length];
    }

    // Generate a random letter
    private Letter getLetter(Integer previousNumber, Boolean needConsonant, Boolean needVowel) {
        // Done is used to check if the obtaining of the letter is done
        Boolean done = false;
        // Initialize a variable for the letter
        Letter letter = new Letter();

        // Keep doing this until the letter is found
        while (!done) {
            // Get a random letter from the alphabet
            letter = pickLetter(previousNumber);
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
        // Get the number of the previous letter used
        Integer previousNumber = 0;

        // Keep adding letters to the name until the name ends
        for (int i = 0; i < nameLength; i++) {
            // Get a letter with the method using the previously was bools
            Letter letter = getLetter(previousNumber, previouslyConsonant2, previouslyVowel2);
            // Check if the letter is the first letter
            if (i == 0) {
                // If so use the upper character
                generatedName = generatedName + letter.upperChar;
            } else {
                // Otherwise use the lower character
                generatedName = generatedName + letter.lowerChar;
            }
            // Update all the previously was bools
            previouslyVowel2 = (letter.getIsVowel() && previouslyVowel);
            previouslyConsonant2 = (letter.getIsConsonant() && previouslyConsonant);
            previouslyVowel = letter.getIsVowel();
            previouslyConsonant = letter.getIsConsonant();
            previousNumber = letter.getAlphabetIndex();
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
        // Get the number of the previous letter used
        Integer previousNumber = 0;

        // Keep adding letters to the name until the name ends
        for (int i = 0; i < nameLength; i++) {
            // Get a letter with the method using the previously was bools
            Letter letter = getLetter(previousNumber, previouslyConsonant2, previouslyVowel2);
            // Check if the letter is the first letter
            if (i == 0) {
                // If so use the upper character
                generatedName = generatedName + letter.upperChar;
            } else {
                // Otherwise use the lower character
                generatedName = generatedName + letter.lowerChar;
            }
            // Update all the previously was bools
            previouslyVowel2 = (letter.getIsVowel() && previouslyVowel);
            previouslyConsonant2 = (letter.getIsConsonant() && previouslyConsonant);
            previouslyVowel = letter.getIsVowel();
            previouslyConsonant = letter.getIsConsonant();
            previousNumber = letter.getAlphabetIndex();
        }

        // Return the generated name
        return generatedName;
    }
}
