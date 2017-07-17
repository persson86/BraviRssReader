package com.mobile.persson.bravirssreader.data.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "item", strict = false)
class FeedItem(
        @field:Element(name = "pubDate")
        var pubDate: String? = null,

        @field:Element(name = "title")
        var title: String? = null,

        @field:Element(name = "link")
        var link: String? = null,

        @field:Element(name = "description")
        var description: String? = null,

        var rss: String? = null

/*        @field:ElementList(entry = "link", inline = true, required = false)
        var feedLinks: List<Link>? = null*/
)

class Link(
        @field:Attribute(required = false)
        var href: String? = null,

        @field:Attribute(required = false)
        var rel: String? = null,

        @field:Attribute(name = "type", required = false)
        var contentType: String? = null,

        @field:Text(required = false)
        var link: String? = null
)
