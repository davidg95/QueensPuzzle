/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.davidg95.queenspuzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class for the Queen's Puzzle.
 *
 * @author David
 */
public class QueensPuzzle {

    private static Board b;

    public static final String QUEEN_CHAR = "\u25A0";
    public static final String BLANK_CHAR = "\u25A1";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("---------------------------------------------");
        System.out.println("Queens Puzzle v1.0");
        System.out.println("Created by David Grant");
        System.out.println("GitHub repositry can be viewed at https://github.com/davidg95/QueensPuzzle.git");
        System.out.println("---------------------------------------------");
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        try {
            int n;
            if (args.length > 0) { //Check if n was passed in parameters.
                n = Integer.parseInt(args[0]);
            } else {
                //Get the value for n from the user.
                if (GraphicsEnvironment.isHeadless()) { //Check if there is a graphics environment.
                    System.out.println("Enter value for n:");
                    n = Integer.parseInt(new Scanner(System.in).nextLine());
                } else {
                    n = Integer.parseInt(JOptionPane.showInputDialog("Enter value for n"));
                }
            }
            if (n < 1) { //Check a valid number was entered.
                System.out.println("Must be value greater than 0!");
                if (!GraphicsEnvironment.isHeadless()) {
                    JOptionPane.showMessageDialog(null, "Must be value greater than 0!");
                }
                return;
            }
            b = new Board(n);
            System.out.println("Searching with size " + n + "...");
            long start = new Date().getTime();
            final Runnable run = () -> {
                try {
                    String anim = "|/-\\";
                    int i = 0;
                    while (true) {
                        String data = "\r" + anim.charAt(i);
                        System.out.write(data.getBytes());
                        Thread.sleep(100);
                        i++;
                        if (i == anim.length()) {
                            i = 0;
                        }
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(QueensPuzzle.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            Thread thread = new Thread(run);
            thread.start();
            boolean result = b.start(); //Start the search
            thread.stop();
            long end = new Date().getTime();
            if (result) {
                //If the result was found.
                System.out.println("Found!");
                b.printState();
                long time = end - start;
                double s = time / 1000D;
                System.out.println("Complete in " + s + "s");

                //Display the solution.
                if (!GraphicsEnvironment.isHeadless()) {
                    JFrame frame = new JFrame("Queens Puzzle");
                    frame.setSize(900, 900);
                    JPanel panel = new MyPanel();
                    panel.setSize(800, 800);
                    frame.add(panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    SwingUtilities.invokeLater(() -> {
                        frame.setVisible(true);
                    });
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(frame, "Found in " + s + "s");
                    });
                }
            } else {
                //If the result was not found.
                System.out.println("No solution found!");
                if (!GraphicsEnvironment.isHeadless()) {
                    JOptionPane.showMessageDialog(null, "No solution found!");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            if (!GraphicsEnvironment.isHeadless()) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Panel for displaying the result.
     */
    public static class MyPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.BLACK);
            int n = b.getSize();
            int size = 800 / n;
            int currentx = 0;
            int currenty = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    boolean q = b.getBoard()[i][j];
                    if (q) {
                        g.fillRect(currentx, currenty, size, size);
                    } else {
                        g.drawRect(currentx, currenty, size, size);
                    }
                    currentx += size;
                }
                currenty += size;
                currentx = 0;
            }
        }
    }

}
