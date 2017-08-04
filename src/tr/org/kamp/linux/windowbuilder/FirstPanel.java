package tr.org.kamp.linux.windowbuilder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import tr.org.kamp.linux.agarioclone.logic.GameLogic;
import tr.org.kamp.linux.agarioclone.model.Difficulty;

public class FirstPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private ButtonGroup buttonGroup;

	/**
	 * Create the panel.
	 */
	public FirstPanel() {
		setLayout(new MigLayout("", "[82px][5px][grow][][]", "[15px][][][][][][][][][][][][]"));
		
		JLabel lblUserName = new JLabel("User Name:");
		add(lblUserName, "cell 0 0,alignx center,aligny top");
		
		textField = new JTextField();
		add(textField, "cell 2 0,growx");
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		add(lblPassword, "cell 0 1");
		
		textField_1 = new JTextField();
		add(textField_1, "cell 2 1,growx");
		textField_1.setColumns(10);
		
		JLabel lblSetColor = new JLabel("Select Color:");
		add(lblSetColor, "cell 0 3");
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("PINK");
		comboBox.addItem("RED");
		comboBox.addItem("BLUE");
	
		
		add(comboBox, "flowx,cell 2 3,aligny top");
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		add(lblDifficulty, "cell 0 5");
		
		JRadioButton rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setSelected(true);
		add(rdbtnEasy, "cell 2 5,aligny top");
		
		JRadioButton rdbtnNormal = new JRadioButton("Normal");
		add(rdbtnNormal, "cell 2 6");
		

		JRadioButton rdbtnHard = new JRadioButton("Hard");
		add(rdbtnHard, "cell 2 7");
		
		buttonGroup= new ButtonGroup();
		buttonGroup.add(rdbtnEasy);
		buttonGroup.add(rdbtnNormal);
		buttonGroup.add(rdbtnHard);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Color selectedColor=Color.BLACK;
				switch (comboBox.getSelectedItem().toString()) {
				case "RED":
					selectedColor=Color.RED;
					break;
				case "BLUE":
					selectedColor=Color.BLUE;
					break;
				case "PINK":
					selectedColor=Color.PINK;
					break;
				default:
					break;
				}
				
				Difficulty difficulty=Difficulty.EASY;
				
				
				if(rdbtnEasy.isSelected()) {
					difficulty=Difficulty.EASY;
				}
				if(rdbtnEasy.isSelected()) {
					difficulty=Difficulty.NORMAL;
				}
				if(rdbtnEasy.isSelected()) {
					difficulty=Difficulty.HARD;
				}
				else {
					
				}
				GameLogic gameLogic =new GameLogic(textField.getText(),selectedColor,difficulty);
				gameLogic.startApplication();
			}
		});
		add(btnStart, "cell 2 9,alignx center");
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showConfirmDialog(FirstPanel.this, "Lyk 2017de, özgür yazilim araçları ve felsefesi ile geliştirilmiştir.\nTüm hakları saklı falan da değil,istediğinizi yapın.\n Special thanks to Tansel Altınel." , "About", JOptionPane.CANCEL_OPTION);
			}
		});
		add(btnAbout, "flowx,cell 2 10,alignx center");
		

	}

}
