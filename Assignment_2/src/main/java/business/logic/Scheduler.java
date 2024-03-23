package business.logic;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    protected List<Server> servers = new ArrayList<Server>();
    protected int maxNoServers;
    protected int maxTasksPerServer;
    protected int currentTime;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        currentTime = 0;
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        for(int i = 0; i < maxNoServers; i++) {
            Server serv = new Server(maxTasksPerServer);
            Thread t = new Thread(serv);
            t.start();
            servers.add(serv);
        }
    }

    public List<Server> getServers() {
        return servers;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        int queueNb = 0;
        for(Server i : servers) {
            ++queueNb;
            string.append("Queue ").append(queueNb).append(": ");
            for(Task j : i.getTasks()) {
                string.append(j.toString()).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    public void dispatchTask(Task newTask) {
        int minWaitingPeriod = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).getWaitingPeriod().get() < minWaitingPeriod) {
                minWaitingPeriod = servers.get(i).getWaitingPeriod().get();
                index = i;
            }
        }

        if(index == -1) {
            System.out.println("No tasks dispatched");
            return;
        }

        if(servers.get(index).addTask(newTask)) {
            int waitingPeriod = servers.get(index).getWaitingPeriod().get(); // waiting period la coada specifica
            int newWait = waitingPeriod + newTask.getServiceTime();
            AtomicInteger atomicWaitingPeriod = new AtomicInteger();
            atomicWaitingPeriod.set(newWait);
            servers.get(index).setWaitingPeriod(atomicWaitingPeriod); // adaug service time u de la tasku nou adaugat la waiting periodu la coada asta, da am un drum anevoios de convertit in atomic integer
        }
    }

    public double avgWait() {
        double totalTime = 0;
        for(Server i : servers) {
            for(Task j : i.getTasks()) {
                totalTime += j.getServiceTime();
            }
        }
        return totalTime / servers.size();
    }

}
