import java.util.LinkedList;


public class Cell {
	private String CellID;
	private String CellValue;
	private String SuperRowRef;
	private String CellType;
	public LinkedList<String> CellRole = new LinkedList<String>();
	public LinkedList<Annotation> annotations = new LinkedList<Annotation>();
	private String HeaderRef;
	private String StubRef;
	private String StubHeadRef;
	private String RowN;
	private String ColumnN;
	/**
	 * @return the cellID
	 */
	public String getCellID() {
		return CellID;
	}
	/**
	 * @param cellID the cellID to set
	 */
	public void setCellID(String cellID) {
		CellID = cellID;
	}
	/**
	 * @return the cellValue
	 */
	public String getCellValue() {
		return CellValue;
	}
	/**
	 * @param cellValue the cellValue to set
	 */
	public void setCellValue(String cellValue) {
		CellValue = cellValue;
	}
	/**
	 * @return the superRowRef
	 */
	public String getSuperRowRef() {
		return SuperRowRef;
	}
	/**
	 * @param superRowRef the superRowRef to set
	 */
	public void setSuperRowRef(String superRowRef) {
		SuperRowRef = superRowRef;
	}
	/**
	 * @return the cellType
	 */
	public String getCellType() {
		return CellType;
	}
	/**
	 * @param cellType the cellType to set
	 */
	public void setCellType(String cellType) {
		CellType = cellType;
	}
	/**
	 * @return the cellRole
	 */
	public LinkedList<String> getCellRole() {
		return CellRole;
	}
	/**
	 * @param cellRole the cellRole to set
	 */
	public void setCellRole(LinkedList<String> cellRole) {
		CellRole = cellRole;
	}
	/**
	 * @return the headerRef
	 */
	public String getHeaderRef() {
		return HeaderRef;
	}
	/**
	 * @param headerRef the headerRef to set
	 */
	public void setHeaderRef(String headerRef) {
		HeaderRef = headerRef;
	}
	/**
	 * @return the stubRef
	 */
	public String getStubRef() {
		return StubRef;
	}
	/**
	 * @param stubRef the stubRef to set
	 */
	public void setStubRef(String stubRef) {
		StubRef = stubRef;
	}
	/**
	 * @return the stubHeadRef
	 */
	public String getStubHeadRef() {
		return StubHeadRef;
	}
	/**
	 * @param stubHeadRef the stubHeadRef to set
	 */
	public void setStubHeadRef(String stubHeadRef) {
		StubHeadRef = stubHeadRef;
	}
	/**
	 * @return the rowN
	 */
	public String getRowN() {
		return RowN;
	}
	/**
	 * @param rowN the rowN to set
	 */
	public void setRowN(String rowN) {
		RowN = rowN;
	}
	/**
	 * @return the columnN
	 */
	public String getColumnN() {
		return ColumnN;
	}
	/**
	 * @param columnN the columnN to set
	 */
	public void setColumnN(String columnN) {
		ColumnN = columnN;
	}
	

}
