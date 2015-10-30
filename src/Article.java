import java.util.LinkedList;


public class Article {

	private String PMID;
	private String PMCID;
	private String pissn;
	private String eissn;
	private String title;
	public LinkedList<Author> authors = new LinkedList<Author>();
	public LinkedList<String> KeyWords = new LinkedList<String>();
	public LinkedList<Table> tables = new LinkedList<Table>();
	private String Abstract;
	private String JournalName;
	private String JournalPublisherName;
	private String JournalPublisherLocation;
	
	/**
	 * @return the pMID
	 */
	public String getPMID() {
		return PMID;
	}
	/**
	 * @param pMID the pMID to set
	 */
	public void setPMID(String pMID) {
		PMID = pMID;
	}
	/**
	 * @return the pMCID
	 */
	public String getPMCID() {
		return PMCID;
	}
	/**
	 * @param pMCID the pMCID to set
	 */
	public void setPMCID(String pMCID) {
		PMCID = pMCID;
	}
	/**
	 * @return the pissn
	 */
	public String getPissn() {
		return pissn;
	}
	/**
	 * @param pissn the pissn to set
	 */
	public void setPissn(String pissn) {
		this.pissn = pissn;
	}
	/**
	 * @return the eissn
	 */
	public String getEissn() {
		return eissn;
	}
	/**
	 * @param eissn the eissn to set
	 */
	public void setEissn(String eissn) {
		this.eissn = eissn;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the abstract
	 */
	public String getAbstract() {
		return Abstract;
	}
	/**
	 * @param abstract1 the abstract to set
	 */
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	/**
	 * @return the journalName
	 */
	public String getJournalName() {
		return JournalName;
	}
	/**
	 * @param journalName the journalName to set
	 */
	public void setJournalName(String journalName) {
		JournalName = journalName;
	}
	/**
	 * @return the journalPublisherName
	 */
	public String getJournalPublisherName() {
		return JournalPublisherName;
	}
	/**
	 * @param journalPublisherName the journalPublisherName to set
	 */
	public void setJournalPublisherName(String journalPublisherName) {
		JournalPublisherName = journalPublisherName;
	}
	/**
	 * @return the journalPublisherLocation
	 */
	public String getJournalPublisherLocation() {
		return JournalPublisherLocation;
	}
	/**
	 * @param journalPublisherLocation the journalPublisherLocation to set
	 */
	public void setJournalPublisherLocation(String journalPublisherLocation) {
		JournalPublisherLocation = journalPublisherLocation;
	}
}
