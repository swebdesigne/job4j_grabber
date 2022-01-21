package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    private int page = 0;

    private void parsePage(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".forumTable").select("tr");
        String next = doc.select(".sort_options tbody tr td").select("a").eq(page++).attr("href");
        for (Element td : row) {
            System.out.println(String.format("Name %s, link %s, date %s",
                    td.child(1).children().text(),
                    td.child(1).children().attr("href"),
                    td.child(5).text()
            ));
        }
        if (page < 5) {
            parsePage(next);
        }
    }

    public static void main(String[] args) throws IOException {
        SqlRuParse s = new SqlRuParse();
        s.parsePage("https://www.sql.ru/forum/job-offers/");
    }
}
