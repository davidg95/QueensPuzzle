/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.davidg95.queenspuzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
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
        try {
            int n;
            if (args.length > 0) {
                n = Integer.parseInt(args[0]);
            } else {
                if (GraphicsEnvironment.isHeadless()) {

                    System.out.println("Enter value for n:");
                    n = Integer.parseInt(new Scanner(System.in).nextLine());
                } else {
                    n = Integer.parseInt(JOptionPane.showInputDialog("Enter value for n"));
                }
            }
            if (n < 1) {
                System.out.println("Must be value greater than 0!");
                if (!GraphicsEnvironment.isHeadless()) {
                    JOptionPane.showMessageDialog(null, "Must be value greater than 0!");
                }
                return;
            }
            b = new Board(n);
            long start = new Date().getTime();
            System.out.println("Searching with size " + n + "...");
            boolean result = b.start();
            long end = new Date().getTime();
            if (result) {
                System.out.println("Found!");
                b.printState();
                long time = end - start;
                double s = time / 1000D;
                System.out.println("Complete in " + s + "s");

                if (!GraphicsEnvironment.isHeadless()) {
                    JFrame frame = new JFrame("Queens Puzzle");
                    frame.setSize(1000, 1000);
                    JPanel panel = new MyPanel();
                    panel.setSize(1000, 1000);
                    frame.add(panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            } else {
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

    public static class MyPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.BLACK);
            int n = b.getSize();
            int size = 1000 / n;
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
            repaint();
        }
    }

}
