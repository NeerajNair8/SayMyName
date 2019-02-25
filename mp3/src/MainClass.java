
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
public class MainClass {
    FileInputStream FIS;
    BufferedInputStream BIS;
    public String filepath;
    long total ;
    long pausel ;
    public Player player;
    String previnst;
    
    public void play(String path)       
    {
        try {
            FIS = new FileInputStream(path);
            BIS = new BufferedInputStream(FIS);
            total=FIS.available();
            player = new Player(BIS);
            filepath=path;
            
            
            } catch (JavaLayerException | FileNotFoundException ex) {} catch (IOException ex) { 
        } 
        new Thread()
            {
                @Override
                public void run()
                {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                       
                    }
                }    
            }.start();
        previnst="play";
    } 
    public void stop()
    {
        if(player != null)
        {
            pausel=0;
            total=0;
            player.close();
            previnst ="stop";
            
        }
    }
        public void pause()
    {
        
        
        if(player != null)
        {
            try {
                pausel= FIS.available();
            } catch (IOException ex) {

            }
            player.close();
            previnst="pause";
        }
    }
            public void resume()       
    {
        if(previnst=="pause")
        {
        try {
            FIS = new FileInputStream(filepath);
            BIS = new BufferedInputStream(FIS);
            player = new Player(BIS);
            FIS.skip(total-pausel);
            
            
            } catch (JavaLayerException | FileNotFoundException ex) {} catch (IOException ex) {} 
        new Thread()
            {
                @Override
                public void run()
                {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                       
                    }
                }    
            }.start();
        previnst="resume";
        }
        else if(previnst=="stop")
        {
            play(filepath);
            previnst="pause";
        }
            
    }

}
