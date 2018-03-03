package com.companyofs.planzsp1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupInterfaceIntegrationImpl {
    public String getText(String HtmlElement) {
        Document code = Jsoup.parse(HtmlElement);
        return code.text();
    }

    public String getElementByClass(String fromCode, String className, int index) {
        Document code = Jsoup.parse(fromCode);
        Elements els = code.getElementsByClass(className);
        if (els.size() > index)
            return els.get(index).toString();
        else
            return null;
    }

    public String getElementByTag(String fromCode, String tagName, int index) {
        Document code = Jsoup.parse(fromCode);
        Elements els = code.getElementsByTag(tagName);
        if (els.size() > index)
            return els.get(index).toString();
        else
            return null;
    }
    public String getHref(String htmlElement) {
        Document co = Jsoup.parse(htmlElement);
        Element el = co.select("a").first();
        String href = el.attr("href");
        return href;
    }
    public String parseCode(String code) {
        Document co = Jsoup.parse(code);
        return co.toString();
    }

    public boolean isSupported() {
        return true;
    }

}
