
public class StringPredicate implements Predicate {

	private String pred;
	
	public StringPredicate(String pred) {
		super();
		this.pred = pred;
	}

	@Override
	public boolean isMatch(String s) {
		// TODO Auto-generated method stub
		return pred.equals(s);
	}

}
