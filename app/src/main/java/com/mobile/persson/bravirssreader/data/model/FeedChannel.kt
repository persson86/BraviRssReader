package com.mobile.persson.bravirssreader.data.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
class FeedChannel(
        @field:ElementList(inline = true, required = false)
        var feedItems: List<FeedItem>? = null
)