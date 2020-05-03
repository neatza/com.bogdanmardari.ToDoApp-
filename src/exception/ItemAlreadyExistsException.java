package exception;

public class ItemAlreadyExistsException extends Exception {
    public ItemAlreadyExistsException() {
        super();
        System.out.println(" The item is allready on your list TO DO.Please add oneother thing!");

    }
}
