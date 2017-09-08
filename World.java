
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


public class World {

	private String[] args;
	private final int HEIGHT = 21;
	private final int WIDTH = 21;
	private final int UPPER_BOUND = 10;
	private final int LOWER_BOUND = -10;

	/**
	* gets the closest events to the user's inputted coordinates
	* int i x coordinate
	* int j y coordinate
	*/
	public void getClosestEvents(int i, int j) {
		Queue<Event> eventsByDistance = new PriorityQueue<Event>(new EventComp(i, j));
		eventsByDistance.addAll(generateWorld());
		System.out.println("Closest events to ("+i +","+j+") :");
		int length = 5;
		//if there are 5 or fewer events, return all
		if (eventsByDistance.size() <= 5) {
			length = eventsByDistance.size();
		}
		for (int e = 0; e < length; e++) {
			Event event = eventsByDistance.poll();
			String cheapestTicket  = "";
			if (event.getCheapestTicket() == 0) {
				cheapestTicket = "-no tickets";
			} else {
				cheapestTicket = "-$" + event.getCheapestTicket();
			}

			System.out.println("Event " + event.getEventNumber() + cheapestTicket
			 + " ,Distance" + event.getDistance());
		}
	}

	 /**
	   * Compares two events based on their distance to the user's inputted event
	   */
	  private class EventComp implements Comparator<Event> {
	    	 private int x;
	    	 private int y;
	   	   EventComp(int x, int y) {
	 	      this.x = x;
	 	      this.y = y;
	 	    }
	    @Override
	    public int compare(Event a, Event b) {
	    	  int distanceA = World.getManhattanDistance(a.getCoords().getX(),
	    			  a.getCoords().getY(), x, y);
	    	  a.setDistance(distanceA);
	    	  int distanceB = getManhattanDistance( b.getCoords().getX(), b.getCoords().getY(), x, y);
	    	  b.setDistance(distanceB);
	      return Integer.compare(distanceA, distanceB);
	    }
	  }

	 /**
	  * Generate a random list of events with x y coordinates between -10 and 10
	  * @return
	  */
	private List<Event> generateWorld() {
		//generate between 1 to 200 events to fill the grid
		Random rand = new Random();
		int numEvents = rand.nextInt(201) + 1;
		System.out.println("num events" + numEvents);
		List<Event> events = new ArrayList<>();
		for (int i = 0; i < numEvents; i++) {
			//generate a row and col between upper bound and lower bound
			int row = rand.nextInt(UPPER_BOUND + Math.abs(LOWER_BOUND) + 1) + LOWER_BOUND;
			int col = rand.nextInt(UPPER_BOUND + Math.abs(LOWER_BOUND)+ 1) + LOWER_BOUND;
			events.add(new Event(i, generateTickets(), new Location(row, col)));
		}
		return events;
	}

	/**
	 * Generate a random number of tickets (up to 50) with prices between 1 and 100
	 * @return list of tickets
	 */
	private List<Integer> generateTickets() {
		List<Integer> tickets = new ArrayList<>();
		Random rand = new Random();
		int numTickets = rand.nextInt(51);
		for (int i = 0; i < numTickets; i++) {
			tickets.add(rand.nextInt(100) + 1);
		}
		return tickets;
	}

	/**
	 *
	 * @param a x of first pair
	 * @param b y of first pair
	 * @param c x of second pair
	 * @param d y of second pair
	 * @return distance
	 */
	private static int getManhattanDistance(int a, int b, int c, int d) {
		return Math.abs(a-c) + Math.abs(b-d);
	}

	public static void main(String[] args) throws IOException {
		new World(args).run();
	}


	private World(String[] args) {
	    this.args = args;
	}

	private void run() {
		try {
				  System.out.println("Please enter coordinates: ");
	        BufferedReader repl = new BufferedReader(new InputStreamReader(System.in));
					String input = null;
	 			  while ((input = repl.readLine()) != null) {
		        String[] coords = input.split(",");
		        if (coords.length == 2) {
			        int x = Integer.parseInt(coords[0]);
			        int y = Integer.parseInt(coords[1]);
							if (x > UPPER_BOUND || x < LOWER_BOUND || y > UPPER_BOUND || y < LOWER_BOUND) {
								System.out.println("This location does not exist in this world");
							} else {
			        this.getClosestEvents(x, y);
						}
		        } else {
		        		System.out.println("Invalid Coordinates");
		        }
					}
	    } catch (NumberFormatException ex) {
	       System.out.println("Invalid Coordinates");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
