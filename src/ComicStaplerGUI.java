import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ComicStaplerGUI {
    private JPanel panelMain;
    private JButton fileChooserButton;
    private JFileChooser fileChooser;
    private JTextArea textArea;
    private JButton stapleButton;
    private JProgressBar progressBar;
    String filePath;

    public ComicStaplerGUI() {
        stapleButton.setFont(new Font("Arial", Font.PLAIN, 50));
        JFileChooser fileChooser = new JFileChooser();
        filePath = null;

        fileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setDialogTitle("Select a directory containing comics.");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if(fileChooser.showOpenDialog(fileChooserButton) == JFileChooser.APPROVE_OPTION){
                    //
                }
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });



        //button attempts to staple based ont he filename
        stapleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ComicStapler.staple(filePath, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Stapling failed!");
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Application");
        frame.setContentPane(new ComicStaplerGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
