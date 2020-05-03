package model;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class FileExplorer implements FileVisitor<Path> {
    public int counter=0;
    public Map<Integer,String> list=new HashMap<>();

    public FileExplorer() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Map<Integer, String> getList() {
        return list;
    }

    public void setList(Map<Integer, String> list) {
        this.list = list;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //System.out.println(" Am fost aici 1");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {


        if(file.getFileName().toString().endsWith(".txt")){
//            System.out.println(" the counter is :"+counter);
//            System.out.println("File name: "+file.getFileName()+" -creation date :"+attrs.creationTime());
            counter++;
            list.put(counter,file.getFileName().toString());
        }
//        for(Map.Entry<Integer,String> entry:list.entrySet()){
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println(" An exception has occurred while trying to access "+file.getFileName());
        System.out.println(" Exception"+ exc.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
