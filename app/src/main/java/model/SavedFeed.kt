package model

import java.io.Serializable

class SavedFeed : Serializable {
    var title: String? = null
    var dateOfPublication: String? = null

    //Getters and Setters
    var description: String? = null
    var link: String? = null
    var imageUrl: String? = null
    var source: String? = null
    var timeSaved: String? = null
    var id = 0

    constructor() {}

    //setting constructor
    constructor(title: String?, dateofpublication: String?, link: String?, imageUrl: String?, source: String?, description: String?) {
        this.title = title
        this.dateOfPublication = dateofpublication
        this.link = link
        this.imageUrl = imageUrl
        this.source = source
        this.description = description
    }

    //getting constructor
    constructor(title: String?, dateofpublication: String?, link: String?, imageUrl: String?, source: String?, timeSaved: String?, id: Int, description: String?) {
        this.title = title
        this.dateOfPublication = dateofpublication
        this.link = link
        this.imageUrl = imageUrl
        this.source = source
        this.timeSaved = timeSaved
        this.id = id
        this.description = description
    }

    override fun toString(): String {
        return "SavedFeed{" +
                "title='" + title + '\'' +
                ", dateofpublication='" + dateOfPublication + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", source='" + source + '\'' +
                ", timeSaved='" + timeSaved + '\'' +
                ", id=" + id +
                '}'
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true //if both of them points the same address in memory
        if (obj !is SavedFeed) return false // if "obj" is not a SavedFeed or a childclass
        val thatPeople = obj // than we can cast it to People safely
        return link == thatPeople.link && title == thatPeople.title // if they have the same link and same title,
        // then the 2 objects are equal unless they're pointing to different memory adresses
    }
}