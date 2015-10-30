import java.util.LinkedList;


public class Author {
	
	private String AuthorName;
	private String FirstName;
	private String SecondName;
	public LinkedList<String> AuthorAffiliation = new LinkedList<String>();
	private String Email;
	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return AuthorName;
	}
	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		AuthorName = authorName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return FirstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	/**
	 * @return the secondName
	 */
	public String getSecondName() {
		return SecondName;
	}
	/**
	 * @param secondName the secondName to set
	 */
	public void setSecondName(String secondName) {
		SecondName = secondName;
	}

}
