package com.buffetapp.pro.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.buffetapp.pro.Opciones.OurServices
import com.buffetapp.pro.R
import com.buffetapp.pro.UpdateMenusActivity
import com.buffetapp.pro.WompiActivity
import com.buffetapp.pro.databinding.FragmentSettingsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateMenus()
        facebookFragment()
        webFragment()
        whatsappFragment()
        btnServicesFragment()
        wompiIntent()
        linkSettings()
        callFragment()


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    private fun updateMenus() {
        val btnUpdateMenus = view?.findViewById<Button>(R.id.btnUpdateMenus)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account?.email == "mrgomez84x@gmail.com") {
            btnUpdateMenus?.visibility = View.VISIBLE
            btnUpdateMenus?.setOnClickListener {
                showPinVerificationDialog()
                //val intent = Intent(getActivity(), UpdateMenusActivity::class.java)
                //startActivity(intent)
            }
        } else {
            btnUpdateMenus?.visibility = View.GONE
        }
    }

    private fun showPinVerificationDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_pin_verification, null)
        val pinEditText = dialogView.findViewById<EditText>(R.id.pinEditText)
        val positiveButton: Button

        dialogBuilder.setView(dialogView)
            .setTitle("Verificación de PIN")
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()

        positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.isEnabled = false

        pinEditText.addTextChangedListener(object : TextWatcher { //valida la longitud para mostrar o no el boton aceptar
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val enteredPin = s.toString()
                positiveButton.isEnabled = enteredPin.length == 4
            }
        })

        positiveButton.setOnClickListener {
            val enteredPin = pinEditText.text.toString()
            if (enteredPin == "2100") {
                performIntentAction()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "PIN incorrecto. Intente nuevamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performIntentAction() {
        val intent = Intent(requireContext(), UpdateMenusActivity::class.java)
        startActivity(intent)
    }

    private fun facebookFragment(){
        val btnFacebookFragment = view?.findViewById<Button>(R.id.btnFacebookFragment)
        btnFacebookFragment?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100009157009110"))
            startActivity(intent)
        }
    }

    private fun webFragment(){
        val btnUrlFragment = view?.findViewById<Button>(R.id.btnUrlFragment)
        btnUrlFragment?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://buffetproevent.com/"))
            startActivity(intent)
        }
    }

    private fun whatsappFragment(){
        val btnWhatsappFragment = view?.findViewById<Button>(R.id.btnWhastappFragment)
        btnWhatsappFragment?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20necesito%20informaci%C3%B3n%20de%20buffetproevent.com%20me%20comunico%20desde%20Buffet%20App"))
            startActivity(intent)
        }
    }

    private fun btnServicesFragment(){
        val btnServicesFragment = view?.findViewById<Button>(R.id.btnServicesFragment)
        btnServicesFragment?.setOnClickListener {
            val intent = Intent(getActivity(), OurServices::class.java)
            startActivity(intent)
        }
    }

    private fun wompiIntent(){
        val btnWompi = view?.findViewById<Button>(R.id.btnWompi)
        btnWompi?.setOnClickListener {
            val intent = Intent(getActivity(), WompiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun linkSettings(){
        val linkSettings = view?.findViewById<TextView>(R.id.powerTxtSettings)
        linkSettings?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mrgomez-dev-2650381a6/"))
            startActivity(intent)
        }
    }

    private fun callFragment(){
        val btnCallFragment = view?.findViewById<Button>(R.id.btnCallFragment)
        btnCallFragment?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:78736282")
            startActivity(intent)
        }
    }
}