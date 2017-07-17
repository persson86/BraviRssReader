package com.mobile.persson.bravirssreader.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class FeedItem(
        /*@Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
        @Element()
        var atomLink : AtomLink*/

        @field:Element(name = "pubDate")
        var pubDate: String? = null,

        @field:Element(name = "title")
        var title: String? = null,

        @field:Element(name = "link")
        var link: String? = null,

        @field:Element(name = "description")
        var description: String? = null,

        var rss: String? = null
)