package ca.on.sudbury.hojat.medicalassistant

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DBManager(context: Context) {

    val dbName = "MedicalData"
    var dbTable = "Patients"
    val dbVersion = 1
    //    val patientTable = "PatientDBTable"
    //    val MWTable = "MedicalWorkerDBTable"

    //columns in patient table:
    private val colID = "ID"
    private val patientColHIN = "HealthInsuranceNumber"
    private val patientColName = "PatientName"
    private val patientColRace = "Race"
    private val patientColAge = "Age"
    private val patientColGender = "Gender"
    private val patientColMaritalStatus = "MaritalStatus"
    private val patientColDrug = "Drug"
    private val patientColDose = "Dose"
    private val patientColLabTest = "LabTest"
    private val patientColWard = "Ward"
    private val patientColTreatmentHistory = "TreatmentHistory"
    private val patientColTimeOfAdmittance = "TimeOfAdmittance"
    private val patientColIsDischarged = "IsDischarged"

    //columns in Medical Worker table:
//    private val MWColID = "MedicalWorkerID"
//    private val MWColName = "MedicalWorkerName"
//    private val MWColTitle = "MedicalWorkerTitle"
//    private val MWColYearsOfExperience = "MedicalWorkerYearsOfExperience"
//    private val MWColWard = "MedicalWorkerWard"

//    companion object {
//        var isPatient by Delegates.notNull<Boolean>()
//    }

    val sqlCreatePatientTable =
        "CREATE TABLE IF NOT EXISTS $dbTable " +
                "($colID INTEGER PRIMARY KEY," +
                "$patientColHIN INTEGER," +
                "$patientColName TEXT," +
                "$patientColRace TEXT," +
                "$patientColAge INTEGER," +
                "$patientColGender TEXT," +
                "$patientColMaritalStatus TEXT," +
                "$patientColDrug TEXT," +
                "$patientColDose INTEGER," +
                "$patientColLabTest TEXT," +
                "$patientColWard TEXT," +
                "$patientColTreatmentHistory TEXT," +
                "$patientColTimeOfAdmittance TEXT," +
                "$patientColIsDischarged INTEGER);"

//    val sqlCreateMWTable =
//        "CREATE TABLE IF NOT EXISTS $MWTable " +
//                "($MWColID INTEGER PRIMARY KEY, " +
//                "$MWColName TEXT, " +
//                "$MWColTitle TEXT, " +
//                "$MWColYearsOfExperience INTEGER, " +
//                "$MWColWard TEXT);"

    private var sqlDB: SQLiteDatabase? = null

    init {
        val db = DatabaseHelperPatients(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperPatients(context: Context) :
        SQLiteOpenHelper(context, dbName, null, dbVersion) {
        private var context: Context? = context

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreatePatientTable)//creating the patient table in our database.
            Toast.makeText(this.context, "database is created successfully!", Toast.LENGTH_LONG)
                .show()
//            p0.execSQL(sqlCreateMWTable)//creating the medical workers table in our database.
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
        }
    }

    fun insert(values: ContentValues): Long {
//        dbTable = if (isPatient) {
//            patientTable
//        } else {
//            MWTable
//        }
        return sqlDB!!.insert(dbTable, "", values)
    }

    fun query(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        sortOrder: String
    ): Cursor {
        val qb = SQLiteQueryBuilder()
//        qb.tables = "$patientTable, $MWTable"
        qb.tables = dbTable
        return qb.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
//        dbTable = if (isPatient) {
//            patientTable
//        } else {
//            MWTable
//        }
        return sqlDB!!.delete(dbTable, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
//        dbTable = if (isPatient) {
//            patientTable
//        } else {
//            MWTable
//        }
        return sqlDB!!.update(dbTable, values, selection, selectionArgs)
    }
}