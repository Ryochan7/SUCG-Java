/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepmaniaunlock.controllers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import stepmaniaunlock.Song;

/**
 *
 * @author ryochan7
 */
public class WriteUnlockFileController {
    private File destFile;

    public WriteUnlockFileController (String dirLocation) {
        File temp = new File (dirLocation, "Data");
        if (temp.isDirectory() && temp.canWrite()) {
            destFile = new File (temp, "Unlocks.dat");
        }
    }

    public void writeUnlockFile (ArrayList<Song> songs) {
        if (destFile == null) {
            return;
        }

        if (destFile.exists()) {
            File backup = new File (destFile.getParentFile(), "Unlocks.dat.backup");
            FileChannel source = null;
            FileChannel destination = null;
            try {
                source = new FileInputStream(destFile).getChannel();
                destination = new FileOutputStream(backup).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
            } catch (java.io.IOException e) {
                System.err.println (e.getMessage());
                System.out.println ("Could not create backup file");
            }
        }

        File output = destFile;
        String message = "// Test file for Miryo's new Unlock system.\n" +
                "// Songs/courses are matched by name, not folder name\n" +
                "//\n" +
                "// System modified by curewater\n" +
                "//\n" +
                "// The following methods are implemented:\n" +
                "//\n" +
                "// DP - Dance points, e.g. 2 per perfect, 1 per great, etc.\n" +
                "// AP - Arcade points, like MAX2 arcade\n" +
                "// SP - Song points, like MAX2 Home\n" +
                "// CS - Clear Stages, like 5th Home\n" +
                "// RO - Roulette, unlocked by landing on it in roulette.\n" +
                "//\n" +
                "// To be implemented: Toasty, Clear Extra Stages, Fail Extra Stages.\n" +
                "//\n" +
                "// Courses will be implemented eventually, but not yet.\n" +
                "// Songs are matched by title + subtitle.\n" +
                "//\n" +
                "// Any line not starting with #UNLOCK will be ignored.\n" +
                "//\n" +
                "// Sample lines:\n" +
                "//\n" +
                "// #UNLOCK:xenon:AP=10;\n" +
                "// Song xenon requires 10 arcade points to unlock.\n" +
                "//\n" +
                "// #UNLOCK:the Legend of MAX:RO=3\n" +
                "// Song \"the Legend of Max\" is in roulette slot 3.\n" +
                "//\n" +
                "// #UNLOCK:PARANOIA SURVIVOR MAX:CS=30,RO=3;\n" +
                "// Song \"Paranoia Surivvor MAX\" is locked either by clearing\n" +
                "// 30 stages, or by landing on it in roulette slot 3.\n" +
                "//\n" +
                "// #UNLOCK:POP 4:CS=50;\n" +
                "// Course Pop 4 is locked until 50 stages are cleared.\n" +
                "// (Stepmania doesn't distinguish between song and course titles\n" +
                "// yet.\n" +
                "//\n" +
                "\n";

        try {
            FileWriter writefile = new FileWriter (output);
            writefile.write(message);

            for (Song song: songs) {
                ArrayList<String> stringStuff = new ArrayList<String> ();
                if (song.getArcade() != 0) {stringStuff.add("AP=" + song.getArcade());}
                if (song.getClear() != 0) {stringStuff.add("CS=" + song.getClear());}
                if (song.getDance() != 0) {stringStuff.add("DS=" + song.getDance());}
                if (song.getRoulette() != 0) {stringStuff.add("RO=" + song.getRoulette());}
                if (song.getSong() != 0) {stringStuff.add("SP=" + song.getSong());}
                int changesSize = stringStuff.size ();
                if (changesSize > 0) {
                    writefile.write ("#UNLOCK:" + song.getName() + ":");
                    for (int i=0; i < changesSize; i++) {
                        writefile.write (stringStuff.get(i));
                        if (i != (changesSize - 1)) {
                            writefile.write (",");
                        }
                    }
                    writefile.write (";\n");
                }
            }
            writefile.write ("\n");
            writefile.close();
        } catch (java.io.IOException e) {
            System.out.println (e.getMessage());
        }
    }
}
