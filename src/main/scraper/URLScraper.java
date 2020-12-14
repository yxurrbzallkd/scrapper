package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class URLScraper implements Scraper {
    public URLScraper() { System.out.println("creating URLScraper");
    }


    public String scrape(String url) throws IOException {
        //send real request
        Document doc = Jsoup.connect(url).get();
        return doc.toString();
    }
}
