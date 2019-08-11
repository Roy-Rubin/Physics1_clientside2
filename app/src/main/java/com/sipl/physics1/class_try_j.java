package com.sipl.physics1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.lang.Object;


public class class_try_j {


}


/*******************************************************************************
 * Copyright (c) to ...
 *
 * File dedicated to the initiation of the matlab engine from a java file.
 *
 ******************************************************************************/



/*


// package org.jbox2d.testbed.framework.j2d;

import java.awt.BorderLayout;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

import com.mathworks.engine.MatlabEngine;
import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.AbstractTestbedController.MouseBehavior;
import org.jbox2d.testbed.framework.AbstractTestbedController.UpdateBehavior;

import static org.jbox2d.testbed.framework.j2d.TestbedSidePanel.setNewImageLabel;

*/
/**
 * The entry point for the testbed application
 *
 * @author Daniel Murphy
 *//*

public class TestbedMain {
    // private static final Logger log = LoggerFactory.getLogger(TestbedMain.class);

    // variable for the engine.
    // requires the addition of the .JAR file
    public static MatlabEngine eng;

    //
    //
    public static String jsonPath;


    private static void InitializeEngine() {
        try{
            System.out.println("Initializing Matlab Engine ...");  //<-- do we need this print ?

            eng = MatlabEngine.startMatlab(); */
/*!!!*//*


            System.out.println("Initialization Completed");		//<-- do we need this print ?
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void InitializeDetector() {

        // messages are printed from matlab
        try {
            // Change directory and evaluate function
            String matlabFunDir = "C:\\Users\\Danielle\\Desktop\\ObjectMapper";
            eng.eval("cd '" + matlabFunDir + "'");
            eng.feval(0, "intializeDetector");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static String RunMatlab(String imagePath, boolean showDetection, boolean showMapping, double th) {
        // messages are printed from matlab
        String jsonPath = "";

        try {

            // Change directory and evaluate function
            String matlabFunDir = "C:\\Users\\Danielle\\Desktop\\ObjectMapper";
            eng.eval("cd '" + matlabFunDir + "'");

            Object result = eng.feval(1, "Detect_Map", imagePath, th, showDetection, showMapping);

            jsonPath = (String) result;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return jsonPath;
    }

    public static void main(String[] args) {
        // try {
        // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        // } catch (Exception e) {
        // log.warn("Could not set the look and feel to nimbus. "
        // + "Hopefully you're on a mac so the window isn't ugly as crap.");
        // }

        InitializeEngine();
        InitializeDetector();

        TestbedModel model = new TestbedModel();
        final AbstractTestbedController controller = new TestbedController(model,
                UpdateBehavior.UPDATE_CALLED, MouseBehavior.NORMAL);
        TestPanelJ2D panel = new TestPanelJ2D(model, controller);
        model.setPanel(panel);
        model.setDebugDraw(new DebugDrawJ2D(panel, true));
        TestList.populateModel(model);

        JFrame testbed = new JFrame();
        testbed.setTitle("JBox2D Testbed");
        testbed.setLayout(new BorderLayout());
        TestbedSidePanel side = new TestbedSidePanel(model, controller);

        testbed.add(panel, "Center");

        panel.add(chooser);
        String cd = System.getProperty("user.dir");
        File defaultDir = new File(cd + "\\Images");
        chooser.setCurrentDirectory(defaultDir);
        chooser.showOpenDialog(null);

        setNewJsonPath(false, false, 98);
        setNewImageLabel();
        jsonPath = "C:\\Users\\Danielle\\Downloads\\jbox2d\\Images\\outputExp.json";

        testbed.add(new JScrollPane(side), "East");
        testbed.pack();
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(System.getProperty("java.home"));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                controller.playTest(0);
                controller.start();
            }
        });
    }

    public static void setNewJsonPath(boolean showDetection, boolean showMapping, int threshold){
        jsonPath = RunMatlab(getImagePath(), showDetection, showMapping, (double)threshold/100);
//        jsonPath = "C:\\Users\\Danielle\\Downloads\\jbox2d\\Images\\outputExp.json";
    }

    public static String getImagePath(){
        return chooser.getSelectedFile().getPath();
//        return "C:\\Users\\Danielle\\Downloads\\jbox2d\\Images\\Real_301.jpg";
    }

    public static JFileChooser getChooser(){
        return chooser;
    }

    public static String getJsonPath(){
        return jsonPath;
    }
}
*/




