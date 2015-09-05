package com.schibsted.hackathons.example.gotquotes.services;

import com.schibsted.hackathons.example.gotquotes.exceptions.DilbertException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * Created by Cathepina on 05/09/2015.
 */
public class GilbertImageService {

    private static final String urlDilbert = "http://dilbert.com/strip/";
    private final String dateTimeFormatPattern = "yyyy-MM-dd";

    private String getImageUrl(String url) {
        String result = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements element = doc.select(".img-responsive");
            result = element.hasAttr("src") ?
                    element.attr("src")
                    : null;
        } catch (IOException e) {
            throw new DilbertException();
        }
        return result;
    }

    private String createUrl(LocalDate date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormatPattern);
        return  urlDilbert + date.format(formatter);
    }

    public String getStripUrl(LocalDate date) {
        String result = null;
        String url = this.createUrl(date);
        result = this.getImageUrl(url);
        if(result == null) {
            throw new DilbertException();
        }
        return result;
    }
}
