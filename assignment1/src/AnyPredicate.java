
public class AnyPredicate implements Predicate {

	private static AnyPredicate _instance;
	@Override
	public boolean isMatch(String s) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static AnyPredicate getInstance(){
		if(_instance == null){
			_instance = new AnyPredicate();
		}
		return _instance;
	}
	
	

}
