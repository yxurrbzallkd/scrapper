import org.jsoup.nodes.Document;
import scraper.*;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Scraper s = new CacheScraper(new URLScraper());
        String scrape = s.scrape("https://keepcodeclean.com/web-scraping-using-java-jsoup/");
        //System.out.println(scrape);
        scrape = s.scrape("https://keepcodeclean.com/web-scraping-using-java-jsoup/");
        //System.out.println(scrape);
        /*
        String db = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\main\\scraper\\cached.db";
        System.out.println(db);
        Connection connection = DriverManager.getConnection(db);
        Statement statement = connection.createStatement();
        statement.executeUpdate("drop table if exists cached");
        statement.executeUpdate("create table if not exists cached (url string, page text)");
        String url = "https://keepcodeclean.com/web-scraping-using-java-jsoup/";
        Document doc = Jsoup.connect(url).get();
        String page = doc.toString();
        statement.executeUpdate("insert into cached(url) values ('"+url+"')");
        PreparedStatement pstmt =
                connection.prepareStatement("insert into cached(page) values (?)");
        pstmt.setString(1, page);
        System.out.println("insert into cached(page) values ('"+page+"')");
        pstmt.executeUpdate();
        pstmt.close();*/
    }
}
