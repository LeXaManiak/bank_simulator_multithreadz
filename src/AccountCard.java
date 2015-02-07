
public class AccountCard {
private int accountID;
private int accountstate;
	public AccountCard(int accountstate, int accountID) {
		this.accountstate = accountstate;
		this.accountID=accountID;
	}
	int getAccountstate() {
		return accountstate;
	}
	void setAccountstate(int accountstate) {
		this.accountstate = accountstate;
	}
	private int getAccountID() {
		return accountID;
	}
	
	

}
