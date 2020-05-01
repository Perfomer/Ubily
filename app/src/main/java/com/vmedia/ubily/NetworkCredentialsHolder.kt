package com.vmedia.ubily

import com.vmedia.core.common.android.obj.creds.RssToken
import com.vmedia.core.common.android.obj.creds.Token
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider

class NetworkCredentialsHolder : MutableNetworkCredentialsProvider {

    override var userId: Long = 0L

    override var token: Token = Token()

    override var rssToken: RssToken = RssToken()

}