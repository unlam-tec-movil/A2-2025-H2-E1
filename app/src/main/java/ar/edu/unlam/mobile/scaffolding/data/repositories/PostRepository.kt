package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitsBorrador
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.TuitBody
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository
    @Inject
    constructor(
        private val tuiterApi: TuiterApi,
        private val tuitDao: TuiterDao,
    ) {
        suspend fun postTuit(message: String) {
            // Crear el objeto que la API espera
            val tuitBody = TuitBody(message = message)
            return tuiterApi.postTuit(tuitBody)
        }

        suspend fun guardarBorrador(message: String) {
            val tuitBody = TuitsBorrador(textoBorrador = message)
            return tuitDao.saveBorrador(tuitBody)
        }

        fun devolverBorradores(): Flow<List<TuitsBorrador>> = tuitDao.getBorrador()

        fun devolverBorradorString(texto: String): TuitsBorrador = tuitDao.getBorradorString(texto)

        suspend fun deleteBorradorPR(borrador: TuitsBorrador) = tuitDao.deleteBorrador(borrador)
    }
