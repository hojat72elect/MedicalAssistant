package ca.on.sudbury.hojat.medicalassistant

data class TreatmentRecord(
    var date: String,
    var doctor: String,
    var ward: String,
    var room: String
)