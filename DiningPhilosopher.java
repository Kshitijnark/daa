package practical;
import java.util.concurrent.*; 
public class DiningPhilosopher{
	
	static int NUM_PHILOSOPHER = 5;
	static Semaphore  forks[] = new Semaphore[NUM_PHILOSOPHER];
	static Semaphore diningSemaphore = new Semaphore(NUM_PHILOSOPHER-1);
	
	public DiningPhilosopher(){
		for(int i=0 ;i<NUM_PHILOSOPHER ; i++) {
			forks[i] = new Semaphore(1);
		}
	}
	
	
	public static void dine(int id) throws InterruptedException {
		while(true) {
			
			System.out.println("Philosopher "+id+" is thinking");
			Thread.sleep(1000);
			
			diningSemaphore.acquire();
			
			//left fork
			forks[id].acquire();
			//right fork
			forks[(id+1)%NUM_PHILOSOPHER].acquire();
			
			System.out.println("Philosopher "+id+" is eating");
			Thread.sleep(1000);
			forks[id].release();
			forks[(id+1)%NUM_PHILOSOPHER].release();
			
			diningSemaphore.release();
			
			
		}
	}
	
	
	public static void main(String []args) {
		DiningPhilosopher philosophers = new DiningPhilosopher();
	
		for(int i=0 ;i<NUM_PHILOSOPHER ; i++) {
			
			
			int id = i;
			
			new Thread(()->{
				try {
					philosophers.dine(id);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}