package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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

    public static void main(String[] args) throws IOException {
        SqlRuParse s = new SqlRuParse();
        s.parsePage("https://www.sql.ru/forum/job-offers/");
    }
}
