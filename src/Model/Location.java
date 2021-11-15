package Model;

public class Location {
	
	private int row;
	private int column;
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * Constructor:
	 * @param row
	 * @param column
	 */
	public Location(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	

}
