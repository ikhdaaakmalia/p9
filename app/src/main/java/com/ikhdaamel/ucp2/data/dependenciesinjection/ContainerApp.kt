package com.ikhdaamel.ucp2.data.dependenciesinjection

import android.content.Context
import com.ikhdaamel.ucp2.data.database.KRSDatabase
import com.ikhdaamel.ucp2.repository.LocalRepoDosen
import com.ikhdaamel.ucp2.repository.LocalRepoMataKuliah
import com.ikhdaamel.ucp2.repository.RepoDosen
import com.ikhdaamel.ucp2.repository.RepoMataKuliah

interface InterfaceContainerApp {
    val repoDosen: RepoDosen
    val repoMataKuliah: RepoMataKuliah
}

class ContainerApp (private val context: Context) : InterfaceContainerApp{
    override val repoDosen: RepoDosen by lazy {
        LocalRepoDosen(KRSDatabase.getDatabase(context).dosenDao())
    }
    override val repoMataKuliah: RepoMataKuliah by lazy {
        LocalRepoMataKuliah(KRSDatabase.getDatabase(context).mataKuliahDao())
    }
}