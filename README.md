# Project Overview 

This lab/ hands on assignment take a movie script dialogue lines and using the MapReducer in hadoop. It will look for the most common words spoke by each character, the total number of words spoken by each character, and find any words that are distinct that each character says. 

# Approach and Implementation

* Mapper
    -The Mapper reads the input file line by line, breaks each line into individual words, and removes any punctuation. For every word it finds, it creates a (word, 1) pair

* Reducer 
    -The Reducer takes all those word counts and adds them up. If the Mapper found "Hadoop" five times in different places, the Reducer will sum those 1s together and output the total times the word was reportly found. 

* Length Mapper 
    -The Length Mapper adds up the number of words spoken per character 

*  Length Reducer
    -The Length Reducer sums all the word count per character to get a total of all spoken  

* Unique Length Mapper 
    -The Unique Length Mapper identifies unique words said by each character 

* Unique Length Reducer
    -The Unique Length Reducer sums all the word count per character to get a total of all spoken


# Execution Steps 

* docker compose up -d 

* mvn clean install

* docker cp target/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/

* docker cp input/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/

* docker exec -it resourcemanager /bin/bash

* hadoop fs -mkdir -p /input/movie_scripts

* hadoop fs -put /opt/hadoop-3.2.1/share/hadoop/mapreduce/movie_dialogues.txt /input/movie_scripts/

* hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar \com.movie.script.analysis.MovieScriptAnalysis \ /input/movie_scripts/movie_dialogues.txt /output

* hadoop fs -ls /output

* hadoop fs -cat /output/task1/part-r-00000

* hadoop fs -cat /output/task2/part-r-00000

* hadoop fs -cat /output/task3/part-r-00000

*hadoop fs -get /output /opt/hadoop-3.2.1/share/hadoop/mapreduce/

* exit

* docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/ shared-folder/output/

# Challenges Faced & Solutions 

* I faced issues with my JAR location and I solved it by verifying the path, then used docker cp to get it into the correct directory. I also had issues with the script file not uploading into the HDFS and I fixed that by fixing my spelling on the command. I also had an issue with some broken logic not splitting the words correct or missing words, I added punctuation removal and it is working.   

# Sample Input and Output

---
**Sample Input **
---


    Harry: It takes a great deal of bravery to stand up to our enemies.
    Ron: That's bloody brilliant!
    Hermione: When in doubt, go to the library.
    Ron: I am and always will be the optimist.
    Harry: You have your mother's eyes.

---
**Sample Output of word Frequency **
---

    Harry bravery 1
    Harry takes 1
    Harry deal 1
    Ron bloody 1
    Ron brilliant 1
    Hermione doubt 1
    Hermione library 1

---
**Sample Output of Dialogue Length **
---


    Harry 13
    Ron 10
    Hermione 8


---
**Sample Output of Unique Words per Character **
---

    Harry: bravery, takes, deal, stand, enemies, mother’s, eyes
    Ron: bloody, brilliant, optimist, always, will, am
    Hermione: doubt, go, library
