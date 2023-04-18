
/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {

  /*
   * ------------
   * Data members
   * ------------
   */

  int piNumberOfPhilosophers;

  private enum STATE {
    THINKING,
    HUNGRY,
    EATING,
  }

  //The array of state that will hold the current state of the philosophers
  private STATE[] states;

  //The flag used to refer if philosopher is talking
  private boolean isPhilosopherTalking;

  /**
   * Constructor
   */
  public Monitor(int piNumberOfPhilosophers) {

    // TODO: set appropriate number of chopsticks based on the # of philosophers

    this.piNumberOfPhilosophers = piNumberOfPhilosophers;
    states = new STATE[piNumberOfPhilosophers];

    //Initializing the default state of all the philosophers to THINKING
    for (int i = 0; i < piNumberOfPhilosophers; i++) {
      states[i] = STATE.THINKING;
    }
    isPhilosopherTalking=false;
  }

  /*
   * -------------------------------
   * User-defined monitor procedures
   * -------------------------------
   */

  /**
   * Grants request (returns) to eat when both chopsticks/forks are available.
   * Else forces the philosopher to wait()
   */
  public synchronized void pickUp(final int piTID) {
    int positionOfPhilosopher = piTID - 1;
    states[positionOfPhilosopher] = STATE.HUNGRY;
    test(positionOfPhilosopher);
  }
  

  /**
   * When a given philosopher's done eating, they put the chopstiks/forks down
   * and let others know they are available.
   */
  public synchronized void putDown(final int piTID) {

    states[piTID - 1] = STATE.THINKING;
    notifyAll();
  }

  /**
   * Only one philopher at a time is allowed to philosophy
   * (while she is not eating).
   */
  public synchronized void requestTalk() {
    if (isPhilosopherTalking) {
      try {
        wait();
        requestTalk();
      } 
      catch (InterruptedException exception) {
        System.out.println("Another philosopher is talking");
      }
    }

    isPhilosopherTalking = true;
  }

  /**
   * When one philosopher is done talking stuff, others
   * can feel free to start talking.
   */
  public synchronized void endTalk() {
    isPhilosopherTalking = false;
    notifyAll();
  }

  /**
   * 
   * @param positionOfPhilosopher The position of the philosopher
   * 
   * This method is used to test if the forks on the right and left 
   * of the philosopher are available. We are checking if the given 
   * philosopher is hungry and the philosopher on the right and left 
   * are not eating then the given philosopher will eat 
   */
  public synchronized void test(int positionOfPhilosopher) {
    try{
      while(true){
        int philosopherOnRight = (positionOfPhilosopher + 1) % piNumberOfPhilosophers;
        int philosopherOnLeft = (positionOfPhilosopher + (piNumberOfPhilosophers-1)) % piNumberOfPhilosophers;

        if (states[philosopherOnLeft] != STATE.EATING &&
            states[philosopherOnRight] != STATE.EATING &&
            states[positionOfPhilosopher] == STATE.HUNGRY) 
        {
              states[positionOfPhilosopher] = STATE.EATING;
              break;
        }
        else
        {
          wait();
        }
      }
    }
    catch(InterruptedException exception){
      System.out.println(exception.getMessage());
    }
  }
}
// EOF
