package com.buffetapp.pro.Login

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import com.buffetapp.pro.R
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class SplashScreenActivity : AppCompatActivity() {
    //Variables Privadas de Update
    private var appUpdate : AppUpdateManager? = null

    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)

        //Logica para la Animación
        val backgroundImg : ImageView = findViewById(R.id.iv_logo)
        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        backgroundImg.startAnimation(sideAnimation)

        Handler().postDelayed({
            startActivity(Intent(this, Welcome::class.java))
            finish()
        },1750)

        appUpdate = AppUpdateManagerFactory.create(this)
        checkUpdate()

        title = " "

        darkOff()
    }
    //Logica de Update
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

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }

    private fun darkOff(){        ///////////OF MODE DARK/////////
        val nightModeFlags: Int =
            getResources().getConfiguration().uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                /* si esta activo el modo oscuro lo desactiva */AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

            }
        }
    }


}