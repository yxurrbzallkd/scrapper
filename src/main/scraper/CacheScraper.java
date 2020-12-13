package scraper;

import java.io.IOException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class CacheScraper implements Scraper {
    private Scraper scraper;
    private Connection connection;
    private Statement statement;

    public CacheScraper(Scraper scraper) throws SQLException{
        this.scraper = scraper;
        //open database
        String db = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\cached.db";
        connection = DriverManager.getConnection(db);
        System.out.println("successfully connected to database");
        this.statement = connection.createStatement();
    }

    public String scrape(String url) throws SQLException, IOException {
        String pageText = checkPageInCache(url);
        if (pageText.length() != 0) {return pageText;}
        pageText = scraper.scrape(url);
        storeInCache(url, pageText);
        return pageText;
    }

    public String checkPageInCache(String url) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from url where url='"+url+"'");
        int id = rs.getInt("id");
        return "";
    }

    public void storeInCache(String url, String pageText) throws SQLException {
        //implement storing in cache
        statement.executeUpdate("insert into table values ("+url+", "+pageText+")");
    }
}
