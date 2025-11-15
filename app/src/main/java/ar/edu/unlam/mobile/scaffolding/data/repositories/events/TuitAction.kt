package ar.edu.unlam.mobile.scaffolding.data.repositories.events

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit

sealed interface TuitAction {
    data class FavoriteUser(
        val tuit: Tuit,
    ) : TuitAction

    data class FavoriteTuit(
        val tuit: Tuit,
    ) : TuitAction
}
