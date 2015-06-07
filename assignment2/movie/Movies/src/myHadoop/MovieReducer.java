package myHadoop;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class MovieReducer extends Reducer<Text, Text, Text, Writable> {
	@SuppressWarnings("rawtypes")
	private MultipleOutputs mos;
	private Text result = new Text();
	public void setup(Context context) {
		mos = new MultipleOutputs(context);
	}
	 
	@SuppressWarnings("unchecked")
	public void reduce(Text key,Iterable<Text> values,  Context context) throws IOException, InterruptedException {
	/* Iterate on the list to compute the count */
		for (Text value : values){
			if(key.equals(new Text("actor"))) {
				result.set(value);
            	mos.write("TitleAndActor", NullWritable.get(), result);
			}
			else {
				result.set(value);
            	mos.write("DirectorAndTitle", NullWritable.get(), result);
			}
        }
	}
	
	public void cleanup(Context context) throws IOException, InterruptedException {
		mos.close();
	}
}