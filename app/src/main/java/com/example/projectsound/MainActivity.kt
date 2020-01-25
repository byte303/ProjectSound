package com.example.projectsound

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_sound_info.*


class MainActivity : AppCompatActivity(),AdapterSound.InterfaceSound {

    private var checkPlay = false
    private var mediaPlayer: MediaPlayer? = null
    private var numberTrack: Int = 0

    private lateinit var sheetBehaviorSound: BottomSheetBehavior<RelativeLayout>
    private val arraySound = arrayOf(
        R.raw.sound_uchat_v_shkole,
        R.raw.sound_top_top_topaet_malysh,
        R.raw.sound_tanec_malenkih_utyat,
        R.raw.sound_rechka,
        R.raw.sound_pust_vsegda_budet_solnce,
        R.raw.sound_pesenka_veselogo_karandasha,
        R.raw.sound_oranzhevay_pesnya_irma_sohadze,
        R.raw.sound_mishka_kosolapyy,
        R.raw.sound_kto_pasetsa_na_lugu,
        R.raw.sound_kto_kogo_boitsya,
        R.raw.sound_karavay,
        R.raw.sound_zhil_byl_u_babushki_serenkiy_kozlik,
        R.raw.sound_do_re_mi_fa_sol,
        R.raw.sound_dvazhdy_dva_chetyre,
        R.raw.sound_dva_veselyh_gusya_1,
        R.raw.sound_vospi_pi_pi_pitanie,
        R.raw.sound_v_trave_sidel_kuznechik,
        R.raw.sound_belye_korabliki,
        R.raw.sound_antoshka,
        R.raw.sound_a_ty_menya_lyubish_aga,
        R.raw.sound_happy_birthday_to_you_detskiy_hor
    )
    private val arrayImage = arrayOf(
        R.drawable.image_uchat_v_shkole,
        R.drawable.image_top_top_topaet_malysh,
        R.drawable.image_tanec_malenkih_utyat,
        R.drawable.image_rechka,
        R.drawable.image_pust_vsegda_budet_solnce,
        R.drawable.image_pesenka_veselogo_karandasha,
        R.drawable.image_oranzhevaya_pesnya,
        R.drawable.image_mishka_kosolapyy,
        R.drawable.image_kto_pasetsya_na_lugu,
        R.drawable.image_kto_kogo_boitsya,
        R.drawable.image_karavay_karavay,
        R.drawable.image_zhil_byl_u_babushki_serenkiy_kozlik,
        R.drawable.image_do_re_mi_fa_sol,
        R.drawable.image_dvazhdy_dva_chetyre,
        R.drawable.image_dva_veselyh_gusya,
        R.drawable.image_vospi_pi_pi_pitanie,
        R.drawable.image_v_trave_sidel_kuznechik,
        R.drawable.image_belye_korabliki,
        R.drawable.image_antoshka,
        R.drawable.image_a_ty_menya_lyubish_aga,
        R.drawable.image_happy_birthday_to_you
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayName = resources.getStringArray(R.array.array_name_sound)

        setSupportActionBar(main_toolbar)

        list_sound.layoutManager = LinearLayoutManager(this)
        list_sound.adapter = AdapterSound(this, arrayName, arrayImage)

        sheetBehaviorSound = BottomSheetBehavior.from(bottom_sound)

        // Заставляем наш текст двигаться
        txtNameSound.ellipsize = TextUtils.TruncateAt.MARQUEE
        txtNameSound.isSingleLine = true
        txtNameSound.marqueeRepeatLimit = -1
        txtNameSound.isSelected = true

        mediaPlayer = MediaPlayer.create(this, arraySound[0])
        mediaPlayer?.setOnPreparedListener {

        }
        onSelect(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.action_info) {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun onPlaySound(num: Int, play: Boolean = false) {

        if (checkPlay) onStopSound()

        if (!play) mediaPlayer = MediaPlayer.create(this, arraySound[num])

        mediaPlayer?.start()

        imgPlay.setImageResource(R.drawable.ic_pause)
        checkPlay = true
        numberTrack = num
        onSelect(num)

        mediaPlayer?.setOnCompletionListener {
            if (numberTrack == arraySound.size - 1)
                onPlaySound(0)
            else {
                numberTrack += 1
                onPlaySound(numberTrack)
            }
        }
    }

    private fun onStopSound() {
        mediaPlayer?.pause()
        imgPlay.setImageResource(R.drawable.ic_play)
        checkPlay = false
    }

    private fun onSelect(num: Int) {
        val arrayName = resources.getStringArray(R.array.array_name_sound)
        val arrayText = resources.getStringArray(R.array.array_text_sound)

        txtNameSound.text = arrayName[num]
        txtTextSound.text = arrayText[num]
        imgPhoto.setImageResource(arrayImage[num])

        imgPlay.setOnClickListener {
            if (!checkPlay) onPlaySound(num, true)
            else onStopSound()
        }
        imgBack.setOnClickListener {
            if (numberTrack == 0)
                onPlaySound(arrayName.size - 1)
            else {
                numberTrack -= 1
                onPlaySound(numberTrack)
            }
        }
        imgNext.setOnClickListener {
            if (numberTrack == arrayName.size - 1)
                onPlaySound(0)
            else {
                numberTrack += 1
                onPlaySound(numberTrack)
            }
        }
    }

    override fun onClickItem(num: Int) {
        onPlaySound(num)
    }
/*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("checkPlay", checkPlay)
        outState.putInt("numberTrack", numberTrack)
        outState.putInt("currentPosition", mediaPlayer?.currentPosition as Int)

        onStopSound()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        checkPlay = savedInstanceState.getBoolean("checkPlay")
        numberTrack = savedInstanceState.getInt("numberTrack")
        mediaPlayer?.seekTo(savedInstanceState.getInt("currentPosition"))

        if(checkPlay) onPlaySound(numberTrack,true)
        else onStopSound()
    }*/
}
