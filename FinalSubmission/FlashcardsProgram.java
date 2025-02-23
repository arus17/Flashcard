import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FlashcardsProgram {
    private FlashcardSetsCollection collection;
    private FlashcardSet currentSet;
    public Scanner scanner;


    public FlashcardsProgram(){
        collection = new FlashcardSetsCollection();
        currentSet = null;
        scanner = new Scanner(System.in);
    }


    public void processChoice(int choice){
        int cardChoice = 0;
        int setChoice = 0;
        String response;

        switch(choice) {
            case 1:
                System.out.print("Enter the name of the flashcard set: ");
                String setName = scanner.next();
                FlashcardSet set = new FlashcardSet(setName);
                collection.addFlashCardSet(set);
                System.out.print("Would you like to make this the current set? (y/n): ");
                response = scanner.next();
                System.out.println();
                if(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")){
                    currentSet = set;
                }
                break;
            case 2:
                collection.printCollection();
                System.out.print("Enter the number of the set you want to load: ");
                int setOption = handleImproperInput(collection.getNumOfSets(), "collection");
                //scanner.nextInt();
                currentSet = collection.getSet(setOption-1);
                break;
            case 3:
                if(currentSet==null){
                    System.out.println("You must first have a set loaded");
                    break;
                }

                scanner.nextLine();
                System.out.print("Enter a question: ");
                String question = scanner.nextLine();
                System.out.print("Enter the answer: ");
                String answer = scanner.nextLine();
                System.out.println();
                Flashcard card = new Flashcard(question, answer);
                currentSet.addCard(card);
                break;

            case 4:
                if(currentSet==null){
                    System.out.println("You must first have a set loaded");
                    break;
                }
                currentSet.printCards();
                System.out.print("Enter the number of the card you want to delete: ");

                cardChoice = handleImproperInput(currentSet.getNumOfCards(), "set") ;
                //scanner.nextInt();
                System.out.println();

                currentSet.removeCard(cardChoice-1);
                System.out.println("Sucessfully deleted card number "+cardChoice);
                break;
            case 5:
                if(currentSet==null){
                    System.out.println("You must first have a set loaded");
                    break;
                }
                currentSet.playQuiz();
                break;
            case 6:
                if(currentSet==null){
                    System.out.println("You must first have a set loaded");
                    break;
                }
                if(currentSet.isEmpty()){
                    System.out.println("There are currently no cards in the set");
                    break;
                }
                currentSet.printCards();
                System.out.print("Select the number of the card you would like to edit: ");
                cardChoice = handleImproperInput(currentSet.getNumOfCards(), "set");
                //scanner.nextInt();
                System.out.println();
                currentSet.printCard(cardChoice-1);
                System.out.println();
                scanner.nextLine();
                System.out.print("Would you like to edit the (q)uestion or (a)nswer? ");
                String editChoice = scanner.next();
                scanner.nextLine();

                if(editChoice.equalsIgnoreCase("q") || editChoice.equalsIgnoreCase("question")){
                    System.out.print("What would you like to change the question to? ");
                    String newQuestion = scanner.nextLine();
                    currentSet.editCardQuestion(cardChoice-1, newQuestion);
                    System.out.println("Question successfully changed.");
                }
                else {
                    System.out.print("What would you like to change the answer to? ");
                    String newAnswer = scanner.nextLine();
                    currentSet.editCardAnswer(cardChoice-1, newAnswer);
                    System.out.println("Answer successfully changed.");
                }
                break;
            case 7:
                if(currentSet==null){
                    System.out.println("You must first have a set loaded");
                    break;
                }

                System.out.println("You are currently in the set named: " + currentSet.getName());
                break;
            case 8:
                if(currentSet==null){
                    System.out.println("You must first have a set loaded");
                    break;
                }

                currentSet.printCards();
                break;
            case 9:
                if(currentSet==null){
                    System.out.println("You must first have a set loaded");
                    break;
                }

                currentSet.writeToCSV();
                break;
            case 10:
                System.out.print("Enter the filename of the set you would like to load: ");
                String fileName = scanner.next();
                System.out.println();
                FlashcardSet newSet = collection.loadSetFromCSV(fileName);
                System.out.print("Would you like to make this the current set? (y/n): ");
                response = scanner.next();
                System.out.println();

                if(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")){
                    currentSet = newSet;
                }

                break;
            case 11:
                if(collection.isEmpty()){
                    System.out.println("There are currently no flashcard sets in the collection");
                    break;
                }
                collection.printCollection();
                System.out.print("Enter the number of the set you want to delete: ");
                setChoice = handleImproperInput(collection.getNumOfSets(), "collection");
                //scanner.nextInt();
                System.out.println();
                String toDelete = collection.getSet(setChoice-1).getName();
                collection.removeSet(setChoice-1);
                System.out.println("Successfully deleted set number " + toDelete /*collection.getSet(setChoice-1).getName()*/);
                break;
            case 12:
                collection.printCollection();
                break;
            case 13:
                break;
            default:
                System.out.println("Not a valid option. Try again.");
                break;
        }
    }

    public void printMenu(){
        String menu = """
                1. Create a set of flashcards
                2. Change the current set of flashcards to be used
                3. Create a flashcard and add it to the current set
                4. Delete a flashcard from the current set 
                5. Take a quiz on the flashcards in the current set
                6. Edit a flashcard in the current set
                7. Display the name of the current set
                8. Display all flashcards in the current set
                9. Save current set to a file
                10. Load a set from a CSV file and add it to the collection
                11. Delete a flashcard set from the collection 
                12. Print list of all flashcard sets
                13. Exit
                
                Select a choice:\s
                """;

        System.out.print(menu);
    }

    public void printTutorial(){
        String tutorial = """
        Welcome to the flashcard application!
        
        With this program you have the ability to create different sets of flashcards, where a set is a group of flashcards under a given topic 
        (e.g., a specific subject in school like Math). You can create various flashcards, where a flashcard is defined as a pair of a question 
        and an answer, and add them to the various sets.
        
        Please thoroughly read the menu to see all possible options and capabilities, and then follow any on-screen instructions or prompts 
        related to that option. 

        For a majority of the functions, you must currently have a set loaded, and that given function can only be performed on the current set 
        (e.g., creating/deleting/editing/displaying flashcards, etc.). Therefore, if you want to, for example, add a flashcard to a different set 
        than the one currently loaded, you must first make that other set the "current set" (option 2 in the menu). 
        
        Note that any operations related to files (e.g., loading a set from a file or writing a set to a file) assume that the filename and the 
        name of that flashcard set are the same. So for example, a flashcard set named "biology" will be saved to a file "bioliogy.csv" (and 
        similarly, if you load the set from the file "biology.csv", it will load the set under the name "biology". Currently CSV is 
        the only file format supported for loading and saving to files. When a file is written to, the first line will be the names of the columns 
        ("question,answer"). Simiarly, when a set is loaded from a file it is assumed that the first line will be this same list of column names 
        and will therefore be discarded. 
        
        A primary capability of the program is a flashcard quiz (option 5 in the menu). The quiz will display the questions in that 
        flashcard set in random order and prompt you for the answer. You will be informed if you answered correctly or not. You
        will also have the option to either skip to the next question or exit out of the quiz entirely. 
        
        """;

        System.out.println(tutorial);

    }

    public int handleImproperInput(int allowedRange, String groupType) {
        int choice = -1;

        while (true) {
                try {
                    choice = scanner.nextInt();
                    if (choice < 1 || choice > allowedRange)  {
                        throw new InputMismatchException();
                    }
                    break;

                } catch (InputMismatchException e) {
                    System.out.println("invalid input. Please try again:\n");
                    if (groupType.equalsIgnoreCase("set")){
                        currentSet.printCards();  
                        System.out.print("Enter the corresponding number of the card: ");
                    } 
                    else if (groupType.equalsIgnoreCase("collection")) {
                        collection.printCollection();
                        System.out.print("Enter the corresponding number of the set: ");
                    }
                    
                    scanner.nextLine();
                }

        }
        
        return choice;
    }

    public static void main(String[] args) {
        int choice = 0;

        FlashcardsProgram program = new FlashcardsProgram();
        program.printTutorial();

        while(choice != 13){
            program.printMenu();
            choice = program.scanner.nextInt();
            System.out.println();
            program.processChoice(choice);
            System.out.println();
        }

        program.scanner.close();
    }
}
