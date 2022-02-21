package com.example.do_an_android.Activity

import android.view.View
import android.widget.*
import com.android.volley.Response
import com.example.do_an_android.Util.*
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.HashMap

import com.example.do_an_android.Model.LoaiSanPham
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.do_an_android.R
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.android.volley.RequestQueue
import kotlin.jvm.Synchronized
import com.android.volley.toolbox.Volley
import com.example.do_an_android.Model.DienThoai
import com.bumptech.glide.Glide
import android.os.AsyncTask
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.annotation.SuppressLint
import com.example.do_an_android.Model.cart
import android.app.Activity
import android.content.Intent
import com.example.do_an_android.Activity.GioHang
import com.android.volley.toolbox.StringRequest
import com.android.volley.VolleyError
import kotlin.Throws
import com.android.volley.AuthFailureError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.do_an_android.Admin.DanhSachSP_Admin
import com.example.do_an_android.Admin.DanhSachUser_Admin
import com.example.do_an_android.Admin.DanhSachLoaiSP_Admin
import com.example.do_an_android.Activity.ThongTinCaNhan
import com.example.do_an_android.Activity.TrangChu
import com.example.do_an_android.Activity.ThemSP
import android.widget.AdapterView.OnItemClickListener
import com.example.do_an_android.Activity.ChiTietSanPham
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.example.do_an_android.Admin.Admin
import com.example.do_an_android.Activity.ThemLoaiSP
import com.example.do_an_android.Activity.DangNhap
import android.view.WindowManager
import android.content.SharedPreferences
import com.example.do_an_android.Activity.DatHang
import com.example.do_an_android.Activity.DanhSachSP
import com.example.do_an_android.Activity.DangKy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_android.Activity.TimKiemSP
import androidx.viewpager.widget.ViewPager
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import kotlin.experimental.and
import kotlin.experimental.or

class DangNhap : AppCompatActivity() {
    var email: EditText? = null
    var password: EditText? = null
    var chuyentrangdangki1: ImageView? = null
    var txtdangki: TextView? = null
    var bnt_dangnhap: TextView? = null
    var remember_me: CheckBox? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        anhxa()
        bnt_dangnhap!!.setOnClickListener(View.OnClickListener {
            if (CheckConnection.haveNetworkConnection(applicationContext)) {
                event()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Vui lòng kiểm tra kết nối Internet !",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
        })
        txtdangki!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, DangKy::class.java))
        }
        chuyentrangdangki1!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, DangKy::class.java))
        }
    }

    fun anhxa() {
        chuyentrangdangki1 = findViewById(R.id.chuyentrangdangki1)
        txtdangki = findViewById(R.id.txt_dangkidn)
        bnt_dangnhap = findViewById(R.id.btndangnhap)
        email = findViewById(R.id.edtEmail_Dangnhap)
        password = findViewById(R.id.edtPassword_Dangnhap)
        remember_me = findViewById(R.id.remember_me)
        remember_me?.setChecked(false)
        //Auto điền pw email sau khi logout
        val sharedPreferences = getSharedPreferences(DangNhap.Companion.FILE_SAVE, MODE_PRIVATE)
        val edittext_phone = sharedPreferences.getString("edittext_phone", "")
        val edittext_password = sharedPreferences.getString("edittext_password", "")
        email?.setText(edittext_phone)
        password?.setText(edittext_password)
    }

    private fun event() {
        txtdangki!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, DangKy::class.java))
        }
        chuyentrangdangki1!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, DangKy::class.java))
        }
        bnt_dangnhap!!.setOnClickListener { login() }
    }

    fun login() {
        val email = email!!.text.toString().trim { it <= ' ' }
        val password = password!!.text.toString().trim { it <= ' ' }
        if (email == "" || password == "") {
            Toast.makeText(applicationContext, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            val requestQueue = Volley.newRequestQueue(applicationContext)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                Server.login,
                Response.Listener { response ->
                    if (response == "1") {
                        finish()
                        //                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        val intent = Intent(applicationContext, TrangChu::class.java)
                        Remember(email, password)
                        val editor = getSharedPreferences(
                            DangNhap.Companion.FILE_NAME,
                            MODE_PRIVATE
                        ).edit()
                        editor.putString("email", email)
                        editor.apply()
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Tài khoản hoặc mật khẩu không đúng",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        applicationContext,
                        "Lỗi $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val param = HashMap<String, String>()
                    param["email"] = email
                    param["password"] = MD5(password)!!
                    return param
                }
            }
            requestQueue.add(stringRequest)
        }
    }

    private fun Remember(edittext_phone: String, edittext_password: String) {
        if (remember_me!!.isChecked) {
            val editor = getSharedPreferences(DangNhap.Companion.FILE_SAVE, MODE_PRIVATE).edit()
            editor.putString("edittext_phone", edittext_phone)
            editor.putString("edittext_password", edittext_password)
            editor.apply()
        } else {
            val editor = getSharedPreferences(DangNhap.Companion.FILE_SAVE, MODE_PRIVATE).edit()
            editor.putString("edittext_phone", "")
            editor.putString("edittext_password", "")
            editor.apply()
        }
    }

    fun MD5(md5: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val array = md.digest(md5.toByteArray(charset("UTF-8")))
            val sb = StringBuffer()
            for (i in array.indices) {
                sb.append(Integer.toHexString((array[i] and 0xFF.toByte()).toInt()).substring(1, 3))
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
        } catch (ex: UnsupportedEncodingException) {
        }
        return null
    }

    companion object {
        private const val FILE_NAME = "myFile"
        private const val FILE_SAVE = "savePass"
    }
}