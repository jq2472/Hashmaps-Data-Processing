package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The Dance Marathon
 * @author jolin qiu
 */

public class DanceMarathon {

    /**
     * the jukebox that contains the key-value: (song, # of times song is played pair)
     */
    private final Map<Song, Integer> jukebox;

    /**
     * the Array list of the songs such that they can be indexed
     */
    private final ArrayList<Song> songs;

    /**
     * total number of songs played
     */
    private int totalPlays;

    /**
     * number of simulations ran
     */
    private final int SIMULATIONS_RAN = 100000;

    /**
     * random seed
     */
    private Random rng = new Random(42);


    // setting up the jukebox
    /**
     * reads a file of songs and loads the jukebox (hashmap) with that data.
     * prints the total number of songs, the first song, and the last song.
     * @param filename the name of the file
     * @throws FileNotFoundException error if there's no file
     */
    public DanceMarathon(String filename) throws FileNotFoundException {
        // jukebox : holds song & the number of times it is repeated
        this.jukebox = new HashMap<>();
        System.out.println("Loading the jukebox with songs:\n" +
                "\tReading songs from " + filename + " into jukebox...");
        //open file for scanning
        File file = new File(filename);
        Scanner input = new Scanner(file);
        //loop over and parse each line in the input file
        while (input.hasNextLine()) {
            // read and split the line into an array of strings
            // where each string is separated by <SEP>.
            String line = input.nextLine();
            String[] element = line.split("<SEP>");
            // [TRMMMYQ128F932D901, SOQMMHC12AB0180CB8, Faster Pussy cat, Silent Night]
            Song song = new Song(element[2], element[3]);
            jukebox.put(song, 0);
        }
        songs = new ArrayList<>(this.jukebox.keySet());
        System.out.println("\tJukebox is loaded with " + getNumSongs() + " songs"
                + "\n\tFirst song in jukebox: " + getFirstSong()
                + "\n\tLast song in jukebox: " + getLastSong());
    }

    /**
     * compute the total number of songs in the jukebox
     * @return (int) the total number of songs in the jukebox
     */
    public int getNumSongs() {
        return songs.size();
    }

    /**
     * returns the first song in the jukebox
     * @return (Song) the first song in the jukebox
     */
    public Song getFirstSong() {
        return songs.get(0);
    }

    /**
     * returns the last song in the jukebox
     * @return (Song) the last song in the jukebox
     */
    public Song getLastSong() {
        return songs.get(songs.size() - 1);
    }


    // gathering data for the simulation

    /**
     * prints the first 5 songs of the array
     */
    public void printFirstFive() {
        if (songs.size() < 5){
           for (int i = 0; i < 5; i++) {
               System.out.println("\t" + "\t" + songs.get(0));
           }
        } else {
            for (int i = 0; i < 5; i++) {
                System.out.println("\t" + "\t" + songs.get(i));
            }
        }
    }

    /**
     * the dance marathon algorithm
     * Create an empty hash set of songs
     *     While a duplicate song has not been played:
     *         Randomly generate an index in the full range of the jukebox's size
     *         Get the song from the jukebox array and add it to the hash set
     *         If the song was not already in the set:
     *             Increase the number of times the song was played in the jukebox by 1
     */
    public void marathon() {
        //start timer
        long startTime = System.currentTimeMillis();
        //running the simulation
        System.out.println("Running the simulation. The jukebox starts rockin'!");
        for (int i = 0; i < SIMULATIONS_RAN; i++) {
            Set<Song> songSet = new HashSet<>();
            while(true){
                int nxt = this.rng.nextInt(getNumSongs());
                Song nextSong = songs.get(nxt);
                if (!songSet.contains(nextSong)) {
                    // Increase the number of times the song was played in the jukebox by 1
                    // get returns the value of the key
                    int newValue = jukebox.get(nextSong) + 1;
                    jukebox.put(nextSong, newValue);
                    songSet.add(nextSong);
                } else {
                    break;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        double totalTime = (double)(endTime - startTime)/1000;
        System.out.println("\tPrinting first 5 songs played...");
        printFirstFive();
        //end simulation
        System.out.println("\tSimulation took " + totalTime + " second/s to run");
        marathonStatistics();
    }


    // gathering the simulation statistics
    /**
     * gets the total plays of songs from the jukebox
     * @return (int) the total number of songs played from the jukebox
     */
    public int getTotalPlays(){
        //the values of the jukebox are the number of times that song has been played
        for (int timesPlayed : jukebox.values()) {
            totalPlays += timesPlayed;
        }
        return totalPlays;
    }

    /**
     * gets the average number of songs the jukebox plays before a duplicate
     * is played
     * @return (int) the avergae number of plays
     */
    public int getAvgNumPlays(){
        return (int) Math.ceil((double)totalPlays/SIMULATIONS_RAN);
    }

    /**
     * gets the most played song from the jukebox
     * @return (Song) the most played song
     */
    public Song getMostPlayedSong(){
        Map.Entry<Song,Integer> maxEntry = null;
        for (Map.Entry<Song, Integer> entry : jukebox.entrySet()){
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    /**
     * songs by the most played artist ordered alphabetically
     */
    public void alphabeticalSongs(){
//        System.out.println("Running alphabeticalSongs()");
        TreeSet<Song> allByArtistAlphabet = new TreeSet<>();
        for (Song song : jukebox.keySet()){
            if (song.artist.equals(getMostPlayedSong().artist)){
                allByArtistAlphabet.add(song);
            }
        }
        for (Song song : allByArtistAlphabet){
            System.out.println("\t\t" + "\"" + song.title + "\""
                    + " with " + jukebox.get(song) + " plays");
        }
//        System.out.println("Finished running alphabeticalSongs()");
    }

    /**
     * displays the simulation statistics
     */
    public void marathonStatistics() {
        // number of songs played per simulation to get a duplicate
        Song mostPlayedSong = getMostPlayedSong();
        System.out.println("Displaying simulation statistics:"
                + "\n\tNumber of simulations run: " + SIMULATIONS_RAN
                + "\n\tTotal number of songs played: " + getTotalPlays()
                + "\n\tAverage number of songs played per simulation to get duplicate: " + getAvgNumPlays());
        // Most played song: "Silent Night" by "Faster Pussy cat"
        System.out.println("\tMost played song:" + "\"" + mostPlayedSong.title
                + "\" by " + "\"" + mostPlayedSong.artist + "\":"
                //   All songs alphabetically by "Faster Pussy cat":
                //       "Silent Night" with 100000 plays
                + "\n\tAll songs alphabetically by: "
                + "\"" + mostPlayedSong.artist + "\":");
                alphabeticalSongs();
    }

    /**
     * main program that runs the dance marathon
     * @param args command line arguments : the filename to read
     * @throws FileNotFoundException if the incorrect number of files are gathered
     */
    public static void main(String[] args) throws FileNotFoundException {
        // check how many arguments we get by checking the length of the array
        if (args.length != 1) {
            System.out.println("Usage: Java DanceMarathon filename");
        } else {
            //read input file (can throw an exception)
            DanceMarathon jukebox = new DanceMarathon(args[0]);
            // simulation main loop will only run if there is no error
            // reading the input file
            jukebox.marathon();
        }
    }

}