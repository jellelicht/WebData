package myHadoop;

import java.util.ArrayList;

public class Movie {
	String title;
	String year;
	ArrayList<Actor> actors = new ArrayList<Actor>();
	String role;
	String director;

	public Movie() {
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public void setActors(ArrayList<Actor> actors) {
		this.actors = actors;
	}
	public void addActors(Actor actor ){
		this.actors.add(actor);
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
}
