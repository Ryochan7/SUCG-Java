/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepmaniaunlock.controllers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import stepmaniaunlock.Song;

/**
 *
 * @author ryochan7
 */
public class LoadUnlockFileController {
    private File unlockFile;

    public LoadUnlockFileController (String destDir) {
        File temp = new File (destDir, "Unlocks.dat");
        if (temp.exists() && temp.canRead()) {
            unlockFile = temp;
        }
    }

    public void readUnlockFile (ArrayList<Song> songs) {
        if (unlockFile == null) {
            return;
        }

        try {
            ArrayList<String> titleList = new ArrayList<String> ();
            for (Song song: songs) {
                titleList.add(song.getName());
            }
            BufferedReader input = new BufferedReader (new FileReader (unlockFile));
            int[] changes = {0,0,0,0,0};
            String readline = input.readLine();
            readline = readline.trim();
            String temp = "";
            while (readline != null) {
                if (readline.startsWith("#UNLOCK:")) {
                    //System.out.println ("READLINE: " + readline);
                    int startIndex = readline.indexOf("#UNLOCK:");
                    temp = readline.substring(startIndex + 8, (readline.length() - 1));
                    String[] match = temp.split(":", 2);
                    //System.out.println (match[0]);
                    String title = match[0];
                    //System.out.println (match[1]);
                    match = match[1].split(",");
                    //System.out.println (match[0]);
                    for (int i=0; i < match.length; i++) {
                        if (match[i].startsWith("AP=")) {
                            //System.out.println (match[i].split("AP=")[1]);
                            changes[0] = Integer.parseInt(match[i].split("AP=")[1]);
                        }
                        else if (match[i].startsWith("CS=")) {
                            changes[1] = Integer.parseInt(match[i].split("CS=")[1]);
                        }
                        else if (match[i].startsWith("DS=")) {
                            changes[2] = Integer.parseInt(match[i].split("DS=")[1]);
                        }
                        else if (match[i].startsWith("RO=")) {
                            changes[3] = Integer.parseInt(match[i].split("RO=")[1]);
                        }
                        else if (match[i].startsWith("SP=")) {
                            changes[4] = Integer.parseInt(match[i].split("SP=")[1]);
                        }
                    }
                    //System.out.println ("SOMETHING DARKSIDE");
                    int index = titleList.indexOf (title);
                    if (index != -1) {
                        Song song = songs.get(index);
                        song.setArcade (changes[0]);
                        song.setClear (changes[1]);
                        song.setDance (changes[2]);
                        song.setRoulette (changes[3]);
                        song.setSong (changes[4]);
                        song.setDefaults(changes);
                        //System.out.println ("ALTERED SONG: " + song.getName());
                    }
                }
                readline = input.readLine();
            }
            input.close();
        } catch (java.io.IOException e) {
            System.err.println (e.getMessage());
        }
    }
}
