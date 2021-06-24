package view.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import control.io.AnnotationWorkspace;
import view.workspace.WorkspacePanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2594191483753611719L;
	private JPanel contentPane;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem fileNewItem;
	private JMenuItem fileOpenItem;
	private JMenuItem fileSaveItem;
	private JMenuItem fileSaveAsItem;
	private JSeparator separator;
	private JMenuItem fileQuitItem;
	
	/**
	 * Create the frame.
	 */
	public MainFrame(WorkspacePanel<BufferedImage> workspacePanel, AnnotationWorkspace<BufferedImage> annotationWorkspace) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.contentPane.add(workspacePanel);
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileNewItem = new JMenuItem("New");
		fileMenu.add(fileNewItem);
		
		fileOpenItem = new JMenuItem("Open");
		fileOpenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		fileMenu.add(fileOpenItem);
		
		fileSaveAsItem = new JMenuItem("Save as ...");
		fileSaveAsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					annotationWorkspace.saveAs();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		
		fileSaveItem = new JMenuItem("Save");
		fileSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					annotationWorkspace.save();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		fileMenu.add(fileSaveItem);
		fileMenu.add(fileSaveAsItem);
		
		separator = new JSeparator();
		fileMenu.add(separator);
		
		fileQuitItem = new JMenuItem("Quit");
		fileMenu.add(fileQuitItem);
	}

}
