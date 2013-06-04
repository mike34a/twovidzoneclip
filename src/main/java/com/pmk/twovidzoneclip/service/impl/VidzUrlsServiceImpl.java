package com.pmk.twovidzoneclip.service.impl;

import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;
import com.pmk.twovidzoneclip.service.VidzUrlsService;

import javax.inject.Inject;
import java.util.List;

import com.pmk.twovidzoneclip.youTube.YouTubeManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VidzUrlsServiceImpl implements VidzUrlsService {

    private VidzUrlsDAO vidzUrlsDAO;

    @Inject
    public VidzUrlsServiceImpl(VidzUrlsDAO vidzUrlsDAO) {
        this.vidzUrlsDAO = vidzUrlsDAO;
    }

    @Override
    public final List<VidzUrl> findVidzUrls(final Integer page, Integer numberOfResults) {
        return vidzUrlsDAO.getUrlsForTheNthPage(page, numberOfResults);
    }

    @Override
    public Boolean addVideo(String title, String videoID, String soundID) {
        YouTubeManager ym = new YouTubeManager();
        try {
            if("invalid url".equals(videoID) || "invalid url".equals(soundID) || !ym.checkUrl(videoID) || !ym.checkUrl(soundID)) return false;
        } catch (Exception ex) {
            Logger.getLogger(VidzUrlsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vidzUrlsDAO.addVideo(title, videoID, soundID);
    }
}
