# Queue Management System

## Description
A multi-threaded queue management system that simulates customer flow in a store.
Tasks (customers) are assigned dynamically to queues based on the Shortest Processing Time strategy.

The system provides a graphical interface for real-time monitoring and generates statistics on queue performance.

## Purpose
The project allows the user to set up the simulating conditions.  

![image](https://github.com/user-attachments/assets/d9b961b7-5239-4e36-b529-c84b2cad2f17)

The Queue Management System takes user input for the following parameters:
- Arrival time (Min & Max)
- Serving time (Min & Max)
- Number of queues
- Number of clients
- Simulation duration

The system then automatically generates clients with random arrival and serving times based on the given ranges.

During the simulation, real-time data is calculated and displayed, including:
- Log of events (clients waiting & assigned to queues)
- Peak minute (time with most clients in queues)
- Number of clients at peak
- Average waiting time
- Average serving time

![image](https://github.com/user-attachments/assets/bc3e6e83-3ac9-40ef-98ed-3365b2e3a3bb)

## Modules
The following packages can be found under `src`, in `Assignment2`:
- `business` and `logic`, responsible for dispatching the tasks and running the app (`Main.java`)
- `gui`, responsible for creating the user interface
- `model`, containing the Server and Task classes

The project also contains:
- [`Documentatie_Assignment_2.pdf`](Documentatie_Assignment_2.pdf)
- .txt logs, automatically generated during the execution using the required parameters for my assignment
