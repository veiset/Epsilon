package epsilon.game;


import java.io.BufferedInputStream;
import java.net.URL;

import javazoom.jl.player.Player;

public class SoundPlayer {

    private Player player;
    private URL url;

    public SoundPlayer(String filename) {
        url = this.getClass().getResource(filename);

    }

    public void close() {
        if (player != null) {
            player.close();
        }
    }

    // play the MP3 file to the sound card
    public void play() {
        try {
            BufferedInputStream bis = (BufferedInputStream) url.getContent();
            player = new Player(bis);
        } catch (Exception e) {
            System.out.println("Problem playing file ");
            System.out.println(e);
        }

        // run in new thread to play in background
        // thread should maybe be handled different
        new Thread() {

            @Override
            public void run() {
                try {
                    player.play();
                    System.out.println("test");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }
    public void repeat() {
        if (player.isComplete()) {
            play();
        }
    }
}

