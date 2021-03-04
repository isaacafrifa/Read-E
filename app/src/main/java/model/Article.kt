package model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/*
The 'Article' class represents one single article  */@Root(name = "item", strict = false) //strict set to false. This tells the parser to not throw an exception if an XML element is found for which no mapping is provided.
class Article {
    // Getters and Setters
    @Element(name = "title")
    var title: String? = null

    @Element(name = "pubDate")
    var date: String? = null

    @Element(name = "description")
    var description: String? = null

    @Element(name = "link")
    var link: String? = null

    @Element(name = "guid")
    var guid: String? = null

    //Image Variables
    @Element(name = "enclosure", required = false)
    var enclosure: String? = null

    @Element(name = "media:content", required = false)
    var mediaContentImage: String? = null

    @Element(name = "media:thumbnail", required = false)
    var mediaThumbnail: String? = null
    override fun toString(): String {
        return "Article{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", enclosure='" + enclosure + '\'' +
                ", mediaContentImage='" + mediaContentImage + '\'' +
                ", mediaThumbnail='" + mediaThumbnail + '\'' +
                '}'
    }
}
