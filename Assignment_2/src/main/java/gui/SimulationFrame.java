package gui;

import business.logic.SimulationManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.DocumentListener;

public class SimulationFrame extends JFrame{
    public JFrame frame;
    protected JPanel containerPanel, arrivalTimePanel, servTimePanel, animationsPanel, inputPanel, outputPanel, fillerPanelPeCareNuAmApucatSaLFac, simulationPanel, startPanel;
    protected JButton startBtn;
    protected static JTextArea liveCount;
    protected static JLabel fillerLabelProbabilDone;
    protected JLabel timeText, arrivalTimeLbl, minArrTimeLbl, maxArrTimeLbl, servingTimeLbl, minServTimeLbl, maxServTimeLbl, queuesNbLbl, clientsNbLbl, durationLbl, logLbl, peakTimeLbl, peakTimeClientsNbLbl, avgWaitingTimeLbl, avgServingTimeLbl;
    protected JTextField minArrTimeInput, maxArrTimeInput, minServTimeInput, maxServTimeInput, queuesNbInput, clientsNbInput, durationInput;
    public JTextField peakTimeOutput;
    public JTextField avgWaitingTimeOutput;
    public JTextField avgServTimeOutput;
    public JTextField peakTimeClientsNbOuput;
    protected JScrollPane scrollPane;
    protected static JTextArea outputPtScroll;

    private void contPanel() {
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(1, 3));

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1));
        inputPanel.setBackground(new Color(255, 228, 196));
        containerPanel.add(inputPanel);

        outputPanel = new JPanel();
        outputPanel.setLayout(null);
        outputPanel.setBackground(new Color(255, 235, 205));
        containerPanel.add(outputPanel);

        animationsPanel = new JPanel();
        animationsPanel.setLayout(null);
        animationsPanel.setBackground(new Color(255, 218, 185));
        containerPanel.add(animationsPanel);

        containerPanel.setVisible(true);
        frame.setContentPane(containerPanel);
    }

    private void fillerPanel() {
        fillerLabelProbabilDone = new JLabel("");
        fillerLabelProbabilDone.setFont(new Font("Comic Sans MS", Font.ITALIC, 21));
        fillerLabelProbabilDone.setBounds(150, 30, 120, 60);
        fillerPanelPeCareNuAmApucatSaLFac.add(fillerLabelProbabilDone);
    }

    private void inPanel() {
        arrivalTimePanel = new JPanel();
        arrivalTimePanel.setBackground(new Color(255, 228, 196));
        arrivalTimePanel.setLayout(null);

        servTimePanel = new JPanel();
        servTimePanel.setBackground(new Color(254, 215, 196));
        servTimePanel.setLayout(null);

        simulationPanel = new JPanel();
        simulationPanel.setBackground(new Color(255, 228, 196));
        simulationPanel.setLayout(null);

        fillerPanelPeCareNuAmApucatSaLFac = new JPanel();
        fillerPanelPeCareNuAmApucatSaLFac.setBackground(new Color(254, 215, 196));
        fillerPanelPeCareNuAmApucatSaLFac.setLayout(null);

        startPanel = new JPanel();
        startPanel.setBackground(new Color(255, 228, 196));
        startPanel.setLayout(null);

        arrPanel();
        inputPanel.add(arrivalTimePanel);
        servPanel();
        inputPanel.add(servTimePanel);
        simDataPanel();
        inputPanel.add(simulationPanel);
        fillerPanel();
        inputPanel.add(fillerPanelPeCareNuAmApucatSaLFac);
        startBtnPanel();
    }

    private void animPanel() {
        timeText = new JLabel("Time:");
        timeText.setFont(new Font("Consolas", Font.BOLD, 16));
        timeText.setBounds(20, 30, 50, 20);
        animationsPanel.add(timeText);

        liveCount = new JTextArea(1, 10);
        liveCount.setFont(new Font("Consolas", Font.BOLD, 16));
        liveCount.setBounds(70, 30, 50, 20);
        liveCount.setBackground(new Color(255, 218, 185));
        liveCount.setEditable(false);
        animationsPanel.add(liveCount);
    }

    public static void updateScroll(String newLog){
        outputPtScroll.append(newLog);
        outputPtScroll.setCaretPosition(outputPtScroll.getDocument().getLength());
        outputPtScroll.update(outputPtScroll.getGraphics());
    }

    public static void updateTime(int time) {
        liveCount.setText(String.valueOf(time + 1));
        liveCount.update(liveCount.getGraphics());
        if(time == 0) {
            fillerLabelProbabilDone.setText("Running...");
            fillerLabelProbabilDone.setForeground(Color.YELLOW);
        }
        fillerLabelProbabilDone.update(fillerLabelProbabilDone.getGraphics());
    }

    private void arrPanel() {
        Font fontMare = new Font("Consolas", Font.BOLD, 13);
        Font fontMic = new Font("Consolas", Font.BOLD, 12);

        arrivalTimeLbl = new JLabel("Arrival time");
        arrivalTimeLbl.setFont(fontMare);
        arrivalTimeLbl.setHorizontalAlignment(SwingConstants.CENTER);
        arrivalTimeLbl.setVerticalAlignment(SwingConstants.CENTER);
        arrivalTimeLbl.setBounds(10, 10, 347, 20);
        arrivalTimePanel.add(arrivalTimeLbl);

        minArrTimeLbl = new JLabel("Minimum: ");
        minArrTimeLbl.setFont(fontMic);
        minArrTimeLbl.setBounds(30, 40, 120, 20);
        arrivalTimePanel.add(minArrTimeLbl);

        minArrTimeInput = new JTextField();
        minArrTimeInput.setBounds(135, 40, 100, 20);
        minArrTimeInput.setColumns(10);
        minArrTimeInput.setFont(fontMic);
        arrivalTimePanel.add(minArrTimeInput);

        maxArrTimeLbl = new JLabel("Maximum: ");
        maxArrTimeLbl.setFont(fontMic);
        maxArrTimeLbl.setBounds(30, 70, 120, 20);
        arrivalTimePanel.add(maxArrTimeLbl);

        maxArrTimeInput = new JTextField();
        maxArrTimeInput.setBounds(135, 70, 100, 20);
        maxArrTimeInput.setColumns(10);
        maxArrTimeInput.setFont(fontMic);
        arrivalTimePanel.add(maxArrTimeInput);
    }

    private void servPanel() {
        Font fontMare = new Font("Consolas", Font.BOLD, 14);
        Font fontMic = new Font("Consolas", Font.BOLD, 12);

        servingTimeLbl = new JLabel("Serving time");
        servingTimeLbl.setFont(fontMare);
        servingTimeLbl.setHorizontalAlignment(SwingConstants.CENTER);
        servingTimeLbl.setVerticalAlignment(SwingConstants.CENTER);
        servingTimeLbl.setBounds(5, 10, 347, 20);
        servTimePanel.add(servingTimeLbl);

        minServTimeLbl = new JLabel("Minimum: ");
        minServTimeLbl.setFont(fontMic);
        minServTimeLbl.setBounds(30, 40, 120, 20);
        servTimePanel.add(minServTimeLbl);

        minServTimeInput = new JTextField();
        minServTimeInput.setBounds(135, 40, 100, 20);
        minServTimeInput.setColumns(10);
        minServTimeInput.setFont(fontMic);
        servTimePanel.add(minServTimeInput);

        maxServTimeLbl = new JLabel("Maximum: ");
        maxServTimeLbl.setFont(fontMic);
        maxServTimeLbl.setBounds(30, 70, 120, 20);
        servTimePanel.add(maxServTimeLbl);

        maxServTimeInput = new JTextField();
        maxServTimeInput.setBounds(135, 70, 100, 20);
        maxServTimeInput.setColumns(10);
        maxServTimeInput.setFont(fontMic);
        servTimePanel.add(maxServTimeInput);
    }

    private void simDataPanel() {
        Font fontMare = new Font("Consolas", Font.BOLD, 12);
        Font fontMic = new Font("Consolas", Font.BOLD, 10);

        queuesNbLbl = new JLabel("Number of queues");
        queuesNbLbl.setFont(fontMare);
        queuesNbLbl.setBounds(20, 20, 250, 20);
        simulationPanel.add(queuesNbLbl);

        queuesNbInput = new JTextField();
        queuesNbInput.setFont(fontMic);
        queuesNbInput.setBounds(250, 20, 75, 20);
        simulationPanel.add(queuesNbInput);

        clientsNbLbl = new JLabel("Number of clients");
        clientsNbLbl.setFont(fontMare);
        clientsNbLbl.setBounds(20, 50, 250, 20);
        simulationPanel.add(clientsNbLbl);

        clientsNbInput = new JTextField();
        clientsNbInput.setFont(fontMic);
        clientsNbInput.setBounds(250, 50, 75, 20);
        simulationPanel.add(clientsNbInput);

        durationLbl = new JLabel("Simulation duration");
        durationLbl.setFont(fontMare);
        durationLbl.setBounds(20, 80, 250, 20);
        simulationPanel.add(durationLbl);

        durationInput = new JTextField();
        durationInput.setFont(fontMic);
        durationInput.setBounds(250, 80, 75, 20);
        simulationPanel.add(durationInput);
    }

    private void startBtnPanel() {
        startBtn = new JButton("Start");
        startBtn.setBackground(new Color(255, 219, 224));
        startBtn.setFont(new Font("Consolas", Font.BOLD, 20));
        startBtn.setBounds(110, 30, 120, 60);
        startPanel.add(startBtn);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fillerLabelProbabilDone.setText("");
                updateTime(0);
                outputPtScroll.setText("");
                scrollPane.setViewportView(outputPtScroll);
                liveCount.setText("");
                peakTimeOutput.setText("");
                peakTimeClientsNbOuput.setText("");
                avgWaitingTimeOutput.setText("");
                avgServTimeOutput.setText("");

                try {
                    int minArrivalTime, maxArrivalTime, minServingTime, maxServingTime, nbOfServers, nbOfClients, simInterval;
                    if (Objects.equals(minArrTimeInput.getText(), "") || Objects.equals(maxArrTimeInput.getText(), "")) {
                        fillerLabelProbabilDone.setText("Eroare...");
                        fillerLabelProbabilDone.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(null, "minArrTimeInput sau maxArrTimeInput e gol!", "Oops", JOptionPane.ERROR_MESSAGE);
                    } else {
                        minArrivalTime = Integer.parseInt(minArrTimeInput.getText());
                        maxArrivalTime = Integer.parseInt(maxArrTimeInput.getText());
                        if (maxArrivalTime <= minArrivalTime) {
                            System.out.println(minArrivalTime + " " + maxArrivalTime);
                            fillerLabelProbabilDone.setText("Eroare...");
                            fillerLabelProbabilDone.setForeground(Color.RED);
                            JOptionPane.showMessageDialog(null, "maxArrivalTime must be greater than minArrivalTime!", "Oops", JOptionPane.ERROR_MESSAGE);
                        } else {

                            if (Objects.equals(minServTimeInput.getText(), "") || Objects.equals(maxServTimeInput.getText(), "")) {
                                fillerLabelProbabilDone.setText("Eroare...");
                                fillerLabelProbabilDone.setForeground(Color.RED);
                                JOptionPane.showMessageDialog(null, "minServTimeInput sau maxServTimeInput e gol!", "Oops", JOptionPane.ERROR_MESSAGE);
                            } else {

                                minServingTime = Integer.parseInt(minServTimeInput.getText());
                                maxServingTime = Integer.parseInt(maxServTimeInput.getText());

                                if (maxServingTime <= minServingTime) {
                                    fillerLabelProbabilDone.setText("Eroare...");
                                    fillerLabelProbabilDone.setForeground(Color.RED);
                                    System.out.println(minServingTime + " " + maxServingTime);
                                    JOptionPane.showMessageDialog(null, "maxServingTime must be greater than minServingTime!", "Oops", JOptionPane.ERROR_MESSAGE);
                                } else {

                                    if (Objects.equals(queuesNbInput.getText(), "") || Objects.equals(clientsNbInput.getText(), "") || Objects.equals(durationInput.getText(), "")) {
                                        fillerLabelProbabilDone.setText("Eroare...");
                                        fillerLabelProbabilDone.setForeground(Color.RED);
                                        JOptionPane.showMessageDialog(null, "queuesNbInput sau clientsNbInput sau durationInput e gol!", "Oops", JOptionPane.ERROR_MESSAGE);
                                    } else {



                                        nbOfServers = Integer.parseInt(queuesNbInput.getText());
                                        nbOfClients = Integer.parseInt(clientsNbInput.getText());
                                        simInterval = Integer.parseInt(durationInput.getText());

                                        SimulationManager simManager = new SimulationManager(minArrivalTime, maxArrivalTime, minServingTime, maxServingTime, nbOfServers, nbOfClients, simInterval);
                                        System.out.println("Start");
                                        Thread thread = new Thread(simManager);
                                        thread.start();

                                        thread.join();

                                        System.out.println("End");

                                        fillerLabelProbabilDone.setText("Done");
                                        fillerLabelProbabilDone.setForeground(Color.GREEN);

                                        DecimalFormat numberFormat = new DecimalFormat("#.##");
                                        peakTimeOutput.setText(Integer.toString(simManager.getPeakMin()));
                                        peakTimeClientsNbOuput.setText(Integer.toString(simManager.getPeakTimeClients()));
                                        avgWaitingTimeOutput.setText(numberFormat.format(simManager.getAverageWaitTime()));
                                        avgServTimeOutput.setText(numberFormat.format(simManager.getAverageServiceTime()));
                                    }
                                }
                            }
                        }
                    }
                } catch (FileNotFoundException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        inputPanel.add(startPanel);
    }

    private void outPanel() {
        Font fontMare = new Font("Consolas", Font.BOLD, 16);
        Font fontMic = new Font("Consolas", Font.BOLD, 12);

        logLbl = new JLabel("Log of events");
        logLbl.setFont(fontMare);
        logLbl.setBounds(20, 30, 347, 20);
        outputPanel.add(logLbl);

        outputPtScroll = new JTextArea();
        outputPtScroll.setEditable(false);
        outputPtScroll.setBackground(new Color(255, 215, 196));
        outputPtScroll.setBounds(150, 250, 100, 100);
        outputPtScroll.setWrapStyleWord(true);
        outputPtScroll.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputPanel.add(outputPtScroll);

        scrollPane = new JScrollPane(outputPtScroll);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(15, 70, 317, 300);
        scrollPane.setViewportView(outputPtScroll);
        outputPanel.add(scrollPane);

        peakTimeLbl = new JLabel("Peak minute");
        peakTimeLbl.setFont(fontMic);
        peakTimeLbl.setBounds(30, 400, 150, 20);
        outputPanel.add(peakTimeLbl);

        peakTimeOutput = new JTextField();
        peakTimeOutput.setEditable(false);
        peakTimeOutput.setFont(fontMic);
        peakTimeOutput.setBounds(200, 400, 75, 20);
        outputPanel.add(peakTimeOutput);

        peakTimeClientsNbLbl = new JLabel("Clients at peak min");
        peakTimeClientsNbLbl.setFont(fontMic);
        peakTimeClientsNbLbl.setBounds(30, 430, 150, 20);
        outputPanel.add(peakTimeClientsNbLbl);

        peakTimeClientsNbOuput = new JTextField();
        peakTimeClientsNbOuput.setEditable(false);
        peakTimeClientsNbOuput.setFont(fontMic);
        peakTimeClientsNbOuput.setBounds(200, 430, 75, 20);
        outputPanel.add(peakTimeClientsNbOuput);

        avgWaitingTimeLbl = new JLabel("Avg waiting time");
        avgWaitingTimeLbl.setFont(fontMic);
        avgWaitingTimeLbl.setBounds(30, 460, 150, 20);
        outputPanel.add(avgWaitingTimeLbl);

        avgWaitingTimeOutput = new JTextField();
        avgWaitingTimeOutput.setEditable(false);
        avgWaitingTimeOutput.setFont(fontMic);
        avgWaitingTimeOutput.setBounds(200, 460, 75, 20);
        outputPanel.add(avgWaitingTimeOutput);

        avgServingTimeLbl = new JLabel("Avg serving time");
        avgServingTimeLbl.setFont(fontMic);
        avgServingTimeLbl.setBounds(30, 490, 150, 20);
        outputPanel.add(avgServingTimeLbl);

        avgServTimeOutput = new JTextField();
        avgServTimeOutput.setEditable(false);
        avgServTimeOutput.setFont(fontMic);
        avgServTimeOutput.setBounds(200, 490, 75, 20);
        outputPanel.add(avgServTimeOutput);
    }

    private void init() {
        frame = new JFrame("Queues management system");
        frame.setSize(1041, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contPanel();
        inPanel();
        outPanel();
        animPanel();
    }

    public SimulationFrame() {
        init();
    }

}
