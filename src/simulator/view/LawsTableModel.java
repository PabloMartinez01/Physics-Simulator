package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

public class LawsTableModel extends AbstractTableModel {


	private static final long serialVersionUID = -2553185616503478666L;
	
	private String[] columnNames = {"Key", "Values", "Description"};
	private List<LawsInfo> rows;
	
	public LawsTableModel() {
		rows = new ArrayList<LawsInfo>();
	}
	
	public void updateTable(JSONObject data) {
		rows.clear();
		for (String key : data.keySet()) {
			LawsInfo parameter = new LawsInfo(key, "", data.getString(key));
			rows.add(parameter);
		}
		fireTableStructureChanged();
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}
	
	
	@Override
	public void setValueAt(Object o, int rowIndex, int columnIndex) {
		LawsInfo parameter = rows.get(rowIndex);
		parameter.setValue(o.toString());
		fireTableStructureChanged();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		LawsInfo parameter = rows.get(arg0);
		String s = "";
		
		switch(arg1) {
		case 0:
			s = parameter.getKey();
			break;
		case 1:
			s = parameter.getValue();
			break;
		case 2:
			s = parameter.getDescription();
			break;
		}
		
		return s;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	

}
