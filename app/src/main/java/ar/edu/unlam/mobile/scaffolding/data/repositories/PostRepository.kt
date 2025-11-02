package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.TuitBody
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import javax.inject.Inject

class PostRepository
    @Inject
    constructor(
        private val tuiterApi: TuiterApi,
    ) {
        suspend fun postTuit(message: String) {
            // Crear el objeto que la API espera
            val tuitBody = TuitBody(message = message)
            return tuiterApi.postTuit(tuitBody)
        }
    }
