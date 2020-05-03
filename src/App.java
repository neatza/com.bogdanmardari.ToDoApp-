import exception.ItemAlreadyExistsException;
import exception.ParsingException;
import model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws ItemAlreadyExistsException {
        System.out.println(" Welcom to your TO DO APP. Please give us your name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        User user = new User(name);

        handleUserInput(user);
        //generateToDoFile(user);

    }

    public static void displayMenu() {
        System.out.println("Please chose what you want to do:\n" +
                "           1.Display\n" +
                "           2.Add\n" +
                "           3.Remove\n" +
                "           4.Update\n" +
                "           5.Generate File\n" +
                "           6.Import File\n" +
                "           7.Exit\n" +
                "           8.Display Menu");
    }

    public static void handleUserInput(User user) throws ItemAlreadyExistsException {
        System.out.println("Hello ! " + user.getName());
        boolean quit = false;
        displayMenu();
        Scanner scanner = new Scanner(System.in);
        while (!quit) {
            System.out.println(" Enter an action (8 - to show available actions)");
            int action = scanner.nextInt();
            scanner.nextLine();
            switch (action) {
                case 1:
                    displayItem(user);
                    break;
                case 2:
                    addItem(user);
                    break;
                case 3:
                    removeItem(user);
                    break;
                case 4:
                    updateItem(user);
                    break;
                case 5:
                    if (!user.getToDoList().isEmpty()) {
                        System.out.println("Are you sure you want to generate the file?\n" +
                                "Enter “yes” if you want to procceed! ");
                        String answer = scanner.nextLine();
                        System.out.println(answer);
                        if (answer.equals("yes")) {
                            System.out.println(" File is generatig.......");
                            generateToDoFile(user);
                            break;
                        } else {
                            displayMenu();
                        }
                    } else {
                        System.out.println("you don't have something to do.Please add something:");
                        addItem(user);
                    }

                    break;
                case 6:
                    try {
                        importToDoList(user);
                    } catch (ParsingException e) {
                        e.printStackTrace();
                    }
                    break;

                case 7:
                    System.out.println("\nSystem shuting down.....");
                    quit = true;
                    break;
                case 8:
                    displayMenu();

            }
        }
    }

    public static void addItem(User user) throws ItemAlreadyExistsException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        int intAnswer;
        System.out.println("Hello ! " + user.getName() + " What you want to do ? Please Enter 1-Normal Thing or 2-for High Priority Task");
        do {

            String answerInit = scanner.nextLine();
            intAnswer = Integer.parseInt(answerInit);
            if (intAnswer == 1 || intAnswer == 2) {
                flag = true;
            } else{
                System.out.println(" Enter 1-Normal Thing or 2-for High Priority Task ");
            }
        }
        while (!flag);
        if (intAnswer == 1) {
            System.out.println(" Please enter your thing to do:");
            String thing = scanner.nextLine();
            BaseToDo base1 = new BaseToDo(thing);
            if (!user.getToDoList().contains(base1)) {
                user.getToDoList().add(base1);
            } else {
                throw new ItemAlreadyExistsException();
            }
        } else {
            System.out.println(" Please enter your thing to do:");
            String thingPriority = scanner.nextLine();
            System.out.println(" Please Enter a integer as number of seconds:");
            int secondsRecived = scanner.nextInt();
            HighPriorityTodo highPriorityTodo = new HighPriorityTodo(thingPriority, LocalDateTime.now().plusSeconds(secondsRecived));
            if (!user.getToDoList().contains(highPriorityTodo)) {
                user.getToDoList().add(highPriorityTodo);
            } else{
                throw new ItemAlreadyExistsException();
            }
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.submit(new HighPriorityRunnable(highPriorityTodo));

        }


    }


    public static void displayItem(User user) {
        System.out.println("Hello ! " + user.getName() + " This is your TO DO List");
        List<BaseToDo> toDo = user.getToDoList();
        for(int i=0;i<user.getToDoList().size();i++){

            System.out.println((i+1)+". "+user.getToDoList().get(i));
        }
//        for (BaseToDo base : toDo) {
//            System.out.println((toDo.indexOf(base) + 1) + ". " + base.toString());
//        }

    }

    public static void removeItem(User user) {
        displayItem(user);
        System.out.println("What item do you want to remove: ? Give the number");
        Scanner scanner = new Scanner(System.in);
        int item = scanner.nextInt();
        user.getToDoList().remove(user.getToDoList().get(item - 1));
        System.out.println(" The item with no " + item + " has been removed");

    }

    public static void updateItem(User user) {
        System.out.println(" Here is your to do list : ");
        displayItem(user);
        System.out.println("Choose what number the do be updated has and the text");
        Scanner scanner = new Scanner(System.in);
        String updateContent = scanner.nextLine();
        StringBuilder stringBuilder = new StringBuilder();
        String[] stringArray = updateContent.split(" ");

        int choose = Integer.parseInt(stringArray[0]);
        for (int i = 1; i < stringArray.length; i++) {
            stringBuilder.append(stringArray[i]);
            stringBuilder.append(" ");
        }
        updateContent = stringBuilder.toString();
        BaseToDo updatedToDo = new BaseToDo(updateContent);

        user.getToDoList().set(choose - 1, updatedToDo);
    }

    public static void generateToDoFile(User user) {

        Path path = Paths.get("src\\todo-items\\" + user.getName() + ".txt");
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write("TODO Items for " + user.getName() + " :\n");
            for (BaseToDo bd : user.getToDoList()) {
                bw.write((user.getToDoList().indexOf(bd) + 1) + ". " + bd.toString() + ".\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void importToDoList(User user) throws ParsingException {
        Path path = Paths.get("src\\todo-items\\");
        FileVisitor<Path> customFileExplorer = new FileExplorer();
        Scanner scanner = new Scanner(System.in);
        // LinkedList<BaseToDo> listOfEvents = new LinkedList<>();
        LinkedList<String> importedList = new LinkedList<>();

        try {
            Files.walkFileTree(path, customFileExplorer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Integer, String> answerList = ((FileExplorer) customFileExplorer).getList();

        for (Map.Entry<Integer, String> entry : answerList.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println(" Which file you want to load? Enter a number please:");
        System.out.println(" Please choose a number:");
        int answer = scanner.nextInt();
        Path pathToLoad = Paths.get("src\\todo-items\\" + answerList.get(answer));
        String input;

        try (BufferedReader bf = Files.newBufferedReader(pathToLoad)) {
            while ((input = bf.readLine()) != null) {
                importedList.add(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (checkContent(importedList)) {

            for (int i = 1; i < importedList.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] stringToImport = importedList.get(i).split(" ");
                for (int j = 1; j < stringToImport.length; j++) {
                    if (j != stringToImport.length - 1) {
                        stringBuilder.append(stringToImport[j]);
                        stringBuilder.append(" ");
                    } else {
                        char[] charArray = stringToImport[j].toCharArray();

                        for (char ch : charArray) {

                            if (ch != '.') {
                                stringBuilder.append(ch);
                            }
                        }
                    }


                }
                user.getToDoList().add(new BaseToDo(stringBuilder.toString()));
            }

        } else {
            throw new ParsingException();
        }
    }

    public static boolean checkContent(LinkedList<String> listToCheck) {
        boolean checked = false;
        String[] inputToCheck;
        String[] firstPrase = listToCheck.get(0).split(" ");

        if (listToCheck.get(0).contains("TODO Items for") && firstPrase[firstPrase.length - 1].equals(":")) {
            for (int i = 1; i < listToCheck.size(); i++) {
                inputToCheck = listToCheck.get(i).split(" ");

                if (inputToCheck[0].equals(i + ".") && inputToCheck[inputToCheck.length - 1].endsWith(".")) {
                    checked = true;
                } else {
                    checked = false;
                }

            }
        }
        return checked;
    }
}
