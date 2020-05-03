package model;

import java.time.LocalDateTime;

public class HighPriorityRunnable implements Runnable {
    private HighPriorityTodo event;

    public HighPriorityRunnable(HighPriorityTodo event) {
        this.event = event;
    }

    @Override
    public void run() {
        boolean flag = false;
        int checkedSeconds;
        int checkedMinutes;
        int checkedHour;

        while (!flag) {

            checkedSeconds = LocalDateTime.now().getSecond() - this.event.getDeadLine().getSecond();
            checkedMinutes=LocalDateTime.now().getMinute()-this.event.getDeadLine().getMinute();
            checkedHour=LocalDateTime.now().getHour()-this.event.getDeadLine().getHour();
            if(checkedHour>=0){
                if(checkedMinutes>=0){
                    if (checkedSeconds > 0) {
                        flag = true;
                        System.out.println("Deadline for item { " + this.event.getContent() + "} has expired!");
                    }
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
