package ca.on.sudbury.hojat.medicalassistant

data class Patient(
    var nodeID: Int,
    var patientHIN: Int,
    var patientName: String,
    var patientRace: String,
    var patientAge: Int,
    var patientGender: String,
    var patientMaritalStatus: String,
//    var patientPrescription: Prescription,
    var patientDrug: String,
    var patientDose: Int,
    var patientLabTest: String,
    var ward: String,
    var patientTreatmentHistory: String,
    var patientTimeOfAdmittance: String,
    var patientIsDischarged: Int
)