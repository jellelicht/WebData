package myHadoop;

/**
 * Import the necessary Java packages
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

//http://java.dzone.com/articles/hadoop-practice

public class MovieMain {
  
  public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
    
		/* We expect two arguments */

		if (args.length != 2) {
		  System.err.println("Usage: AuthorsJob <in> <out>");
		  System.exit(2);
		}
		
	    conf.set("xmlinput.start", "<movie>");           
	    conf.set("xmlinput.end", "</movie>");    
	    conf.set("io.serializations",
	    "org.apache.hadoop.io.serializer.JavaSerialization,org.apache.hadoop.io.serializer.WritableSerialization");
	    
	    Job job = Job.getInstance(conf, "movie specs");
	    job.setJarByClass(MovieMain.class);
	    
	    job.setMapperClass(MovieMapper.class);
	    job.setReducerClass(MovieReducer.class);  
		
	    /* Define the output type */
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(Text.class);
	    
	    /* Define the Xml input format */
	    job.setInputFormatClass(XmlInputFormat.class);
	    
	    /* Set the input and the output */
	    FileInputFormat.setInputDirRecursive(job, true);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
		MultipleOutputs.addNamedOutput(job, "TitleAndActor", TextOutputFormat.class, NullWritable.class, Text.class);
		MultipleOutputs.addNamedOutput(job, "DirectorAndTitle",TextOutputFormat.class, NullWritable.class, Text.class);
	    
	    FileSystem hdfs = FileSystem.get(new Path(args[1]).toUri(), conf);
	    if (hdfs.exists(new Path(args[1]))) {
	        hdfs.delete(new Path(args[1]), true);
	    }

		/* Do it! */
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
		}
}