package model;

import java.time.LocalDateTime;
import java.util.Date;

public class HighPriorityTodo extends BaseToDo{
    private LocalDateTime deadLine;

    public HighPriorityTodo(String content, LocalDateTime deadLine) {
        super(content);
        this.deadLine = deadLine;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        //System.out.println(super.getContent()+deadLine);
        return getContent();
    }
}
