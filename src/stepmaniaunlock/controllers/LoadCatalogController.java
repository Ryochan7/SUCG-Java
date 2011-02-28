/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepmaniaunlock.controllers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import stepmaniaunlock.SongGroup;
import stepmaniaunlock.Song;

/**
 *
 * @author ryochan7
 */
public class LoadCatalogController {
    private static final int SM_FILE = 0;
    private static final int DWI_FILE = 1;
    private String location;
    private File mainDir;
    private JLabel uiModel;


    public LoadCatalogController (String location, JLabel textField) {
        mainDir = new File (location);
        if (mainDir.isDirectory()) {
            this.location = location;
        }
        else {
            mainDir = null;
        }
        this.uiModel = textField;
    }

    public ArrayList<SongGroup> loadSongs () {
        ArrayList<SongGroup> groups = new ArrayList<SongGroup> ();
        ArrayList<Song> songs = new ArrayList<Song> ();
        SongGroup group;

        Date startTime = new Date ();
        File songDir = new File (mainDir, "Songs");
        System.out.println ("Start Time: " + startTime);
        System.out.println ("Songs Dir: " + songDir.getAbsolutePath());
        if (!songDir.exists()) {
            return null;
        }
        File[] dirs = songDir.listFiles();
        List<File> sortedDirs = Arrays.asList(dirs);
        Collections.sort (sortedDirs, new Comparator<File> () {
            public int compare (File file1, File file2) {
                return file1.getAbsolutePath().compareToIgnoreCase(file2.getAbsolutePath());
            }
        });
        for (int i=0; i < dirs.length; i++) {
            File temp = new File (dirs[i].getAbsolutePath());
            boolean group_dir = false;
            if (temp.isDirectory()) {
                //System.out.println ("Group Shit: " + temp.getAbsolutePath());
                group_dir = true;
            }
            if (group_dir) {
                ArrayList<File> simFiles = new ArrayList<File> ();
                File[] songDirs = temp.listFiles();
                for (int j=0; j < songDirs.length; j++) {
                    if (songDirs[j].isDirectory()) {
                        File songDirTemp = new File (songDirs[j].getAbsolutePath());
                        File[] songFiles = songDirTemp.listFiles();
                        List<File> fileList = Arrays.asList(songFiles);
//                        for (File narg: fileList) {
//                            System.out.println ("FILE: " + narg.getAbsolutePath());
//                        }
                        Collections.sort(fileList, new Comparator<File> () {
                            public int compare (File file1, File file2) {
                                return file1.getAbsolutePath().compareToIgnoreCase(file2.getAbsolutePath());
                            }
                        });

                        Collections.reverse(fileList);
//                        for (File narg: fileList) {
//                            System.out.println ("SORTED FILE: " + narg.getAbsolutePath());
//                        }

                        boolean fileFound = false;
                        for (int k=0; k < fileList.size () && !fileFound; k++) {
                            if (fileList.get(k).getName().toLowerCase().endsWith((".sm"))) {
                                //System.out.println ("In Song Dir: " + fileList.get(k).getAbsolutePath());
                                simFiles.add(fileList.get(k));
                                fileFound = true;
                            }
                            else if (fileList.get(k).getName().toLowerCase().endsWith(".dwi")) {
                                //System.out.println ("In Song Dir: " + fileList.get(k).getAbsolutePath());
                                simFiles.add(fileList.get(k));
                                fileFound = true;
                            }
                        }
                    }
                }
                if (simFiles.size() > 0) {
                    ArrayList<Song> songList = readSimFiles (simFiles);
                    if (songList.size() > 0) {
                        Collections.sort (songList, new Comparator<Song> () {
                            public int compare (Song song1, Song song2) {
                                return song1.getName().compareToIgnoreCase(song2.getName());
                            }
                        });
                        groups.add (new SongGroup (temp.getName(), songList));
                    }
                }
            }
        }

        Date endTime = new Date ();
        System.out.println ("End time: " + endTime);
        System.out.println ("Diff: " + (endTime.getTime() - startTime.getTime()) + " ms");
        return groups;
    }

    private ArrayList<Song> readSimFiles (ArrayList<File> files) {
        ArrayList<Song> songs = new ArrayList<Song> ();
        for (File file: files) {
            int filetype;
            String title = "";
            String subtitle = "";
            String titleTrans = "";
            String subtitleTrans = "";

            if (file.getName().toLowerCase().endsWith(".sm")) {
                filetype = SM_FILE;
            }
            else {
                filetype = DWI_FILE;
            }

            BufferedReader input = null;
            try {
                //System.out.println ("ATTEMPTING TO OPEN FILE: " + file.getAbsolutePath());
                //uiModel.setText(file.getAbsolutePath());
                input = new BufferedReader (new FileReader (file));
                String readline;
                readline = input.readLine();

                while (readline != null) {
                    if (filetype == SM_FILE) {
                        if (readline.indexOf("#TITLE:") != -1) {
                            int startIndex = readline.indexOf("#TITLE:");
                            title = readline.substring(startIndex+7, readline.length()-1);
                            //System.out.println ("SONG TITLE: " + title);
                        }
                        else if (readline.indexOf("#SUBTITLE:") != -1) {
                            int startIndex = readline.indexOf("#SUBTITLE:");
                            subtitle = readline.substring(startIndex+10, readline.length()-1);
                            //System.out.println ("SONG SUBTITLE: " + subtitle);
                        }
                        else if (readline.indexOf("#TITLETRANSLIT:") != -1) {
                            int startIndex = readline.indexOf("#TITLETRANSLIT:");
                            titleTrans = readline.substring(startIndex+15, readline.length()-1);
                            //System.out.println ("SONG TITLETRANSLIT: " + titleTrans);
                        }
                        else if (readline.indexOf("#SUBTITLETRANSLIT:") != -1) {
                            int startIndex = readline.indexOf("#SUBTITLETRANSLIT:");
                            subtitleTrans = readline.substring(startIndex+18, readline.length()-1);
                            //System.out.println ("SONG SUBTITLETRANSLIT: " + subtitleTrans);
                        }

                    }
                    else if (filetype == DWI_FILE) {
                        int startIndex = readline.indexOf("#TITLE:");
                        if (readline.indexOf("#TITLE:") != -1) {
                            title = readline.substring(startIndex+7, readline.length()-1);
                            //System.out.println ("SONG TITLE: " + title);
                        }
                    }
                    readline = input.readLine();
                }
                input.close();
            } catch (java.io.IOException e) {
                System.err.println ("The following file could not be read: " + file.getAbsolutePath());
            }
            if (!title.isEmpty() && titleTrans.isEmpty()) {
                titleTrans = title;
            }
            else if (!subtitle.isEmpty() && subtitleTrans.isEmpty()) {
                subtitleTrans = subtitle;
            }

            if (!title.isEmpty() && subtitle.isEmpty() && subtitleTrans.isEmpty()) {
                songs.add (new Song (title, titleTrans));
            }
            else if (!title.isEmpty() && subtitle.isEmpty()) {
                songs.add (new Song (title, titleTrans + " " + subtitleTrans));
            }
            else if (!title.isEmpty()) {
                songs.add (new Song (title + " " + subtitle, titleTrans +  " " + subtitleTrans));
            }
        }
        return songs;
    }
}
