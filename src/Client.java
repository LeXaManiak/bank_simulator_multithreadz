import java.util.ArrayList;

public class Client {

	final static int bankthread = 666;

	int clientid;
	private int accountstate;

	TransactionStack trs;
	ArrayList<Transaction> clientlog;
	long transactionnumber = 0;

	// private Object lock;

	public Client(TransactionStack trs, int clientid, int accountstate) { // creates
																			// new
		this.clientid = clientid; // account
		this.accountstate = accountstate;
		clientlog = new ArrayList<Transaction>();
		clientlog.add(new Transaction(0, clientid, clientid, accountstate,
				bankthread));
		this.trs = trs;
		System.out.println("Client" + clientid + "Added accountstate="
				+ accountstate);

	}

	public long transfermoneyto(int consumerid, int sum, int threadnumber) {

		if (accountstate > sum) {
			accountstate = accountstate - sum;
			Transaction tr = new Transaction(1, clientid, consumerid, sum,
					threadnumber);
			transactionnumber = trs.addTransaction(tr);
			clientlog.add(tr);

			return transactionnumber;
		}
		return 0l;
	}

	public void gotmoney(Transaction tr) {
		accountstate = accountstate + tr.getSum();
		// System.out.println(tr.getTransactionID()+ "got\t\t" +"\tfrom "
		// +tr.getProducerID()+"\tto"+tr.getConsumerID()+"\tsum "+tr.getSum()+"\tHE has now\t"+accountstate);
		clientlog.add(tr);

	}

	public int closeaccount() {

		gotmoney(new Transaction(2, clientid, clientid, 0, bankthread));

		saveclientlog();
		// System.out.println(System.currentTimeMillis() + "Account " + clientid
		// + " CLOSED Last transaction " + transactioncounter + " PASSED");
		return accountstate;
	}

	public int getaccountstate() {
		return accountstate;

	}

	public void saveclientlog() {

		LogSaver ls = new LogSaver("Clientlog" + clientid + ".txt", clientlog);
		ls.savelog();
	}
}
