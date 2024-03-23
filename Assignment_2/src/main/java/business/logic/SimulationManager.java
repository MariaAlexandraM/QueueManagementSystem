package business.logic;

import model.Server;
import model.Task;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static gui.SimulationFrame.*;

public class SimulationManager implements Runnable {

    // data read from the user
    protected int simulationLimit;
    protected int minProcessingTime, maxProcessingTime;
    protected int numberOfServers, numberOfClients;
    protected int minArrivalTime, maxArrivalTime;

    // entity responsible w queue management and client distribution
    protected Scheduler scheduler;
    protected List<Task> generatedTasks = new ArrayList<>();

    // altele
    protected static int peakMin = 0, peakTimeClients = 0;
    protected PrintWriter printWriter = new PrintWriter("Log.txt");
    public static double averageWaitTime = 0.0f;
    public static double averageServeTime = 0.0f;

    public SimulationManager(int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime,
                             int numberOfServers, int numberOfClients, int simulationLimit) throws FileNotFoundException {
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.simulationLimit = simulationLimit;
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        generateNRandomTasks(numberOfClients);
    }

    private void generateNRandomTasks(int N) {
        for (int i = 0; i < N; i++) {
            Random random = new Random();
            int ID = i + 1;
            int arrivalTime = (random.nextInt(maxArrivalTime - minArrivalTime)) + minArrivalTime;
            int servingTime = (random.nextInt(maxProcessingTime - minProcessingTime)) + minProcessingTime;
            Task client = new Task(ID, arrivalTime, servingTime);
            generatedTasks.add(client);
        }
        Collections.sort(this.generatedTasks); // ordoneaza in fctie de arrivalTime
    }

    @Override
    public void run() {
        int currentTime = 0;
        while (currentTime < simulationLimit) {
            List<Task> removed = new ArrayList<>(); // pun intr-o lista de toBeRemoved
            for (Task i : generatedTasks) { // in toate task-urile adica clientii waiting,
                if (i.getArrivalTime() == currentTime) { // daca le-a ajuns arrival time u,
                    scheduler.dispatchTask(i); // stabileste in ce coada baga tasku 'i', si face asta cu shortestTime strategy
                    averageServeTime += i.getServiceTime();  // aici incep sa adun service time u
                    removed.add(i); // adaug tasku care trebe removed
                }
            }
            generatedTasks.removeAll(removed); // le sterg abia dupa ce am gatat de parcurs toate in timpu curent, gen la momentu currentTime
            removed.clear(); // o reinitializez dupa da vr sa fiu sigura sincer ca am avut prea multe probleme cu proiectu asta

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            updateScroll(printTasks(scheduler.getServers(), currentTime, printWriter));
            updateTime(currentTime);
            peakTime(scheduler.getServers(), currentTime);
            averageWaitTime += scheduler.avgWait();
            currentTime++;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        peakTime(scheduler.getServers(), currentTime);
        averageWaitTime = averageWaitTime / (double) numberOfClients;
        averageServeTime = averageServeTime / (double) numberOfClients;

        printWriter.println("+------------------------------------------------+");
        printWriter.println("|              Simulation results                |");
        printWriter.println("|    peak min: " + peakMin + "                                 |");
        printWriter.println("|    peak nb of clients: " + peakTimeClients + "                       |");
        printWriter.println("|    avg waiting time: " + String.format("%3.2f", averageWaitTime) + "                      |");
        printWriter.println("|    avg serving time: " + String.format("%3.2f", averageServeTime) + "                      |");
        printWriter.println("+------------------------------------------------+");
        printWriter.close();
    }

    public String printTasks(List<Server> servers, int currentTime, PrintWriter printWriter) {
        int queueNb = 1;
        StringBuilder printedOutput = new StringBuilder();
        printedOutput.append("Time: ").append(currentTime).append("\n");
        printWriter.println("--------------------------------------------------\nTime: " + currentTime);
        printedOutput.append("Waiting: ");
        printWriter.print("Waiting: ");
        for(Task i : generatedTasks) {
            printedOutput.append(i).append(" ");
            printWriter.print(i + " ");
        }
        printedOutput.append("\n");
        printWriter.print("\n");
        for (Server i : servers) {
            BlockingQueue<Task> tasks = i.getTasks();
            if (tasks.isEmpty()) {
                printedOutput.append("Queue ").append(queueNb).append(" is empty\n");
                printWriter.print("Queue " + queueNb + " is empty\n");
            } else {
                printedOutput.append("Queue ").append(queueNb).append(": ");
                printWriter.print("Queue " + queueNb +": ");
                for (Task j : tasks) {
                    printedOutput.append(j).append(" ");
                    printWriter.print(j + " ");
                }
                printedOutput.append("\n");
                printWriter.println("");
            }
            queueNb++;
        }
        printedOutput.append("\n");
        printWriter.println("--------------------------------------------------\n");

        return printedOutput.toString();
    }

    public void peakTime(List<Server> serverList, int currentTime) {
        int peakClients = 0;
        for (Server i : serverList) {
            peakClients += i.getTasks().size();
        }
        if (peakClients > peakTimeClients) {
            peakMin = currentTime;
            peakTimeClients = peakClients;
        }
    }

    public int getPeakMin() {
        return peakMin;
    }

    public int getPeakTimeClients() {
        return peakTimeClients;
    }

    public double getAverageWaitTime() {
        return averageWaitTime;
    }

    public double getAverageServiceTime() {
        return averageServeTime;
    }

}
