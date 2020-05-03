package model;

public class BaseToDo {
    private String content;

    public BaseToDo(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
       // System.out.println(this.content);
        return this.content;
    }
}
