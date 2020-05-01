package com.vmedia.core.network.datasource

import com.vmedia.core.common.android.obj.creds.RssToken
import com.vmedia.core.common.android.obj.creds.Token

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