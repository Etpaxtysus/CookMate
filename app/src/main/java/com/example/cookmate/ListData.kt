import java.io.Serializable

class ListData(
    var name: String,
    var time: String,
    var ingredients: Int,
    var desc: Int,
    var image: Int,
    var isFavorite: Boolean = false
) : Serializable