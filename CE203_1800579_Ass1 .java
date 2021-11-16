package Assignment1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

// Incomplete ID class for CE203 Assignment 1
// Date: 12/10/2020
// Author: f.Doctor
class ID implements Comparable<ID>{

    // id attribute
    int id = 000000;

    // constructor should input an ID as a String or int and set it to the attribute id - to be modified
    public ID(int id)
    {
        this.id = id;
    }

    // gets a stored ID
    public int getID() {
        return id;
    }

    // sets the input parameter to an ID this can be modified to input a string in which case you will need to convert
    // the parameter to an int
    // honestly not sure how to properly implement/ use this
    public void setID(String inputID) {

        try { id = Integer.parseInt(inputID);
        }
        catch (NullPointerException e1)
        {
            System.out.println("Input = null");
        }
        catch (NumberFormatException e1) {
            System.out.println("Input isn't a legal input");
        }
    }

    @Override
    // method used for comparing ID objects based on stored ids, you need to complete the method
    // using the getID() method to compare the integers in each id and returning a number
    public int compareTo(ID o) {
        if(this.getID() == o.getID()) return 1;
        else if (this.getID() < o.getID()) return -1;
        else return 0;
    }

    // outputs a string representation of the object
    public String toString()
    {
        return ( id +"\n");
    }

    // main to execute program
    public static void main(String[] args){
        new DrawGUI().setVisible(true);
    }

    public static class DrawGUI extends JFrame{
        JPanel panel, panel2, panel3;                    // JPanel variables
        JTextField tField, tField1, tField2, tField3 ;   // text fields to allow user input
        JTextArea tArea;                                 // text area, isn't editable. For output
        JLabel label, label1, label2, label3;            // labels for the text fields
        ArrayList<ID> idList = new ArrayList<>();      // Arraylist to hold all ID's
        ArrayList <ID> b = new ArrayList<>();          // new empty arraylist which all the ID's matching "id" in the removeID(ID id) method will be put into

        // display method
        // loop through all elements and append to StringBuilder out and then return out
        public String display(){
            StringBuilder out = new StringBuilder();
            for (ID id : idList) {
                out = new StringBuilder(id + out.toString() + " ");
            }
            return out.toString();
        }

        //add ID method
        public void addID(int id){ idList.add(new ID(id));}

        // Remove id method, supplied with 1 argument id, removes all instances in Arraylist
        // loop through the array checking if any elements at index given match id then all items in b are removed from id
        public void removeID(ID id){
            for (ID value : idList) {
                if (value.compareTo(id) > 0) {
                    b.add(value);
                }
            }
            idList.removeAll(b);
        }

        //sorts the Array, to be displayed in ascending order
        public void sortID(){
            idList.sort(Collections.reverseOrder());
        }


        // Constructor Drawing the GUI with the panels and buttons
        public DrawGUI(){
            setTitle("ID Inventory");
            setSize(600, 400);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //set values for panels
            panel = new JPanel();
            panel2 = new JPanel();
            panel3 = new JPanel();

            // adding the panels to the JFrame
            add(panel, BorderLayout.SOUTH);
            add(panel2, BorderLayout.NORTH);
            add(panel3, BorderLayout.CENTER);

            //Declaring and setting valued for buttons
            JButton display = new JButton("Display");
            JButton addID = new JButton("Add ID");
            JButton removeID = new JButton("Remove ID");
            JButton sortID = new JButton("Sort ID's");
            JButton clear = new JButton("Clear list");

            //set values for panels and area
            tField = new JTextField(10);
            tArea = new JTextArea(15,30);
            tArea. setEditable(false);

            // for the rgb values
            tField1 = new JTextField(3);
            tField2 = new JTextField(3);
            tField3 = new JTextField(3);

            //labels
            label = new   JLabel("Enter ID's");
            label1 = new   JLabel("R");
            label2 = new   JLabel("G");
            label3 = new   JLabel("B");

            //adding the buttons to the panel
            panel.add(display);
            panel.add(addID);
            panel.add(removeID);
            panel.add(sortID);
            panel.add(clear);

            // adding the labels and corresponding text fields
            panel2.add(label);
            panel2.add(tField);

            panel2.add(label1);
            panel2.add(tField1);

            panel2.add(label2);
            panel2.add(tField2);

            panel2.add(label3);
            panel2.add(tField3);

            //adding the text area
            panel3.add(tArea);

            //giving buttons action listeners and suitable id's
            display.addActionListener(new DrawGUI.ButtonHandler(this, 1));
            addID.addActionListener(new DrawGUI.ButtonHandler(this, 2));
            removeID.addActionListener(new DrawGUI.ButtonHandler(this, 3));
            sortID.addActionListener(new DrawGUI.ButtonHandler(this, 4));
            clear.addActionListener(new DrawGUI.ButtonHandler(this, 5));
        }

        class ButtonHandler implements ActionListener {
            DrawGUI theApp;
            int action;

            ButtonHandler(DrawGUI app, int action) {
                this.theApp = app;
                this.action = action;
            }

            @Override
            //checks for which action was performed
            public void actionPerformed(ActionEvent e) {


                if (this.action == 1) {
                    //display ID's
                    int[] rgb = new int[3];
                    // set []rgb to the values in the text fields
                    try {
                        int r = Integer.parseInt(tField1.getText());
                        int g = Integer.parseInt(tField2.getText());
                        int b = Integer.parseInt(tField3.getText());
                        rgb[0] = r; rgb[1] = g; rgb[2] = b;
                    }catch(NumberFormatException e1){
                        tArea.setText("RGB values need to be numbers");}

                    // Loop through the array rgb to check if any input out of range
                    // set to black as requested, although the colour is automatically black
                    // if the rgb values in range set foreground to values
                    for (int i: rgb ) {
                        if (i > 255 || i < 0) {
                            tArea.setForeground(new Color(0, 0, 0));
                            tArea.setText(display()+ i + " needs to be a positive number less than 255"); // error message, to let the user know the input out of range
                        } else {
                            try {
                                tArea.setForeground(new Color(rgb[0], rgb[1], rgb[2]));
                                tArea.setText(display());
                            }catch (IllegalArgumentException e1){
                                tArea.setText("Color parameter outside of expected range");
                            }
                        }
                    }
                    // reset the text areas and RGB
                    tField.setText(""); tField1.setText(""); tField2.setText(""); tField3.setText("");
                    rgb[0] = 0; rgb[1] =0; rgb[2] = 0;             // reset the rgb values
                }
                if (this.action == 2) {
                    // ad ID entered in text area
                    try{
                        // set the user input to an int
                        int a = Integer.parseInt(tField.getText());

                        //checks the integer is 6 digits long and isn't a negative
                        if(String.valueOf(a).length()!= 6 || a < 0 ) {
                            throw new NumberFormatException();}

                        // perform the addIDD() to the input
                        addID(a);
                        tArea.setText("ID '"+a+"' has been added to the list.");}
                    catch (NullPointerException e1){
                        tArea.setText("Input = null");}
                    catch (NumberFormatException e1){
                        tArea.setText("Input isn't a legal input");}
                    tField.setText("");                          // reset the text area
                }

                if (this.action == 3){
                    // remove ID entered
                    try {
                        // set the user input to an int
                        int a = Integer.parseInt(theApp.tField.getText());

                        //checks the integer is 6 digits long and isn't a negative
                        if (String.valueOf(a).length() != 6 || a < 0) {
                            throw new NumberFormatException();
                        }
                        // remove any instance of input in the array, using the removeID() method
                        ID k = new ID(a);
                        removeID(k);
                        /*does a check to see if array b is empty as it stores any matching id during the removeID method
                         if it isn't empty we empty the array and display message*/
                        if (b.isEmpty()) {
                            tArea.setText("ID " + a + "' not in list.");
                        } else {
                            b.removeAll(b);
                            tArea.setText("ID " + a + "' has been removed from the list.");
                        }
                    }catch (NullPointerException e1){
                        tArea.setText("Input = null");}
                    catch (NumberFormatException e1) {
                        tArea.setText("Input isn't a legal input");}
                    catch (InputMismatchException e1){
                        tArea.setText("Input not in list");}
                    tField.setText("");
                }

                if (this.action == 4){
                    // sort ID's in descending order
                    sortID();
                    theApp.tArea.setText(display());
                    tField.setText("");
                }

                if (this.action == 5){
                    // Clear all id's
                    //check if the arraylist empty
                    if (idList.isEmpty()){
                        tArea.setText("List is already empty"); // output to the user, letting them know the list is already empty
                    }else{
                        idList.removeAll(idList);
                        tArea.setText("List has been cleared");}
                    tField.setText("");                         // reset the text area
                }
            }
        }
    }

}
