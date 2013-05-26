package com.pmk.twovidzoneclip.metier;

public class VidzUrl {

    private final String imageVidz;

    private final String soundVidz;

    public VidzUrl(final String imageVidz, final String soundVidz) {
        this.imageVidz = imageVidz;
        this.soundVidz = soundVidz;
    }

    public String getImageVidz() {
        return imageVidz;
    }

    public String getSoundVidz() {
        return soundVidz;
    }
}