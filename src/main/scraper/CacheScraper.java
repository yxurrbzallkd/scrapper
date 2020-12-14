package scraper;

import java.io.IOException;

import java.sql.*;

public class CacheScraper implements Scraper {
    private Scraper scraper;
    private Connection connection;
    private Statement statement;

    public CacheScraper(Scraper scraper) throws SQLException{
        System.out.println("creating CacheScraper");
        this.scraper = scraper;
        //open database
        String db = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\main\\scraper\\cached.db";
        System.out.println("connecting to "+db);
        connection = DriverManager.getConnection(db);
        statement = connection.createStatement();
        statement.executeUpdate("drop table if exists cached");
        statement.executeUpdate("create table if not exists cached (url string, page string)");
    }

    public String scrape(String url) throws SQLException, IOException {
        String pageText = checkPageInCache(url);
        if (pageText.length() != 0) {
            System.out.println("returning cached "+url);
            return pageText;
        }
        pageText = scraper.scrape(url);
        storeInCache(url, pageText);
        return pageText;
    }

    public String checkPageInCache(String url) throws SQLException {
        ResultSet rs = statement.executeQuery("select url, page from cached where url='"+url+"'");
        String yrl;
        String pageText;
        while (rs.next()) {
            yrl = rs.getString("url");
            pageText = rs.getString("page");
            if (pageText != null) {
                return pageText;
            }
        }
        return "";
    }

    public void storeInCache(String url, String pageText) throws SQLException {
        //System.out.println("storing "+url+" in cache");
        //implement storing in cache
        //statement.executeUpdate("insert into cached(url) values ('"+url+"')");
        PreparedStatement pstmt =
                connection.prepareStatement("insert into cached(url, page) values (?, ?)");
        pstmt.setString(2, pageText);
        pstmt.setString(1, url);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
