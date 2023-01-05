package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.json.JSONObject;

import simulator.control.Controller;

public class ForceLawsDialog extends JDialog{

	private static final long serialVersionUID = 1502947097702452899L;
	
	private int status;
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> values;
	
	private JTable parametersTable;
	private LawsTableModel model;
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	private Controller ctrl;
	
	public ForceLawsDialog(Frame frame, Controller ctrl) {
		super(frame, true);
		this.ctrl = ctrl;
		values = new DefaultComboBoxModel<>();
		
		for(JSONObject object : ctrl.getForceLawsInfo()) {
			values.addElement(object.getString("desc"));
		}
		
		comboBox = new JComboBox<>(values);
		
		initGUI();
	}
	
	public void initGUI() {
		
		status = 0;
		
		this.setTitle("Force Laws Selector");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		
		JLabel helpMsg = new JLabel( "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
		helpMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainPanel.add(helpMsg);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel,BorderLayout.CENTER);
				
		// add border
		eventsPanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Parameters", TitledBorder.LEFT,
				TitledBorder.TOP));

		
		// the model
		model = new LawsTableModel();
		parametersTable = new JTable(model);
		parametersTable.setFillsViewportHeight(true);

		eventsPanel.add(new JScrollPane(parametersTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewsPanel.setPreferredSize(new Dimension(800, 40));
		viewsPanel.setMaximumSize(new Dimension(800, 40));
		viewsPanel.setMinimumSize(new Dimension(800, 40));
		
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				for (int i = 0; i < ctrl.getForceLawsInfo().size(); i++) {
					if (i == comboBox.getSelectedIndex()) {

						model.updateTable(ctrl.getForceLawsInfo().get(i).getJSONObject("data"));
					}
				}
				
			}
			
		});
		
		JLabel label = new JLabel("Forces: ");
		
		viewsPanel.add(label);
		viewsPanel.add(comboBox);
		
		mainPanel.add(viewsPanel);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ForceLawsDialog.this.setVisible(false);
			}
		});
		
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 1;
				ForceLawsDialog.this.setVisible(false);
				}
			
		});
		
		buttonsPanel.add(okButton);
		
		setPreferredSize(new Dimension(800, 500));
		pack();
		setResizable(false);
	
	}
	
	public int open() {
		this.setVisible(true);
		return status;
	}

	public JSONObject getSelectedLaws() {
		JSONObject object = new JSONObject(ctrl.getForceLawsInfo().get(comboBox.getSelectedIndex()).toString());
		JSONObject data = object.getJSONObject("data");
		
		
		
		for (int i = 0; i < model.getRowCount(); i++) {
			String key = (String) model.getValueAt(i, 0);
			String value = (String) model.getValueAt(i, 1);
			
			if (!value.isEmpty()) {
				data.put(key, value );
			}
			else {
				data.remove(key);
			}
			
				
		}
		
		object.put("data", data);
		System.out.println(object);
		
		
		return object;
	}

	
	
	
	

}
