import scraper.*;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        //Scraper s = new URLScraper();

        Scraper s = new CacheScraper(new URLScraper());
        String db = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\cached.db";
        Connection connection = DriverManager.getConnection(db);
        Statement statement = connection.createStatement();
        String url = "https://www.wikipedia.org/";
        String pageText = s.scrape(url);
        statement.executeUpdate("insert into table values ("+url+", "+pageText+")");
    }
}
