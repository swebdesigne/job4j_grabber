package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    private static final int END = 5;

    private void parsePage(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements next = doc.select(".sort_options tbody tr td").select("a");
        for (int i = 0; i < END;) {
            Elements row = doc.select(".forumTable").select("tr");
            for (Element td : row) {
                System.out.println(String.format("Name %s, link %s, date %s",
                        td.child(1).children().text(),
                        td.child(1).children().attr("href"),
                        td.child(5).text()
                ));
            }
            doc = Jsoup.connect(next.eq(i++).attr("href")).get();
        }
    }

    public static void main(String[] args) throws IOException {
        SqlRuParse s = new SqlRuParse();
        s.parsePage("https://www.sql.ru/forum/job-offers/");
    }
}
