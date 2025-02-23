import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FlashcardSetCollection {
    private ArrayList<FlashcardSet> sets;

    public FlashcardSetCollection(){
        sets = new ArrayList<>();
    }

    public void addFlashCardSet(FlashcardSet set){
        sets.add(set);
    }

    public FlashcardSet getSet(int index){
        return sets.get(index);
    }

    public void removeSet(int index){
        sets.remove(index);
    }

    public boolean isEmpty(){
        return sets.isEmpty();
    }

    public FlashcardSet loadSetFromCSV(String filename){
        File file = new File(filename);

        if(!file.exists()){
            System.out.println("Cannot load set from file, file does not exist.");
        }

        String setName = filename.replace(".csv", "");
        FlashcardSet newSet = new FlashcardSet(setName);
        Flashcard currentCard;
        String currentLine;
        String[] currentLineSplit;
        String currentQuestion;
        String currentAnswer;

        try(BufferedReader fileReader = new BufferedReader(new FileReader(filename))){
            fileReader.readLine(); // Discard the first line which has the column names

            while((currentLine = fileReader.readLine()) != null){
                currentLineSplit = currentLine.split(",");
                currentQuestion = currentLineSplit[0];
                currentAnswer = currentLineSplit[1];
                currentCard = new Flashcard(currentQuestion, currentAnswer);
                newSet.addCard(currentCard);
            }

            sets.add(newSet);
            return newSet;

        } catch (IOException e){
            System.out.println("An error occurred trying to load the set from the file: " + e.getMessage());
        }

        return null;
    }

    public void printCollection(){
        if(sets.isEmpty()){
            System.out.println("There are currently no flashcard sets in the collection");
            return;
        }
        System.out.println("This is the list of flash card sets in the collection: ");

        if(sets.isEmpty()){
            System.out.println("There are no cards in this set.");
        }

        for(int i = 0; i < sets.size(); i++){
            FlashcardSet set = sets.get(i);
            System.out.println(i+1 + ". " + set.getName());
        }
        System.out.println();
    }
}
