package ca.on.sudbury.hojat.medicalassistant

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_patient_info_edit.*

class PatientInfoEdit : AppCompatActivity() {

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_info_edit)

        try {
            val bundle: Bundle? = intent.extras
            id = bundle!!.getInt("ID", 0)
            if (id != 0) {
                et_hin.setText(bundle.getInt("HealthInsuranceNumber").toString())
                et_patient_name.setText(bundle.getString("PatientName"))
                et_patient_race.setText(bundle.getString("Race"))
                et_patient_age.setText(bundle.getInt("Age").toString())
                et_patient_gender.setText(bundle.getString("Gender"))
                et_patient_marital_status.setText(bundle.getString("MaritalStatus"))
                et_patient_drug.setText(bundle.getString("Drug"))
                et_patient_dose.setText(bundle.getInt("Dose").toString())
                et_patient_lab_test.setText(bundle.getString("LabTest"))
                et_ward.setText(bundle.getString("Ward"))
                et_patient_treatment_history.setText(bundle.getString("TreatmentHistory"))
                et_time_of_admittance.setText(bundle.getString("TimeOfAdmittance"))
                cb_patient_is_discharged.isChecked = bundle.getInt("IsDischarged") == 1
            }
        } catch (e: Exception) {
        }

        /**
         * this part of script is performed whenever we click on ok.
         */
        ok_button_id.setOnClickListener {
            val dbManager = DBManager(this)
            val values = ContentValues()

            values.put("HealthInsuranceNumber", et_hin.text.toString().toLong())
            values.put("PatientName", et_patient_name.text.toString())
            values.put("Race", et_patient_race.text.toString())
            values.put("Age", et_patient_age.text.toString().toInt())
            values.put("Gender", et_patient_gender.text.toString())
            values.put("MaritalStatus", et_patient_marital_status.text.toString())
            values.put("Drug", et_patient_drug.text.toString())
            values.put("Dose", et_patient_dose.text.toString().toInt())
            values.put("LabTest", et_patient_lab_test.text.toString())
            values.put("Ward", et_ward.text.toString())
            values.put(
                "TreatmentHistory",
                et_patient_treatment_history.text.toString() + "\ndrugs:\n${et_patient_drug.text.toString()} - ${
                    et_patient_dose.text.toString().toInt()
                }milliliters\nlab test:\n${et_patient_lab_test.text.toString()}"
            )
            values.put("TimeOfAdmittance", et_time_of_admittance.text.toString())
            values.put("IsDischarged", if (cb_patient_is_discharged.isChecked) 1 else 0)

            if (id == 0) {
                val id = dbManager.insert(values)
                if (id > 0) {
                    Toast.makeText(this, " patient is added", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, " cannot add patient ", Toast.LENGTH_LONG).show()
                }
            } else {
                val selectionArs = arrayOf(id.toString())
                val id = dbManager.update(values, "ID=?", selectionArs)
                if (id > 0) {
                    Toast.makeText(this, " patient is added", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, " cannot add patient ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}