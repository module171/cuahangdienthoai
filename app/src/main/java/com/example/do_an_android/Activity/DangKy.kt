package com.example.do_an_android.Activity

import com.example.do_an_android.Model.LoaiSanPham
import android.widget.BaseAdapter
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.do_an_android.R
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
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
import android.widget.AdapterView
import com.example.do_an_android.Activity.ChiTietSanPham
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.example.do_an_android.Admin.Admin
import com.example.do_an_android.Activity.ThemLoaiSP
import android.widget.EditText
import com.example.do_an_android.Activity.DangNhap
import android.view.WindowManager
import android.content.SharedPreferences
import com.example.do_an_android.Activity.DatHang
import com.example.do_an_android.Activity.DanhSachSP
import android.widget.CheckBox
import com.example.do_an_android.Activity.DangKy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_android.Activity.TimKiemSP
import androidx.viewpager.widget.ViewPager
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.LinearLayout
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.android.volley.Response
import com.example.do_an_android.Util.*
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.HashMap

class DangKy : AppCompatActivity() {
    private var nameuser: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var Repassword: EditText? = null
    private var btnDK: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dangki)
        nameuser = findViewById<View>(R.id.txt_hotendangki) as EditText
        email = findViewById<View>(R.id.txt_emaildangki) as EditText
        password = findViewById<View>(R.id.txt_passworddangki) as EditText
        Repassword = findViewById<View>(R.id.txt_Repassworddangki) as EditText
        btnDK = findViewById<View>(R.id.btntrangdangki) as TextView
        btnDK!!.setOnClickListener(View.OnClickListener {
            if (CheckConnection.haveNetworkConnection(applicationContext)) {
                register()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Vui lòng kiểm tra kết nối Internet !",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
        })
    }

    fun onLoginClick(view: View?) {
        finish()
        startActivity(Intent(this, DangNhap::class.java))
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    fun register() {
        val nameuser = nameuser!!.text.toString()
        val email = email!!.text.toString()
        val password = password!!.text.toString()
        val repass = Repassword!!.text.toString()
        if (nameuser == "" || email == "" || password == "" || repass == "") {
            Toast.makeText(
                applicationContext,
                "Vui lòng nhập đầy đủ thông tin",
                Toast.LENGTH_SHORT
            ).show()
            return
        } else if (email.indexOf("@") < 0) {
            Toast.makeText(applicationContext, "Email không đúng định dạng!", Toast.LENGTH_SHORT)
                .show()
        } else if (password == repass != true) {
            Toast.makeText(applicationContext, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            val requestQueue = Volley.newRequestQueue(applicationContext)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                Server.registeruser,
                Response.Listener { response ->
                    if (response == "Done") {
                        finish()
                        Toast.makeText(
                            applicationContext,
                            "Đăng ký thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(applicationContext, DangNhap::class.java))
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Đăng ký không thành công",
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
                    param["nameuser"] = nameuser
                    param["email"] = email
                    param["password"] = MD5(password)!!
                    return param
                }
            }
            requestQueue.add(stringRequest)
        }
    }

    fun MD5(md5: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val array = md.digest(md5.toByteArray(charset("UTF-8")))
            val sb = StringBuffer()
            for (i in array.indices) {
                sb.append(Integer.toHexString(array[i] and 0xFF or 0x100).substring(1, 3))
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
        } catch (ex: UnsupportedEncodingException) {
        }
        return null
    }
}