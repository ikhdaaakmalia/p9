package com.ikhdaamel.ucp2.data.dependenciesinjection

import com.ikhdaamel.ucp2.repository.RepoDosen
import com.ikhdaamel.ucp2.repository.RepoMataKuliah

interface InterfaceContainerApp {
    val repoDosen: RepoDosen
    val repoMataKuliah: RepoMataKuliah
}