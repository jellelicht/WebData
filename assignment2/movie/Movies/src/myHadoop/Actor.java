package myHadoop;

public class Actor {
	String name;
	String birthYear;
	String role;
	
	public Actor(String name, String birthYear, String role) {
		this.name = name;
		this.birthYear = birthYear;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
