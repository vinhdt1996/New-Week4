package com.example.vinhtruong.ktlthdt.model

import com.google.gson.annotations.SerializedName

data class ResultAddress(
        @SerializedName("results") val results: List<Result>?,
        @SerializedName("status") val status: String?)

data class Result (
        @SerializedName("address_components") val addressComponents: List<AddressComponent>?,
        @SerializedName("formatted_address") val formattedAddress: String?,
        @SerializedName("geometry") val geometry: Geometry?,
        @SerializedName("place_id") val placeId: String?,
        @SerializedName("types") val types: List<String>? )

data class AddressComponent (
    @SerializedName("long_name") val longName: String?,
    @SerializedName("short_name") val shortName: String?,
    @SerializedName("types") val types: List<String>?)

data class Geometry (
        @SerializedName("location") val location: Location?,
        @SerializedName("location_type") val locationType: String?,
        @SerializedName("viewport") val viewport: Viewport?,
        @SerializedName("bounds") val bounds: Bounds?)

data class Location (
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?)

data class Viewport (
        @SerializedName("northeast") val northeast: Northeast?,
        @SerializedName("southwest") val southwest: Southwest?)

