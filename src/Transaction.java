public class Transaction {
	int transactiontype = 0;// 0-create account 1-transfermoney 2-closeaccount
	private long transactionID = 0;
	private long transactiontime;
	private int producerID;
	private int consumerID;
	private int sum;
	private int threadnumber;

	public Transaction(int transactiontype, int producerID, int consumerID,
			int sum, int threadnumber) {
		this.setTransactiontime(System.currentTimeMillis());
		this.setProducerID(producerID);
		this.setConsumerID(consumerID);
		this.setSum(sum);
		this.setThreadnumber(threadnumber);
	}

	public void settransactionid(long transactionID) {
		this.setTransactionID(transactionID);
	}

	public String toString() {
		return ("transactionType=" + transactiontype + "transactionID="
				+ getTransactionID() + "\tFrom " + getProducerID() + "\tto "
				+ getConsumerID() + "\tSum" + getSum() + "\tthreadnumber"
				+ getThreadnumber() + "\tat" + getTransactiontime() + "\t");
	}

	long getTransactionID() {
		return transactionID;
	}

	void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}

	long getTransactiontime() {
		return transactiontime;
	}

	void setTransactiontime(long transactiontime) {
		this.transactiontime = transactiontime;
	}

	int getProducerID() {
		return producerID;
	}

	void setProducerID(int producerID) {
		this.producerID = producerID;
	}

	int getConsumerID() {
		return consumerID;
	}

	void setConsumerID(int consumerID) {
		this.consumerID = consumerID;
	}

	int getSum() {
		return sum;
	}

	void setSum(int sum) {
		this.sum = sum;
	}

	private int getThreadnumber() {
		return threadnumber;
	}

	private void setThreadnumber(int threadnumber) {
		this.threadnumber = threadnumber;
	}

}
