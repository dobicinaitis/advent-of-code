package dev.dobicinaitis.advent;

abstract class Puzzle {
    private static final String EXAMPLE_FILENAME_TEMPLATE = "day{number}_example_input.txt";
    private static final String MY_FILENAME_TEMPLATE = "day{number}_my_input.txt";
    protected String exampleInputFilename;
    protected String myInputFilename;

    protected Puzzle() {
        this.exampleInputFilename = EXAMPLE_FILENAME_TEMPLATE.replace("{number}", String.valueOf(getDayNumber()));
        this.myInputFilename = MY_FILENAME_TEMPLATE.replace("{number}", String.valueOf(getDayNumber()));
    }

    private int getDayNumber(){
        return Utils.extractFirstNumberFromString(getClass().getSimpleName());
    }

    public abstract Object getFirstPuzzleSolution(String filename);
    public abstract Object getSecondPuzzleSolution(String filename);
}
