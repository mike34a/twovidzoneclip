package com.pmk.twovidzoneclip.persistence;

import com.pmk.twovidzoneclip.metier.VidzUrl;

import java.util.List;

public interface VidzUrlsDAO {
    List<VidzUrl> getUrlsForTheNthPage(Integer page, Integer numberOfResults);
}
