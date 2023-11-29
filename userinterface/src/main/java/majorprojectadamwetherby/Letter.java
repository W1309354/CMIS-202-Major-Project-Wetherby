package majorprojectadamwetherby;

// Class letter
public class Letter {
    // Lower character of the letter
    public char lowerChar;
    // Upper character of the letter
    public char upperChar;
    // Is it a vowel
    public Boolean isVowel;
    // Is it a consonant
    public Boolean isConsonant;

    // Empty constructor
    public Letter() {

    }

    // Constructor with all the information
    public Letter(char lowerChar, char upperChar, Boolean isVowel, Boolean isConsonant) {
        this.lowerChar = lowerChar;
        this.upperChar = upperChar;
        this.isVowel = isVowel;
        this.isConsonant = isConsonant;
    }

    // Methods to get attributes of the letter
    public char getLowerChar() {
        return this.lowerChar;
    }

    public char getUpperChar() {
        return this.upperChar;
    }

    public Boolean getIsVowel() {
        return this.isVowel;
    }

    public Boolean getIsConsonant() {
        return this.isConsonant;
    }
}
