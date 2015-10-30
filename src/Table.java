import java.util.LinkedList;


public class Table {
	private String fileName;
	private String TableOrder;
	private String Caption;
	private String Footer;
	private String StructureType;
	private String PragmaticType;
	private String hasXML;
	public LinkedList<Cell> cells = new LinkedList<Cell>();
	/**
	 * @return the tableOrder
	 */
	public String getTableOrder() {
		return TableOrder;
	}
	/**
	 * @param tableOrder the tableOrder to set
	 */
	public void setTableOrder(String tableOrder) {
		TableOrder = tableOrder;
	}
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return Caption;
	}
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		Caption = caption;
	}
	/**
	 * @return the footer
	 */
	public String getFooter() {
		return Footer;
	}
	/**
	 * @param footer the footer to set
	 */
	public void setFooter(String footer) {
		Footer = footer;
	}
	/**
	 * @return the structureType
	 */
	public String getStructureType() {
		return StructureType;
	}
	/**
	 * @param structureType the structureType to set
	 */
	public void setStructureType(String structureType) {
		StructureType = structureType;
	}
	/**
	 * @return the pragmaticType
	 */
	public String getPragmaticType() {
		return PragmaticType;
	}
	/**
	 * @param pragmaticType the pragmaticType to set
	 */
	public void setPragmaticType(String pragmaticType) {
		PragmaticType = pragmaticType;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the hasXML
	 */
	public String getHasXML() {
		return hasXML;
	}
	/**
	 * @param hasXML the hasXML to set
	 */
	public void setHasXML(String hasXML) {
		this.hasXML = hasXML;
	}
	

}
