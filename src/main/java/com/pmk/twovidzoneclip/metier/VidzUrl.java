package com.pmk.twovidzoneclip.metier;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class VidzUrl {

    private transient Date date;

    private final String imageUrl;

    private final String soundUrl;

    public VidzUrl(final Date date, final String imageUrl, final String soundUrl) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.soundUrl = soundUrl;
    }

    public VidzUrl(String imageUrl, String soundUrl) {
        this.imageUrl = imageUrl;
        this.soundUrl = soundUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VidzUrl vidzUrl = (VidzUrl) o;

        return Objects.equals(vidzUrl.getImageUrl(), this.getImageUrl())
                && Objects.equals(vidzUrl.getSoundUrl(), this.getSoundUrl())
                && Objects.equals(vidzUrl.getDate().toString(), this.getDate().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, soundUrl, date.getTime());
    }

    @Override
    public String toString() {
        return "VidzUrl{" +
                "date=" + date.getTime() +
                ", imageUrl='" + imageUrl + '\'' +
                ", soundUrl='" + soundUrl + '\'' +
                '}';
    }
}