package dev.njc.androidgui;

import javax.swing.*;
import dev.njc.androidgui.gui.MainAndroidApp;

public class App {
    // for test purposes
    public String getGreeting() {
        return "Hello World!";
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MainAndroidApp("Neil Jason Cañete", "0701"));                 }
}

