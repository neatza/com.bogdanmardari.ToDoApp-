package exception;

public class ParsingException extends Exception {
    public ParsingException() {
        super();
        System.out.println(" Format of the file to be parsed is Wrong!! Try a new File !" );
    }
}
