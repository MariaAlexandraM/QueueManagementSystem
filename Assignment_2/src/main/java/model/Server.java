package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Server implements Runnable {
    private BlockingQueue<Task> clientsQueue;
    private AtomicInteger waitingPeriod;

    public Server(int maxTasksPerServer) {
        clientsQueue = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        waitingPeriod = new AtomicInteger(0);
    }

    public boolean addTask(Task newTask) { // add task to queue + increment waiting period
        if (!clientsQueue.offer(newTask)) {
            System.out.println("Queue is full, " + newTask + " was not added");
            return false;
        }
        waitingPeriod.addAndGet(newTask.getServiceTime());
        return true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!clientsQueue.isEmpty()) {
                    Task firstTask = clientsQueue.peek();
                    int serviceTime = firstTask.getServiceTime();
                    for(int i = 0; i < serviceTime; i++) {
                        Thread.sleep(1000);
                        firstTask.setServiceTime(clientsQueue.peek().getServiceTime() - 1);
                    }
                    waitingPeriod.getAndDecrement();
                    clientsQueue.take();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public BlockingQueue<Task> getTasks(){
        return this.clientsQueue;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

}
