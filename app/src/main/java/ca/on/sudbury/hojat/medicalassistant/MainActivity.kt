package ca.on.sudbury.hojat.medicalassistant

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*


/**
 * coded by Hojat Ghasemi.
 * first created on November 25th 2020.
 */
class MainActivity : AppCompatActivity() {

    private var listPatients = ArrayList<Patient>()
//    private var listMedicalWorkers = ArrayList<MedicalWorker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadQuery("%")//loading data from database
    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }

    private fun loadQuery(inputQuery: String) {
        //connecting to DB and loading the current entities.
        val dbManager = DBManager(this)
        val projections = arrayOf(
            "ID",
            "HealthInsuranceNumber",
            "PatientName",
            "Race",
            "Age",
            "Gender",
            "MaritalStatus",
            "Drug",
            "Dose",
            "LabTest",
            "Ward",
            "TreatmentHistory",
            "TimeOfAdmittance",
            "IsDischarged"
        )//all the stuff that we want to take out of the database and show in this list.
        val selectionArgs = arrayOf(inputQuery)
        val cursor =
            dbManager.query(projections, "PatientName like ?", selectionArgs, "PatientName")
        listPatients.clear()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val hin = cursor.getInt(cursor.getColumnIndex("HealthInsuranceNumber"))
                val name = cursor.getString(cursor.getColumnIndex("PatientName"))
                val race = cursor.getString(cursor.getColumnIndex("Race"))
                val age = cursor.getInt(cursor.getColumnIndex("Age"))
                val gender = cursor.getString(cursor.getColumnIndex("Gender"))
                val maritalStatus = cursor.getString(cursor.getColumnIndex("MaritalStatus"))
                val drug = cursor.getString(cursor.getColumnIndex("Drug"))
                val dose = cursor.getInt(cursor.getColumnIndex("Dose"))
                val labTest = cursor.getString(cursor.getColumnIndex("LabTest"))
                val ward = cursor.getString(cursor.getColumnIndex("Ward"))
                val treatmentHistory = cursor.getString(cursor.getColumnIndex("TreatmentHistory"))
                val timeOfAdmittance = cursor.getString(cursor.getColumnIndex("TimeOfAdmittance"))
                val isDischarged = cursor.getInt(cursor.getColumnIndex("IsDischarged"))

                listPatients.add(
                    Patient(
                        id,
                        hin,
                        name,
                        race,
                        age,
                        gender,
                        maritalStatus,
                        drug,
                        dose,
                        labTest,
                        ward,
                        treatmentHistory,
                        timeOfAdmittance,
                        isDischarged
                    )
                )

            } while (cursor.moveToNext())
        }


        val myPatientsAdapter = MyEntitiesAdapter(this, listPatients)
        list_view_entities.adapter = myPatientsAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        val sv: SearchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                loadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.addPatient -> {
                //Going to the page for adding "patient" to the database.
                val intent = Intent(this, PatientInfoEdit::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class MyEntitiesAdapter(
        context: Context,
        private var listPatientsAdapter: ArrayList<Patient>
    ) : BaseAdapter() {

        private var context: Context? = context


        @SuppressLint("ViewHolder", "InflateParams", "SetTextI18n")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val myView = layoutInflater.inflate(R.layout.ticket, null)
            val myPatient = listPatientsAdapter[p0]
            myView.tvTitle.text = myPatient.patientName
            myView.tvDes.text = "Health Insurance Number: ${myPatient.patientHIN}"
            myView.ivDelete.setOnClickListener {
                val dbManager = DBManager(this.context!!)
                val selectionArgs = arrayOf(myPatient.nodeID.toString())
                dbManager.delete("ID=?", selectionArgs)
                loadQuery("%")
            }
            myView.ivEdit.setOnClickListener {
                goToUpdate(myPatient)
            }
            return myView
        }

        override fun getItem(p0: Int): Any {
            return listPatientsAdapter[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listPatientsAdapter.size
        }


    }

    fun goToUpdate(patient: Patient) {
        val intent = Intent(this, PatientInfoEdit::class.java)
        intent.putExtra("ID", patient.nodeID)
        intent.putExtra("HealthInsuranceNumber", patient.patientHIN)
        intent.putExtra("PatientName", patient.patientName)
        intent.putExtra("Race", patient.patientRace)
        intent.putExtra("Age", patient.patientAge)
        intent.putExtra("Gender", patient.patientGender)
        intent.putExtra("MaritalStatus", patient.patientMaritalStatus)
        intent.putExtra("Drug", patient.patientDrug)
        intent.putExtra("Dose", patient.patientDose)
        intent.putExtra("LabTest", patient.patientLabTest)
        intent.putExtra("Ward", patient.ward)
        intent.putExtra("TreatmentHistory", patient.patientTreatmentHistory)
        intent.putExtra("TimeOfAdmittance", patient.patientTimeOfAdmittance)
        intent.putExtra("IsDischarged", patient.patientIsDischarged)
        startActivity(intent)
    }
}