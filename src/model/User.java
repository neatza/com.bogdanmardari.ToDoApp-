package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<BaseToDo> toDoList;

    public User(String name) {
        this.name = name;
        this.toDoList=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseToDo> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<BaseToDo> toDoList) {
        this.toDoList = toDoList;
    }

    @Override
    public String toString() {
        System.out.println(this.name+" to do list is:");
        for(BaseToDo base:this.toDoList){
            System.out.println((toDoList.indexOf(base)+1)+". "+ base.toString());
        }
        return null;
    }
}
