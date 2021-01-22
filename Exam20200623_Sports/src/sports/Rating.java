package sports;

public class Rating {
	
	private Product product;
	private String username;
	private int numStars;
	private String comment;
	
	public Rating(Product product, String username, int numStars, String comment) 
			throws SportsException {
		if(numStars < 0 || numStars > 5) {
			throw(new SportsException("Invalid number of stars"));
		}
		this.product = product;
		this.username = username;
		this.numStars = numStars;
		this.comment = comment;
	}

	public Product getProduct() {
		return product;
	}

	public String getUsername() {
		return username;
	}

	public int getNumStars() {
		return numStars;
	}

	public String getComment() {
		return comment;
	}
	
	@Override
	public String toString() {
		return this.numStars + " : " + this.comment;
	}
	
}
