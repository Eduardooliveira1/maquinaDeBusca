package com.maquinadebusca.app.model.service;

import com.maquinadebusca.app.model.Documento;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

public class ServicoColetor {

    public List executar() {
        List<Documento> documentos = new LinkedList ();
        try {
            List<URL> urlsSementes = new LinkedList();
            urlsSementes.add(new URL("https://www.youtube.com/"));
            urlsSementes.add(new URL("https://www.facebook.com"));
            urlsSementes.add(new URL("https://www.twitter.com"));
            for (URL url : urlsSementes) {
                 documentos.add (this.coletar(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documentos;
    }

    public Documento coletar(URL url) {
        Documento d = new Documento();
        try {
            Document doc = Jsoup.connect(url.toString()).get();
            Elements links = doc.select("a[href]");
            d.setUrl(url);
            d.setTexto(doc.html());
            d.setVisao(doc.text());
            List<String> urls = new LinkedList();

            for (Element link : links) {
                if ((!link.attr("abs:href").equals("") && (link.attr("abs:href") != null))) {
                    urls.add(link.attr("abs:href"));
                }
            }
            d.setUrls(urls);
        } catch (Exception e) {
            System.out.println("Erro ao coletar a página.");
            e.printStackTrace();
        }
        return d;
    }
}
