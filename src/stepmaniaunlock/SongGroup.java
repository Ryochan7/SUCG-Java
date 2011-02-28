/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepmaniaunlock;

import java.util.ArrayList;

/**
 *
 * @author ryochan7
 */
public class SongGroup {
    private String name = "";
    private ArrayList<Song> songs = new ArrayList ();
    private int[] defaults;
    private int arcadePoints = 0;
    private int clearPoints = 0;
    private int dancePoints = 0;
    private int roulettePoints = 0;
    private int songPoints = 0;


    public SongGroup (String name, ArrayList<Song> songs) {
        this.name = name;
        this.songs = songs;
        this.defaults = new int[] {0, 0, 0, 0, 0};
    }

    public void reset () {
        System.out.print ("DEFAULTS: [");
        for (int i=0; i < defaults.length; i++) {
            System.out.print (defaults[i]);
            if (i != defaults.length-1) {
                System.out.print (",");
            }
        }
        System.out.println ("]");
        arcadePoints = defaults[0];
        clearPoints = defaults[1];
        dancePoints = defaults[2];
        roulettePoints = defaults[3];
        songPoints = defaults[4];

        for (Song song: songs) {
            song.reset();
            System.out.println (song);
        }
    }

    public void changeSongs () {
        for (Song song: songs) {
            song.setArcade(arcadePoints);
            song.setClear(clearPoints);
            song.setDance(dancePoints);
            song.setRoulette(roulettePoints);
            song.setSong(songPoints);
        }
    }

    public ArrayList<Song> getSongs () {
        return songs;
    }

    @Override
    public String toString () {
        return this.name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void addSong (Song song) {
        songs.add (song);
    }

    /**
     * @return the defaults
     */
    public int[] getDefaults() {
        return defaults;
    }

    /**
     * @return the arcadePoints
     */
    public int getArcadePoints() {
        return arcadePoints;
    }

    /**
     * @param arcadePoints the arcadePoints to set
     */
    public void setArcadePoints(int arcadePoints) {
        this.arcadePoints = arcadePoints;
    }

    public int getClearPoints() {
        return clearPoints;
    }

    public void setClearPoints(int clearPoints) {
        this.clearPoints = clearPoints;
    }

    public int getDancePoints() {
        return dancePoints;
    }

    public void setDancePoints(int dancePoints) {
        this.dancePoints = dancePoints;
    }

    public int getRoulettePoints() {
        return roulettePoints;
    }

    public void setRoulettePoints(int roulettePoints) {
        this.roulettePoints = roulettePoints;
    }

    public int getSongPoints() {
        return songPoints;
    }

    public void setSongPoints(int songPoints) {
        this.songPoints = songPoints;
    }
}
