package com.buffetapp.pro

import android.content.Intent
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.buffetapp.pro.AdapterFragment.MyAdapterFragmentHome
import com.buffetapp.pro.Fragment.About
import com.buffetapp.pro.Fragment.Menus
import com.buffetapp.pro.Fragment.Snack
import com.buffetapp.pro.Login.MainActivity
import com.buffetapp.pro.Login.Welcome
import com.buffetapp.pro.Opciones.*
import com.buffetapp.pro.databinding.ActivityHomeBinding
import com.buffetapp.pro.databinding.ActivityHomeFragmentBinding
import com.buffetapp.pro.databinding.FragmentMenusBinding
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import androidx.constraintlayout.motion.widget.Animatable as Animatable1

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    //Variables Privadas de Update
    private var appUpdate : AppUpdateManager? = null

    private val REQUEST_CODE = 100

    private lateinit var binding: ActivityHomeBinding

    private lateinit var firebaseAuth: FirebaseAuth

    /////////////////SECCION 1 PARA NOMBRE EN BARRA/////////////////
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val response = IdpResponse.fromResultIntent(it.data)

        if (it.resultCode == RESULT_OK){
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
            }
        }else{
            if (response == null){
                Toast.makeText(this, "Hasta Pronto", Toast.LENGTH_LONG).show()
                finish()
            }else{
                response.error?.let {
                    if (it.errorCode == ErrorCodes.NO_NETWORK){
                        Toast.makeText(this, "Sin Red", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Codigo de Error: ${it.errorCode}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    /////////////////SECCION 1 PARA NOMBRE EN BARRA/////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        aboutUs() // Ir a Sobre Nosotros
        services() // Ir a Servicios
        configAuthName()// Para el Nombre en la barras
        link2()
        newMenu()
        newSnacks()

        conet()

        appUpdate = AppUpdateManagerFactory.create(this)
        checkUpdate()

        slider()

        //Logica para mostrar fragment
        /*
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabLayout.addTab(tabLayout.newTab().setText("Menus"))
        tabLayout.addTab(tabLayout.newTab().setText("Snack"))
        tabLayout.addTab(tabLayout.newTab().setText("About"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        
        val adapter = MyAdapterFragmentHome(this,supportFragmentManager,tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })*/

        binding.btnFragmentMenus.setOnClickListener { replaceFragment(Menus()) }

        binding.btnFragmentSnack.setOnClickListener { replaceFragment(Snack()) }

        binding.btnFragmentAbout.setOnClickListener { replaceFragment(About()) }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()

    }

    private fun slider() {
        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://buffetproevent.com/wp-content/uploads/2022/04/Somos.png",""))
        imageList.add(SlideModel("https://buffetproevent.com/wp-content/uploads/2022/04/GuadalupanoExpress.png",""))
        imageList.add(SlideModel("https://buffetproevent.com/wp-content/uploads/2022/04/slider_antojitos.png",""))
        imageList.add(SlideModel("https://buffetproevent.com/wp-content/uploads/2022/04/RecoleccionChinameca.png",""))
        imageList.add(SlideModel("https://buffetproevent.com/wp-content/uploads/2022/04/RecoleccionSanMiguel.png",""))

        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_loginOut ->{
                firebaseAuth.signOut()
                Toast.makeText(this, "Sesion Cerrada con Exito", Toast.LENGTH_LONG).show()
                checkUser()
                val intent = Intent(this, Welcome::class.java)
                startActivity(intent)
                finish()
                //Toast.makeText(this, "Cerrar Sesón", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun checkUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo->

            if (updateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
                && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                appUpdate?.startUpdateFlowForResult(updateInfo,
                    AppUpdateType.IMMEDIATE,this,REQUEST_CODE)

            }
        }
    }

    fun inProgressUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo->

            if (updateInfo.updateAvailability()== UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdate?.startUpdateFlowForResult(updateInfo,AppUpdateType.IMMEDIATE,this,REQUEST_CODE)

            }
        }
    }

    private fun configAuthName(){
        firebaseAuth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            supportActionBar?.title = auth.currentUser?.displayName
            if (auth.currentUser != null){
                supportActionBar?.title = auth.currentUser?.displayName
            }
        }
    }

    /////////////////SECCION 2 PARA NOMBRE EN BARRA/////////////////
    override fun onResume() {
        super.onResume()
        inProgressUpdate()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    /////////////////SECCION 2 PARA NOMBRE EN BARRA/////////////////

    private fun checkUser(){

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            //val phote = firebaseUser.photoUrl
            val email = firebaseUser.email
            val nameUser = firebaseUser.displayName
            binding.nameTv.text = nameUser
            binding.emailTv.text = email

        }

        val sudo  = "mrgomez@webforallsv.com"
        val btnservice : Button = findViewById(R.id.btnService)
        val btnnet : Button = findViewById(R.id.btnNet)
        val btnscrolling : Button = findViewById(R.id.btnScrolling)
        if(binding.emailTv.text == sudo){
            btnservice.visibility = View.VISIBLE
            btnnet.visibility = View.VISIBLE
            btnscrolling.visibility = View.VISIBLE
        }
    }

    private fun aboutUs(){
        val btnConocenos : Button = findViewById(R.id.btnConocenos)
        btnConocenos.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun services(){
        val btnservice : Button = findViewById(R.id.btnService)
        btnservice.setOnClickListener {
            val intent = Intent(this, OurServices::class.java)
            startActivity(intent)
        }
    }

    private fun link2() {// Yo
        val txtUrl2: TextView = findViewById(R.id.powerTxt2)
        txtUrl2.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.webforallsv.com/portfolio/buffet-proevent-app/")
            )
            startActivity(intent)
        }
    }

    private fun newMenu() {
        val btnNuevoMenu : Button = findViewById(R.id.btnNuevoMenu)
        btnNuevoMenu.setOnClickListener {
            val intent = Intent(this, MenuRecyclerView::class.java)
            startActivity(intent)
        }
    }

    private fun conet(){
        val btnNet : Button = findViewById(R.id.btnNet)
        btnNet.setOnClickListener {
            val intent = Intent(this, MainNetwork::class.java)
            startActivity(intent)
        }
    }

    private fun newSnacks(){
        val btnNewSnack : Button = findViewById(R.id.btnSnackNew)
        btnNewSnack.setOnClickListener {
            val intent = Intent(this, MenuRecyclerviewSnacks()::class.java)
            startActivity(intent)
        }
    }
}