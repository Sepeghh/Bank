package com.atm;

/**
 * An user of the bank.
 */
public abstract class User {

	/**
	 * User username for login.
	 */
	private final String username;

	/**
	 * User password for login.
	 */
	private final String password;

	/**
	 * User Security Question
	 */
	private String securityQuestion;

	/**
	 * User Security Question Answer
	 */
	private String securityAnswer;


	/**
	 * construct a new User.
	 *
	 * @param username the input for this user username
	 * @param password the input for this user password
	 */
	User(String username, String password, String securityQuestion, String securityAnswer) {
		this.username = username;
		this.password = password;
		this.setSecurityQuestion_Answer(securityQuestion,securityAnswer);

	}

	/**
	 * Sets the users security question
	 * @param question String
	 * @param answer String
	 */
	 private void setSecurityQuestion_Answer(String question,String answer){
		this.securityQuestion = question;
		this.securityAnswer = answer;
	 }

	/**
	 * Gets this users security question
	 * @return String
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * Gets this users security question answer
	 * @return String
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @return this User username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Return a string representing this user and their accounts
	 *
	 * @return String representation of a user.
	 */
	@Override
	public String toString() {
		return getUsername();
	}

	/**
	 * Return this users password.
	 *
	 * @return Password of this user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Abstract method for all users to have a screen to interact with the ATM
	 * @param atm atm being run
	 */
	public abstract void getOptionsScreen(ATM atm);
}
