package com.mobile.persson.bravirssreader.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class Feed(
        @field:Element(name = "channel", required = false)
        var channel: FeedChannel? = null,

        var url: String? = null
)