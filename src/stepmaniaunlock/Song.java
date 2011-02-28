/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepmaniaunlock;

/**
 *
 * @author ryochan7
 */
public class Song {
    private static final int ARCADE_INDEX = 0;
    private static final int CLEAR_INDEX = 1;
    private static final int DANCE_INDEX = 2;
    private static final int ROULETTE_INDEX = 3;
    private static final int SONG_INDEX = 4;
    
    private String name = "";
    private String nameTrans = "";
    private int arcade = 0;
    private int clear = 0;
    private int dance = 0;
    private int roulette = 0;
    private int song = 0;
    private int[] defaults;

    public Song (String name, String nameTrans) {
        this.name = name;
        this.nameTrans = nameTrans;
        this.defaults = new int[] {this.arcade, this.clear, this.dance,
            this.roulette, this.song};
    }

    public Song (String name, String nameTrans, int arcade, int clear, int dance,
            int roulette, int song) {
        this (name, nameTrans);
        this.arcade = arcade;
        this.clear = clear;
        this.dance = dance;
        this.roulette = roulette;
        this.song = song;
        this.defaults = new int[] {this.arcade, this.clear, this.dance,
            this.roulette, this.song};
    }

    public void reset () {
        arcade = defaults[0];
        clear = defaults[1];
        dance = defaults[2];
        roulette = defaults[3];
        song = defaults[4];
    }

    public void unlockValues (int[] changes) {
        if (changes.length == 5) {
            defaults = changes;
            arcade = defaults[0];
            clear = defaults[1];
            dance = defaults[2];
            roulette = defaults[3];
            song = defaults[4];
        }
    }

    @Override
    public String toString () {
        return name;
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

    /**
     * @return the nameTrans
     */
    public String getNameTrans() {
        return nameTrans;
    }

    /**
     * @param nameTrans the nameTrans to set
     */
    public void setNameTrans(String nameTrans) {
        this.nameTrans = nameTrans;
    }

    /**
     * @return the arcade
     */
    public int getArcade() {
        return arcade;
    }

    /**
     * @return the clear
     */
    public int getClear() {
        return clear;
    }

    /**
     * @return the dance
     */
    public int getDance() {
        return dance;
    }

    /**
     * @return the roulette
     */
    public int getRoulette() {
        return roulette;
    }

    /**
     * @return the song
     */
    public int getSong() {
        return song;
    }

    /**
     * @param arcade the arcade to set
     */
    public void setArcade(int arcade) {
        this.arcade = arcade;
    }

    /**
     * @param clear the clear to set
     */
    public void setClear(int clear) {
        this.clear = clear;
    }

    /**
     * @param dance the dance to set
     */
    public void setDance(int dance) {
        this.dance = dance;
    }

    /**
     * @param roulette the roulette to set
     */
    public void setRoulette(int roulette) {
        this.roulette = roulette;
    }

    /**
     * @param song the song to set
     */
    public void setSong(int song) {
        this.song = song;
    }

    /**
     * @return the defaults
     */
    public int[] getDefaults() {
        return defaults;
    }

    /**
     * @param defaults the defaults to set
     */
    public void setDefaults(int[] defaults) {
        this.defaults = defaults;
    }
}
