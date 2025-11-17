package ar.edu.unlam.mobile.scaffolding.data.repositories.events

sealed interface FeedTuitsFilter {
    object AllTuits : FeedTuitsFilter

    object FavoriteTuits : FeedTuitsFilter

    object TuitsMadeByFavoriteUsers : FeedTuitsFilter
}
