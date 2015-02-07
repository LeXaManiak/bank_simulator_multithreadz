import java.util.ArrayList;
import java.util.Stack;

public class TransactionStack {

	private Object lock = new Object();
	static ArrayList<String> stacklog;
	long transactionid = 0;
	int stackpointer = 0;
	public Stack<Transaction> operationstack;

	public TransactionStack() {
		operationstack = new Stack<Transaction>();
		stacklog = new ArrayList<String>();
	}

	public long addTransaction(Transaction tr) {

		tr.settransactionid(transactionid);
		//System.out.println("push " + tr.toString() + "\t" + stackpointer);
		transactionid++;

		synchronized (lock) {
			stacklog.add("Push"+System.currentTimeMillis()+tr.toString()+"\n\r");
			operationstack.push(tr);
		}

		stackpointer++;
		return transactionid;
	}

	public Transaction getTransaction() {
		if (operationstack.isEmpty())
			return null;
		synchronized (lock) {
			Transaction tr = operationstack.pop();
			stacklog.add("Pop"+System.currentTimeMillis()+tr.toString()+"\n\r");
			//System.out.println("pop\t" + tr.toString() + "\t" + " SP= "
			//		+ stackpointer);
			stackpointer--;
			return tr;
		}
	}

	public boolean isempty() {
		if (operationstack.isEmpty())
			return true;
		return false;
	}
	
	
	public void savestacklog() {
		LogSaver ls = new LogSaver("Stacklog.txt", stacklog);
		ls.savelog();
	}

}
