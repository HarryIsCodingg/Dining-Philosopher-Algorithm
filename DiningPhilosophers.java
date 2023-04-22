/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Harinder Partap Singh
 */
public class DiningPhilosophers {

  /*
   * ------------
   * Data members
   * ------------
   */

  /**
   * This default may be overridden from the command line
   */
  public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

  /**
   * Dining "iterations" per philosopher thread
   * while they are socializing there
   */
  public static final int DINING_STEPS = 10;

  /**
   * Our shared monitor for the philosphers to consult
   */
  public static Monitor soMonitor = null;

  /*
   * -------
   * Methods
   * -------
   */

  /**
   * Main system starts up right here
   */
  public static void main(String[] argv) {
    try {
      /*
       * TODO:
       * Should be settable from the command line
       * or the default if no arguments supplied.
       */
      int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

      //Setting the philosophers if entered in command line
      if (argv.length > 0) {

        try {

          //Checking if the number entered is integer
          int numberOfPhilosophers = Integer.parseInt(argv[0]);

          if (numberOfPhilosophers <= 0) {
            System.out.println(argv[0]+" is not positive integer. Please enter positive integer and try again");
            System.exit(0);
          } 
          else 
          {
            iPhilosophers = numberOfPhilosophers;
          }
        } 
        catch (NumberFormatException exception) 
        {
          System.out.println(argv[0]+" is not valid integer. Please enter a valid integer and try again");
          System.exit(0);
        }
      }

      // Make the monitor aware of how many philosophers there are
      soMonitor = new Monitor(iPhilosophers);

      // Space for all the philosophers
      Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

      // Let 'em sit down
      for (int j = 0; j < iPhilosophers; j++) {
        aoPhilosophers[j] = new Philosopher();
        aoPhilosophers[j].start();
      }

      System.out.println(
        iPhilosophers + " philosopher(s) came in for a dinner."
      );

      // Main waits for all its children to die...
      // I mean, philosophers to finish their dinner.
      for (int j = 0; j < iPhilosophers; j++) aoPhilosophers[j].join();

      System.out.println(
        "All philosophers have left. System terminates normally."
      );
    } catch (InterruptedException e) {
      System.err.println("main():");
      reportException(e);
      System.exit(1);
    }
  } // main()

  /**
   * Outputs exception information to STDERR
   * @param poException Exception object to dump to STDERR
   */
  public static void reportException(Exception poException) {
    System.err.println(
      "Caught exception : " + poException.getClass().getName()
    );
    System.err.println("Message          : " + poException.getMessage());
    System.err.println("Stack Trace      : ");
    poException.printStackTrace(System.err);
  }
}
// EOF
