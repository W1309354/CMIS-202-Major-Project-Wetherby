package majorprojectadamwetherby;

public class NameGenerationThread implements Runnable {
    private NameGenerator nameGenerator;
    private String generatedName;
    private Integer maxCharacterLength;

    public NameGenerationThread(NameGenerator nameGenerator, Integer maxCharacterLength) {
        this.nameGenerator = nameGenerator;
        this.maxCharacterLength = maxCharacterLength;
    }

    @Override
    public void run() {
        try {
            this.generatedName = nameGenerator.generateName(3, this.maxCharacterLength);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getGeneratedName() {
        return this.generatedName;
    }
}
