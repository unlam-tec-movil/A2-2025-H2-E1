package ar.edu.unlam.mobile.scaffolding.data.datasources.network.response

data class UpdateResponse(
    val name: String,
    val avatar_url: String,
    val email: String
)

data class ProfileResponse(
    val name: String,
    val email: String,
    val avatar_url: String
)

data class FeedResponse(
    val tuits: List<TuitResponse>
)

data class TuitResponse(
    val id: Int,
    val message: String,
    val parent_id: Int,
    val author: String,
    val avatar_url: String,
    val likes: Int,
    val liked: Boolean,
    val date: String
)

data class SendTuitResponse(
    val message: String
)

data class LikeTuitResponse(
    val id: Int,
    val message: String,
    val parent_id: Int,
    val author: String,
    val avatar_url: String,
    val likes: Int,
    val liked: Boolean,
    val date: String
)

data class UnLikeTuitResponse(
    val id: Int,
    val message: String,
    val parent_id: Int,
    val author: String,
    val avatar_url: String,
    val likes: Int,
    val liked: Boolean,
    val date: String
)

