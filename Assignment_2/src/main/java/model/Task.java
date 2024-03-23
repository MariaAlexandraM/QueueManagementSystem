package model;

public class Task implements Comparable<Task> {
    protected int ID;
    protected int serviceTime;
    protected int arrivalTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Task t) {
        if (this.arrivalTime < t.arrivalTime) {
            return -1;
        }
        if (this.arrivalTime > t.arrivalTime) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return ("(" + this.getID() + "," + this.getArrivalTime() + "," + this.getServiceTime() + ")");
    }

}
