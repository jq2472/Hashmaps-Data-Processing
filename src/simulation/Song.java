package simulation;

/**
 * The song class
 * @author jolin qiu
 */
public class Song implements Comparable<Song>{

    /**
     * the artist of the song
     */
    public String artist;

    /**
     * the title of the song
     */
    public String title;

    /***
     * the constructor for the Song object
     * @param artist the artist of the song
     * @param title the title of the song
     */
    public Song(String artist, String title){
        this.artist = artist;
        this.title = title;
    }

    /**
     * gets the artist of the song
     * @return (str) artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * gets the title of the song
     * @return (str) title
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the string representation of the song object
     * @return (str) the title of the song
     */
    @Override
    public String toString(){
        return "Artist: " + artist +
                ", Title: " + title;
    }

    /**
     * return whether or not the song is the same as another song
     * @param other the other song to be compared to
     * @return (boolean) true if the songs are equal and false otherwise
     */
    @Override
    public boolean equals(Object other){
        boolean result = false;
        if (other instanceof Song otherSong){
            // Two songs are equal if both the artist and the song title are the same
            result = this.artist.equals(otherSong.artist) &&
                    this.title.equals(otherSong.title);
        }
        return result;
    }

    /**
     * returns the hashcode of the song
     * @return (int) the hashcode
     */
    @Override
    public int hashCode() {
        return this.artist.hashCode() +
                this.title.hashCode();
    }

    /**
     * returns how to songs compare to one another in its natural order
     * (alphabetically or numerically greater than, less than, etc.)
     * @param other the object to be compared.
     * @return other / comparable value
     */
    @Override
    public int compareTo(Song other) {
        int result = this.artist.compareTo(other.artist);
        if (result == 0){
            result = this.title.compareTo(other.title);
        }
        return result;
    }

}
