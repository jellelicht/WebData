package myHadoop;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MovieMapper   extends Mapper<Object, Text, Text, Text>{
	
	 public void map(Object key, Text value, Context context
	                 ) throws IOException, InterruptedException {
		   String itr = new String(value.toString());
		   MovieParser parser = new MovieParser(itr);
		   Movie movie = parser.parseXML();
		   
			for (Actor actor : movie.getActors()) {
			    String titleAndActor = movie.getTitle() + "\t" + actor.getName() + "\t" + actor.getBirthYear()
			    						+"\t"+ actor.getRole();
			    context.write(new Text("actor"),new Text(titleAndActor));
			}
			
			String dirAndTitle = movie.getDirector() + "\t" + movie.getTitle() + "\t" + movie.getYear();
			
			context.write(new Text("director"),new Text(dirAndTitle));

	 }
}
	
	
	
	

