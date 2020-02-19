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
    private JButton stapleButton;
    private JFormattedTextField directoryField;
    private JLabel directoryLabel;
    String filePath;

    public ComicStaplerGUI() {
        stapleButton.setFont(new Font("Arial", Font.PLAIN, 40));
        fileChooserButton.setFont(new Font("Arial", Font.PLAIN, 40));
        directoryField.setFont(new Font("Arial", Font.PLAIN, 20));
        directoryLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JFileChooser fileChooser = new JFileChooser();
        directoryField.setEditable(false);

        filePath = null;

        fileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setDialogTitle("Select a directory containing comics.");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                fileChooser.setPreferredSize(new Dimension(screen.width/2, screen.height/2));

                if(fileChooser.showOpenDialog(fileChooserButton) == JFileChooser.APPROVE_OPTION){
                    //
                }

                //set the filepath as well as the preview filepath text for the user
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
                directoryField.setValue(filePath);
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
        JFrame frame = new JFrame("ComicStapler");
        frame.setContentPane(new ComicStaplerGUI().panelMain);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        

    }
}
