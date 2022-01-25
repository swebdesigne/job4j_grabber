package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.Parse;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    private static final int END = 5;
    private final DateTimeParser dateTimeParser;
    private List<Post> posts = new ArrayList<>();

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private void parsePage(String link) throws IOException, ParseException {
        for (int i = 1; i <= END; i++) {
            Document doc = Jsoup.connect(link + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                posts.add(detail(td.child(0).attr("href")));
            }
        }
    }

    @Override
    public List<Post> list(String link) throws IOException, ParseException {
        parsePage(link);
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException, ParseException {
        Document doc = Jsoup.connect(link).get();
        String title = doc.select(".messageHeader").eq(0).text().trim();
        String desc = doc.select(".msgBody").eq(1).text();
        String date = doc.select(".msgFooter").eq(0).text().split("\\[")[0].trim();
        return new Post(title, link, desc, dateTimeParser.parse(date));
    }
}
