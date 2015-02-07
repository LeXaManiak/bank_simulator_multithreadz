import java.util.ArrayList;

import java.util.HashMap;


public class Bank extends Thread {

	final static int bankthread=666;
	
	
	int clientcounter = 0;
	static volatile TransactionStack trs;

	static ArrayList<Transaction> banklog;
	static HashMap<Integer, Client> clients;

	static boolean stopbanking;

	private static volatile HashMap<Integer, AccountCard> accounts;

	public Bank() {
		
		trs = new TransactionStack();
		banklog = new ArrayList<Transaction>();
		clients = new HashMap<Integer, Client>();
		accounts = new HashMap<Integer, AccountCard>();
		stopbanking = false;
		
	}

	public void run() {

		while (stopbanking == false) {

			Transaction tr = trs.getTransaction();
			
			if (tr == null)
				continue;
			 banklog.add(tr);
			
			boolean transfersuxess = transfermoney(tr);

			long transactiondelay = System.currentTimeMillis()
					- tr.getTransactiontime();
			if (!transfersuxess) {

				System.out.println("TRANSFER ERROR\t" + tr.toString());

			} else {

				System.out.println("SUXCESS" + tr.toString() + "\t delay "
						+ transactiondelay + " SP=" + trs.stackpointer);
			}
		

		}
	}

	private boolean transfermoney(Transaction tr) {
		// synchronized (map){
		int producerID = tr.getProducerID();
		int consumerID = tr.getConsumerID();
		int sum = tr.getSum();
		Client producer = clients.get(producerID);
		Client consumer = clients.get(consumerID);

		if (producer == null | consumer == null) {
			System.out.println("NO CLIENT");
			producer.gotmoney(tr);// money back to producer
			return false;
		}
		// synchronized (map){
		AccountCard produceraccountstate = accounts.get(producerID);
		AccountCard consumeraccountstate = accounts.get(consumerID);

		if (produceraccountstate.getAccountstate() < sum) {
			System.out.println("CLIENT " + producerID + "Has not enouth money");
			producer.gotmoney(tr);// money back to producer
			return false;

		}

		consumer.gotmoney(tr);
		
		

		produceraccountstate.setAccountstate(produceraccountstate.getAccountstate()-sum);
		consumeraccountstate.setAccountstate(consumeraccountstate.getAccountstate()+sum);
		
		
		//
		accounts.replace(producerID, produceraccountstate);
		accounts.replace(consumerID, consumeraccountstate);
//
		clients.put(consumer.clientid, consumer);
		
		return true;
		
	}

	public void newtransaction(Transaction tr) {
		trs.addTransaction(tr);
	}

	public void bankaddclient(int accountstate) {

		Client cl = new Client(trs, clientcounter, accountstate);

		clients.put(cl.clientid, cl);
		accounts.put(cl.clientid, new AccountCard(accountstate,cl.clientid));
		// clients.add(new Client(b,clientcounter,accountstate));

		clientcounter++;
	}

	public int bankremoveclient(int clientID) {

		int accountremains = 0;

		Client cl = clients.get(clientID);
		if (cl != null) {
			accountremains = cl.closeaccount();
		} else {
			System.out.println("no client ID=" + clientID);
		}

		clients.remove(clientID);
		accounts.remove(clientID);
		return accountremains;
	}

	public void bankclose() {
		// TODO Auto-generated constructor stub
		stopbanking = true;
		while (!trs.isempty()) {

			Transaction tr = trs.getTransaction();

			boolean transfersuxess = transfermoney(tr);

			long transactiondelay = System.currentTimeMillis()
					- tr.getTransactiontime();
			if (!transfersuxess) {

				System.out.println("TRANSFER ERROR\t" + tr.toString());

			} else {

				System.out.println("CLOSING BANK SUXCESS" + tr.toString()
						+ "\t delay " + transactiondelay + " SP="
						+ trs.stackpointer);
			}

		}

		int sumclient = 0;
		int sumaccounts = 0;
		for (int clientid = 0; clientid < clientcounter; clientid++) {
			
			
			if (accounts.containsKey(clientid)) {
				int remains = accounts.get(clientid).getAccountstate();
				System.out.print(" Bank thing, he has " + remains );
				sumaccounts += remains;
			}
			
			
			int brc = bankremoveclient(clientid);
			sumclient = sumclient + brc;
			System.out.print(" Remaining account " + clientid + " " + brc+"\n\r");
			
		}
		System.out.println("\n\rsumclient =" + sumclient);
		System.out.println("sumaccounts =" + sumaccounts);

		savebanklog();
		trs.savestacklog();
	}

	public void savebanklog() {
		LogSaver ls = new LogSaver("Banklog.txt", banklog);
		ls.savelog();
	}

	public HashMap<Integer, Client> getclients() {
		return clients;

	}

}
