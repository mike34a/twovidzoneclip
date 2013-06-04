package com.pmk.twovidzoneclip.service.impl;

import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;
import com.pmk.twovidzoneclip.service.VidzUrlsService;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if("invalid url".equals(videoID) || "invalid url".equals(soundID)) return false;
        return vidzUrlsDAO.addVideo(title, videoID, soundID);
    }
}
