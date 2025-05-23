package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class CharacterWordMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text characterWord = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Ignore empty lines
        String line = value.toString().trim();
        if (line.isEmpty()) return;

        // Split Character: Dialogue
        String[] parts = line.split(": ", 2);
        if (parts.length < 2) return; 

        String character = parts[0].trim();
        StringTokenizer tokenizer = new StringTokenizer(parts[1]);

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!word.isEmpty()) {
                characterWord.set(character + " " + word);
                context.write(characterWord, one);
            }
        }
    }
}
