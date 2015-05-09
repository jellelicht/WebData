
public class StringPredicate implements Predicate {

	private String pred;
	
	public StringPredicate(String pred) {
		super();
		this.pred = pred;
	}

	@Override
	public boolean isMatch(String s) {
		// TODO Auto-generated method stub
		System.out.println("Comparing internal " + pred + " vs external " + s);
		return pred.equals(s);
	}

}
