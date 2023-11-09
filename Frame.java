import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;

/*
 * =============================================================================================================
 * Frame.java : The main frame of the application
 *              Shows the simulation window and any labels etc.
 * By Jeff Smith
 * =============================================================================================================
 */

public class Frame extends JFrame {
    //Frame components:
    private Simulation panel;

    JTree tree;

    public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Frame();
			}
		});
	}

    public Frame() {
        super("Boid Simulation");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);

        // create a new Simulation object
        Simulation panel = new Simulation();
        
        // set the content pane of the JFrame to the JPanel
        setContentPane(panel);
        setVisible(true);
    }
}