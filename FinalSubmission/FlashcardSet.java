import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FlashcardSet {
    private ArrayList<Flashcard> cards;
    private String name;
    private int currNumOfCards;
    private Scanner scanner;

    public FlashcardSet(String name){
        this.name = name;
        cards = new ArrayList<>();
        currNumOfCards = cards.size();
        scanner = new Scanner(System.in);
    }

    public int getNumOfCards() {
        currNumOfCards = cards.size();
        return currNumOfCards;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addCard(Flashcard card){
        cards.add(card);
    }

    public void removeCard(int index){
        cards.remove(index);
    }

    public void editCardQuestion(int index, String newQuestion){
        cards.get(index).setQuestion(newQuestion);
    }

    public void editCardAnswer(int index, String newAnswer){
        cards.get(index).setAnswer(newAnswer);
    }

    public void printCard(int index){
        System.out.println("Question: " + cards.get(index).getQuestion());
        System.out.println("Answer: " + cards.get(index).getAnswer());
    }
    public void printCards(){
        if(cards.isEmpty()){
            System.out.println("There are no cards in this set.");
        }
        else {
            for(int i = 0; i < cards.size(); i++){
                Flashcard flashcard = cards.get(i);
                System.out.println(i+1 + ". Question: " + flashcard.getQuestion());
                System.out.println("    Answer: " + flashcard.getAnswer());
            }
        }
        System.out.println();
    }

    public void writeToCSV(){
        if(isEmpty()){
            System.out.println("There are no cards in this deck - nothing to save.");
            return;
        }
        String filename = getName()+".csv";
        try(FileWriter writer = new FileWriter(filename)){
            writer.write("question,answer\n");
            for(Flashcard card : cards){
                writer.write(card.getQuestion() + "," + card.getAnswer() + "\n");
            }
            System.out.println("Set successfully saved to file.");
        } catch (IOException e){
            System.out.println("There was an error saving the set to a file:" + e.getMessage());
        }
    }

    public void playQuiz(){
        if(cards.isEmpty()){
            System.out.println("There are currently no cards in the set.");
            return;
        }
        ArrayList<Flashcard> shuffled = (ArrayList<Flashcard>) cards.clone();
        Collections.shuffle(shuffled);
        String userAnswer;

        for(Flashcard card : shuffled){
            System.out.println("Question: " + card.getQuestion());
            System.out.print("Your guess: ");
            userAnswer = scanner.nextLine();
            System.out.println();
            while(!userAnswer.equalsIgnoreCase("next")) {
                if (userAnswer.equalsIgnoreCase(card.getAnswer())) {
                    System.out.println("You got the right answer! Good job!");
                    break;
                } else {
                    System.out.print("Incorrect answer. Try again. Type 'next' move to the next question: " +
                            "Type 'exit' to give up on the game entirely: ");
                    userAnswer = scanner.nextLine();
                    System.out.println();
                    if(userAnswer.equalsIgnoreCase("exit")){
                        return;
                    }
                }
            }
        }
        System.out.println("You have gone through all flashcards in the set. The quiz is over.");
    }
}
