package com.pmk.twovidzoneclip.youTube;

import java.util.List;

public class YouTubeTester {
 
    public static void main(String[] args) throws Exception {
  
        String clientID = "2vidz1Clip";
        String validVideo = "3fGgoWNBQZ0";
        String notValidVideo = "nr2enzcl83o";
        int maxResults = 1;
        boolean filter = true;
        int timeout = 2000;
  
        YouTubeManager ym = new YouTubeManager(clientID);
  
        boolean exists = ym.checkUrl(validVideo);
        System.out.println("valide : "+exists);
        exists = ym.checkUrl(notValidVideo);
        System.out.println("non valide : "+exists);
  
    }

}