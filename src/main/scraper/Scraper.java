package scraper;

import java.io.IOException;
import java.sql.SQLException;

public interface Scraper {
    public String scrape(String url) throws IOException, SQLException;
}
