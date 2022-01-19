package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    public static void main(String[] args) throws IOException, IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".forumTable").select("tr");
        for (Element td : row) {
            System.out.println(String.format("Name %s, link %s, data %s",
                    td.child(1).children().text(),
                    td.child(1).children().attr("href"),
                    td.child(5).text()
            ));
        }
    }
}
