package com.pmk.twovidzoneclip.service.impl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pmk.twovidzoneclip.injection.VidzUrlsTestingModule;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;
import com.pmk.twovidzoneclip.persistence.impl.VidzUrlsDAOImpl;
import com.pmk.twovidzoneclip.service.VidzUrlsService;
import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class VidzUrlsServiceImplTest {

    private static VidzUrlsServiceImpl vidzUrlsService;

    private static Injector injector;

    @BeforeClass
    public static void staticSetUp() throws Exception {
        injector = Guice.createInjector(new VidzUrlsTestingModule());
        vidzUrlsService = (VidzUrlsServiceImpl)injector.getInstance(VidzUrlsService.class);
    }

    @Test
    public void should_find_all_urls_of_page_1() {
        //GIVEN/WHEN
        final List<VidzUrl> vidzUrls = vidzUrlsService.findVidzUrls(1, 10);

        //THEN
        assertThat(vidzUrls).isNotEmpty();
    }

    @Test
    public void should_not_find_any_url_on_page_10() {
        //GIVEN/WHEN
        final List<VidzUrl> vidzUrls = vidzUrlsService.findVidzUrls(10, 10);

        //THEN
        assertThat(vidzUrls).isNullOrEmpty();
    }
}
