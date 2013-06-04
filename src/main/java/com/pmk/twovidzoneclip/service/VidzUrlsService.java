package com.pmk.twovidzoneclip.service;

import com.pmk.twovidzoneclip.metier.VidzUrl;

import java.util.List;

public interface VidzUrlsService {
    List<VidzUrl> findVidzUrls(Integer page, Integer numberOfResults);
    Boolean addVideo(String title, String videoID, String soundID);
}
