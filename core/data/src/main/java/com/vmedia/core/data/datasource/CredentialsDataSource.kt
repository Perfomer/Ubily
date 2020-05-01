package com.vmedia.core.data.datasource

import com.vmedia.core.common.pure.obj.creds.Credentials
import io.reactivex.Completable
import io.reactivex.Single

interface CredentialsDataSource {

    fun getCredentials(): Single<Credentials>

    fun writeCredentials(credentials: Credentials): Completable

}