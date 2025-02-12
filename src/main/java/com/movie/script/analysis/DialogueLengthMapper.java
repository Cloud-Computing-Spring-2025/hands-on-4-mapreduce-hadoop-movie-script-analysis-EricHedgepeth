package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class DialogueLengthMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable wordCount = new IntWritable();
    private Text character = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Ignore empty lines
        String line = value.toString().trim();
        if (line.isEmpty()) return;

        // Split Character: Dialogue
        String[] parts = line.split(": ", 2);
        if (parts.length < 2) return; 

        character.set(parts[0].trim()); // Character Name
        StringTokenizer tokenizer = new StringTokenizer(parts[1]);

        wordCount.set(tokenizer.countTokens());
        context.write(character, wordCount);
    }
}
