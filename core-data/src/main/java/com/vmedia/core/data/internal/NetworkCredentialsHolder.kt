package com.vmedia.core.data.internal

import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.obj.RssToken
import com.vmedia.core.network.obj.Token

class NetworkCredentialsHolder : MutableNetworkCredentialsProvider {

    override var userId: Long = 0L

    override var token: Token = Token("", "")

    override var rssToken: RssToken = RssToken("", "")

}