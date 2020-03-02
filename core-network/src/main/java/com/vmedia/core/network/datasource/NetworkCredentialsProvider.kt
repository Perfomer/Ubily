package com.vmedia.core.network.datasource

import com.vmedia.core.network.obj.RssToken
import com.vmedia.core.network.obj.Token

interface NetworkCredentialsProvider {
    val userId: Long
    val token: Token
    val rssToken: RssToken
}

interface MutableNetworkCredentialsProvider : NetworkCredentialsProvider {
    override var userId: Long
    override var token: Token
    override var rssToken: RssToken
}