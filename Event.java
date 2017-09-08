import java.util.Collections;
import java.util.List;

/**
* Models an Event objects with it's unique ID, list of ticket prices, and location
*/
public class Event {
	private List<Integer> tickets;
	private int identifier;
	private Location coords;
	private int distance;

	public Event(int identifier, List<Integer> tickets, Location coords) {
		this.tickets = tickets;
		this.identifier = identifier;
		this.coords = coords;
	}

	public int getCheapestTicket() {
		if (tickets.size() == 0) {
			return 0;
		}
		return Collections.min(tickets);
	}

	public int getEventNumber() {
		return this.identifier;
	}

	public Location getCoords() {
		return coords;
	}

	public void setDistance(int d) {
		this.distance = d;
	}

	public int getDistance() {
		return distance;
	}
}
