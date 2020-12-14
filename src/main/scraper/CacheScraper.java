package scraper;

import java.io.IOException;

import java.sql.*;

public class CacheScraper implements Scraper {
    private Scraper scraper;
    private Connection connection;
    private Statement statement;

    public CacheScraper(Scraper scraper) throws SQLException{
        this.scraper = scraper;
        //open database
        String db = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\main\\scraper\\cached.db";
        System.out.println(db);
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
        while (rs.next()) {
            String pageText = rs.getString("page");
            System.out.println(pageText);
            if (pageText != null) {
                return pageText;
            }
        }
        return "";
    }

    public void storeInCache(String url, String pageText) throws SQLException {
        System.out.println("storing "+url+" in cache");
        //implement storing in cache
        statement.executeUpdate("insert into cached(url) values ('"+url+"')");
        PreparedStatement pstmt =
                connection.prepareStatement("insert into cached(page) values (?)");
        pstmt.setString(1, pageText);
        statement.executeUpdate("insert into cached(url) values ('"+url+"')");
        pstmt.executeUpdate();
        pstmt.close();
    }
}
