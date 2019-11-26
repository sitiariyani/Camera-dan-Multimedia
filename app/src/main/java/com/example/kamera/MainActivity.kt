package com.example.kamera

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.PersistableBundle
import android.widget.Toast
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_camera.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(i,123)
        }
        btn_galeri.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);

                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGalerry();
                }
            }
            else{
                pickImageFromGalerry();
                }
            }
        }
    private fun pickImageFromGalerry() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGalerry()
                }
                else {
                    Toast.makeText(this, "Permission denide", Toast.LENGTH_SHORT).show()
                }
            }
    }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123)
        {

            var baseMenuPresenter = data?.extras?.get("data") as Bitmap
            btn_image.setImageBitmap(baseMenuPresenter)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            btn_image.setImageURI(data?.data)
        }
    }


}
