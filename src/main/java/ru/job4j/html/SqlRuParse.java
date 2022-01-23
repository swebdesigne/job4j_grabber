package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.SimpleFormatter;

public class SqlRuParse {
    private static final int END = 5;

    private void parsePage(String link) throws IOException {
        for (int i = 1; i <= END; i++) {
            Document doc = Jsoup.connect(link + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                System.out.println(String.format("Name %s, link %s, date %s",
                        td.child(0).text(),
                        td.child(0).attr("href"),
                        td.lastElementSibling().text()
                ));
            }
        }
    }

    private String[] loadingPost(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        String desc = doc.select(".msgBody").eq(1).text();
        String date = doc.select(".msgFooter").eq(0).text().split("\\[")[0].trim();
        return new String[] {desc, date};
    }

    public static void main(String[] args) throws IOException {
        SqlRuParse s = new SqlRuParse();
        s.parsePage("https://www.sql.ru/forum/job-offers/");
        s.loadingPost("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
    }
}
