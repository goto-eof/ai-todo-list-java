package com.andreidodu.aidemo.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.yaml.snakeyaml.util.Tuple;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UrlUtil {

    public static Set<String> findLinks(String url) throws IOException {
        Set<String> links = new HashSet<>();
        Document doc = Jsoup.connect(url)
                .userAgent("CompanyBrochureMaker")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        doc.select("a[href]")
                .forEach(element -> links.add(element.attr("href")));
        return links;
    }

    public static Tuple<String, String> getPageContent(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            return new Tuple<>("", "");
        }
        return new Tuple<>(doc.title(), doc.body().text());
    }
}
