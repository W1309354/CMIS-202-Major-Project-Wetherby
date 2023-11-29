package majorprojectadamwetherby;

// Name generator class
public class NameGenerator {
    // List of letters
    private CustomHashTable<Integer, Letter> alphabet;
    // The probability matrix
    private CustomHashMap<Integer, CustomHashMap<Integer, Double>> prob;

    // Empty constructor
    public NameGenerator() {
        // Construct the alphabet
        alphabet = new CustomHashTable<Integer, Letter>();
        alphabet.add(0, new Letter('a', 'A', true, false));
        alphabet.add(1, new Letter('b', 'B', false, true));
        alphabet.add(2, new Letter('c', 'C', false, true));
        alphabet.add(3, new Letter('d', 'D', false, true));
        alphabet.add(4, new Letter('e', 'E', true, false));
        alphabet.add(5, new Letter('f', 'F', false, true));
        alphabet.add(6, new Letter('g', 'G', false, true));
        alphabet.add(7, new Letter('h', 'H', false, true));
        alphabet.add(8, new Letter('i', 'I', true, false));
        alphabet.add(9, new Letter('j', 'J', false, true));
        alphabet.add(10, new Letter('k', 'K', false, true));
        alphabet.add(11, new Letter('l', 'L', false, true));
        alphabet.add(12, new Letter('m', 'M', false, true));
        alphabet.add(13, new Letter('n', 'N', false, true));
        alphabet.add(14, new Letter('o', 'O', true, false));
        alphabet.add(15, new Letter('p', 'P', false, true));
        alphabet.add(16, new Letter('q', 'Q', false, true));
        alphabet.add(17, new Letter('r', 'R', false, true));
        alphabet.add(18, new Letter('s', 'S', false, true));
        alphabet.add(19, new Letter('t', 'T', false, true));
        alphabet.add(20, new Letter('u', 'U', true, false));
        alphabet.add(21, new Letter('v', 'V', false, true));
        alphabet.add(22, new Letter('w', 'W', false, true));
        alphabet.add(23, new Letter('x', 'X', false, true));
        alphabet.add(24, new Letter('y', 'Y', true, true));
        alphabet.add(25, new Letter('z', 'Z', false, true));

        // Create a custom hash map for the probabilities
        this.prob = new CustomHashMap<Integer, CustomHashMap<Integer, Double>>(alphabet.getSize());

        // Loop through all the alphabet's letters
        for (int i = 0; i < alphabet.getSize(); i++) {
            // Create a second custom hash map for the probably that a letter comes after
            // the current one
            CustomHashMap<Integer, Double> letters = new CustomHashMap<Integer, Double>(alphabet.getSize());

            // Loop through all the letters again and add them to the new probability custom
            // hash map
            for (int j = 0; j < alphabet.getSize(); j++) {
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
        this.prob.get(23).set(3, 0.0); // No xd
        this.prob.get(22).set(5, 0.0); // No wf
        this.prob.get(10).set(16, 0.0); // No kq
        this.prob.get(0).set(0, 0.0); // No aa
        this.prob.get(14).set(14, 0.0); // No oo
        this.prob.get(4).set(4, 0.0); // No ee
        this.prob.get(8).set(8, 0.0); // No ii
        this.prob.get(19).set(19, 0.0); // No uu
        this.prob.get(24).set(24, 0.0); // No yy
        this.prob.get(11).set(11, 0.0); // No mm
        this.prob.get(16).set(13, 0.0); // No qn
        this.prob.get(23).set(6, 0.0); // No xg
        this.prob.get(2).set(25, rare); // Rare chance for cz
        this.prob.get(25).set(18, rare); // Rare chance for zs
        this.prob.get(18).set(25, rare); // Rare chance for sz
        this.prob.get(25).set(2, rare); // Rare chance for zc

        // Normalize the probability matrix
        for (int i = 0; i < alphabet.getSize(); i++) {
            // Set up a total variable
            Double total = 0.0;

            // Loop through the row of probability rules
            for (int j = 0; j < alphabet.getSize(); j++) {
                // Add the probability to the total
                total += this.prob.get(i).get(j);
            }

            // Loop through the row again
            for (int j = 0; j < alphabet.getSize(); j++) {
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
        for (int j = 0; j < alphabet.getSize(); j++) {
            total += this.prob.get(i).get(j);
            if (random <= total || j == alphabet.getSize()) {
                return alphabet.get(j);
            }
        }

        // This shouldn't happen as the above loop should returning always
        System.out.println("This shouldn't happen");
        // Return the last letter in case this does
        return alphabet.get(alphabet.getSize());
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
            previousNumber = i;
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
            previousNumber = i;
        }

        // Return the generated name
        return generatedName;
    }
}
