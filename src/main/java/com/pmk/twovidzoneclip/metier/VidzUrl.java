package com.pmk.twovidzoneclip.metier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class VidzUrl {

    private Date date;

    private final String imageUrl;

    private final String soundUrl;
    
    private final String title;

    public VidzUrl(final Date date, final String imageUrl, final String soundUrl, final String title) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.soundUrl = soundUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }
    
    public String getTitle() {
        return title;
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
                && Objects.equals(vidzUrl.getDate().toString(), this.getDate().toString())
                && Objects.equals(vidzUrl.getTitle(), this.getTitle());
    }

    @Override
    public String toString() {
        return "VidzUrl{" +
                "date=" + date +
                ", imageUrl='" + imageUrl + '\'' +
                ", soundUrl='" + soundUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String toJson() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        final String strDate = dateFormat.format(date);

        return "{"
                + "  \"date\": \"" + strDate 
                + "\", \"image_url\": \"" 
                + imageUrl + "\", \"sound_url\": \"" 
                + soundUrl + "\",\"title\": \"" 
                + title 
                + "\"}";
    }
    
    public String getKey() {
        return imageUrl+" "+soundUrl;
    }
}