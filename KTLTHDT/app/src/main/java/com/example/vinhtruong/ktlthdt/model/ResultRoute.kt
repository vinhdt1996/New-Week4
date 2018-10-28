package com.example.vinhtruong.ktlthdt.model

import android.os.Parcel
import com.example.vinhtruong.ktlthdt.utils.KParcelable
import com.example.vinhtruong.ktlthdt.utils.parcelableCreator
import com.google.gson.annotations.SerializedName


data class ResultRoute(
        @SerializedName("geocoded_waypoints") val geocodedWaypoints: List<GeocodedWaypoint>?,
        @SerializedName("routes") val routes: List<Route>?,
        @SerializedName("status") val status: String?) : KParcelable {
    constructor(source: Parcel) : this(
            source.createTypedArrayList(GeocodedWaypoint.CREATOR),
            ArrayList<Route>().apply { source.readList(this, Route::class.java.classLoader) },
            source.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(geocodedWaypoints)
        writeList(routes)
        writeString(status)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::ResultRoute)
    }

}

data class GeocodedWaypoint(
        @SerializedName("geocoder_status") val geocoderStatus: String?,
        @SerializedName("place_id") val placeId: String?,
        @SerializedName("types") val types: List<String>?,
        @SerializedName("partial_match") val partialMatch: Boolean?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(geocoderStatus)
        writeString(placeId)
        writeStringList(types)
        writeValue(partialMatch)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::GeocodedWaypoint)
    }
}

data class Route(
        @SerializedName("bounds") val bounds: Bounds?,
        @SerializedName("copyrights") val copyrights: String?,
        @SerializedName("legs") val legs: List<Leg>?,
        @SerializedName("overview_polyline") val overviewPolyline: OverviewPolyline?,
        @SerializedName("summary") val summary: String?,
        @SerializedName("warnings") val warnings: List<Any>?,
        @SerializedName("waypoint_order") val waypointOrder: List<Any>?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<Bounds>(Bounds::class.java.classLoader),
            source.readString(),
            source.createTypedArrayList(Leg.CREATOR),
            source.readParcelable<OverviewPolyline>(OverviewPolyline::class.java.classLoader),
            source.readString(),
            ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) },
            ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(bounds, 0)
        writeString(copyrights)
        writeTypedList(legs)
        writeParcelable(overviewPolyline, 0)
        writeString(summary)
        writeList(warnings)
        writeList(waypointOrder)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Route)
    }
}

data class Bounds(
        @SerializedName("northeast") val northeast: Northeast?,
        @SerializedName("southwest") val southwest: Southwest?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<Northeast>(Northeast::class.java.classLoader),
            source.readParcelable<Southwest>(Southwest::class.java.classLoader)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(northeast, 0)
        writeParcelable(southwest, 0)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Bounds)
    }
}

data class Leg(
        @SerializedName("distance") val distance: Distance?,
        @SerializedName("duration") val duration: Duration?,
        @SerializedName("end_address") val endAddress: String?,
        @SerializedName("end_location") val endLocation: EndLocation?,
        @SerializedName("start_address") val startAddress: String?,
        @SerializedName("start_location") val startLocation: StartLocation?,
        @SerializedName("steps") val steps: List<Step>?,
        @SerializedName("traffic_speed_entry") val trafficSpeedEntry: List<Any>?,
        @SerializedName("via_waypoint") val viaWaypoint: List<Any>?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<Distance>(Distance::class.java.classLoader),
            source.readParcelable<Duration>(Duration::class.java.classLoader),
            source.readString(),
            source.readParcelable<EndLocation>(EndLocation::class.java.classLoader),
            source.readString(),
            source.readParcelable<StartLocation>(StartLocation::class.java.classLoader),
            source.createTypedArrayList(Step.CREATOR),
            ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) },
            ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(distance, 0)
        writeParcelable(duration, 0)
        writeString(endAddress)
        writeParcelable(endLocation, 0)
        writeString(startAddress)
        writeParcelable(startLocation, 0)
        writeTypedList(steps)
        writeList(trafficSpeedEntry)
        writeList(viaWaypoint)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Leg)
    }
}

data class OverviewPolyline(@SerializedName("points") val points: String?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(points)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::OverviewPolyline)
    }
}

data class Northeast(
        @SerializedName("lat") val lat: Double?,
        @SerializedName("lng") val lng: Double?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(lat)
        writeValue(lng)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Northeast)
    }
}

data class Southwest(
        @SerializedName("lat") val lat: Double?,
        @SerializedName("lng") val lng: Double?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(lat)
        writeValue(lng)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Southwest)
    }
}

data class Distance(
        @SerializedName("text") val text: String?,
        @SerializedName("value") val value: Int?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeValue(value)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Distance)
    }
}

data class Duration(
        @SerializedName("text") val text: String?,
        @SerializedName("value") val value: Int?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeValue(value)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Duration)
    }
}

data class EndLocation(
        @SerializedName("lat") val lat: Double?,
        @SerializedName("lng") val lng: Double?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(lat)
        writeValue(lng)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::EndLocation)
    }
}

data class StartLocation(
        @SerializedName("lat") val lat: Double?,
        @SerializedName("lng") val lng: Double?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(lat)
        writeValue(lng)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::StartLocation)
    }
}

data class Step(
        @SerializedName("distance") val distance: Distance?,
        @SerializedName("duration") val duration: Duration?,
        @SerializedName("end_location") val endLocation: EndLocation?,
        @SerializedName("html_instructions") val htmlInstructions: String?,
        @SerializedName("polyline") val polyline: Polyline?,
        @SerializedName("start_location") val startLocation: StartLocation?,
        @SerializedName("travel_mode") val travelMode: String?,
        @SerializedName("maneuver") val maneuver: String?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<Distance>(Distance::class.java.classLoader),
            source.readParcelable<Duration>(Duration::class.java.classLoader),
            source.readParcelable<EndLocation>(EndLocation::class.java.classLoader),
            source.readString(),
            source.readParcelable<Polyline>(Polyline::class.java.classLoader),
            source.readParcelable<StartLocation>(StartLocation::class.java.classLoader),
            source.readString(),
            source.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(distance, 0)
        writeParcelable(duration, 0)
        writeParcelable(endLocation, 0)
        writeString(htmlInstructions)
        writeParcelable(polyline, 0)
        writeParcelable(startLocation, 0)
        writeString(travelMode)
        writeString(maneuver)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Step)
    }
}

data class Polyline(@SerializedName("points") val points: String?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(points)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Polyline)
    }
}
