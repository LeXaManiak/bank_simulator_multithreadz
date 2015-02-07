import java.util.ArrayList;
import java.util.HashMap;

public class Emulator extends Thread {

	long transactionnumber = 0;
	final static int emulatorcounter = 5;
	static HashMap<Integer, Client> clients;
	int threadnumber = 0;

	public Emulator(int threadnumber) {
		this.threadnumber = threadnumber;

	}

	public static void main(String[] args) {
		// TODO Auto-generated constructor stub

		Bank b = new Bank();
		clients = b.getclients();
		b.start();

		for (int i = 1; i <= 8; i++) {
			// b.bankaddclient((int) (1000*Math.random()));

			b.bankaddclient(1000);
		}
		;
		Emulator emulators[] = new Emulator[emulatorcounter];

		for (int n = 0; n < emulatorcounter; n++) {
			emulators[n] = new Emulator(n);
		}

		for (int n = 0; n < emulatorcounter; n++) {
			emulators[n].start();
			;
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		try {
			for (int n = 0; n < emulatorcounter; n++) {
				emulators[n].join();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		b.bankclose();
		try {

			b.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		ArrayList<StringBuilder> emulatorlog = new ArrayList<StringBuilder>();

		for (int i = 1; i <= 10000; i++) {
			int n = (clients.size());

			int producerId = (int) (n * Math.random());
			int consumerId = (int) (n * Math.random());
			int sum = (int) (1000 * Math.random());
			

			Client producer = clients.get(producerId);

			if (producer != null && clients.get(consumerId) != null) {
				 transactionnumber=producer.transfermoneyto(consumerId, sum, threadnumber);
			}

			
			if (transactionnumber!=0){
			StringBuilder str = new StringBuilder("transactionnumber="+transactionnumber+ "emulator\t" + threadnumber
					+ " producer= " + producerId + "\tconsumer= " + consumerId
					+ "\tsum=" + sum);
			emulatorlog.add(str);
			
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		LogSaver ls = new LogSaver("emulatorlog" + threadnumber + ".txt",
				emulatorlog);
		ls.savelog();
	}
}
