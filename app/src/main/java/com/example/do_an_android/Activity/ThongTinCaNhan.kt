package com.example.do_an_android.Activity

import android.graphics.Color
import android.view.View
import com.android.volley.Response
import com.example.do_an_android.Util.*
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

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
import kotlin.experimental.and

class ThongTinCaNhan : AppCompatActivity() {
    var setListener: OnDateSetListener? = null
    var txtemail: TextView? = null
    var txtten: TextView? = null
    var txtsdt: TextView? = null
    var txtgender: TextView? = null
    var txtngaysinh: TextView? = null
    var txtpass: TextView? = null
    var admin_profile: TextView? = null
    var admin_logout: TextView? = null
    var thaydoithongtin: TextView? = null
    var luuthaydoi: TextView? = null
    var huythaydoi: TextView? = null
    var layout_admin_profile: LinearLayout? = null
    var type_user = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Anhxa()
        data
        Sukien()
        txtngaysinh!!.isClickable = false
    }

    private fun Sukien() {
        admin_logout!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, DangNhap::class.java))
        }
        admin_profile!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, Admin::class.java))
        }
    }

    // trung vs php
    private val data: Unit
        private get() {
            val requestQueue = Volley.newRequestQueue(applicationContext)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                Server.getuserbyemail,
                Response.Listener { response ->
                    try {
                        val jsonArray = JSONArray(response)
                        val jsonObject = jsonArray.getJSONObject(0)
                        val email_profile = jsonObject.getString("email")
                        val namuser_profile = jsonObject.getString("nameuser")
                        val birthday_profile = jsonObject.getString("birthday")
                        val phone_profile = jsonObject.getString("phone")
                        val gender_profile = jsonObject.getString("gender")
                        val pass_profile = jsonObject.getString("password")
                        val type = jsonObject.getInt("typeuser")
                        type_user = type
                        if (type_user == 1) {
                            layout_admin_profile!!.visibility = View.VISIBLE
                        }
                        txtemail!!.text = email_profile + ""
                        txtten!!.text = namuser_profile + ""
                        txtsdt!!.text = phone_profile + ""
                        txtgender!!.text = gender_profile + ""
                        txtpass!!.text = pass_profile + ""
                        txtngaysinh!!.text = birthday_profile + ""
                    } catch (e: JSONException) {
                        e.printStackTrace()
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
                    param["email"] = TrangChu.Companion.email_user // trung vs php
                    return param
                }
            }
            requestQueue.add(stringRequest)
        }

    fun backToHome(view: View?) {
        finish()
        val intent = Intent(this, TrangChu::class.java)
        //       String email_user = txtemail.getText().toString().trim();
//       intent.putExtra("email_user", email_user);
        startActivity(intent)
    }

    fun Anhxa() {
        txtemail = findViewById(R.id.txt_emailprofile)
        txtten = findViewById(R.id.txt_tenprofile)
        txtsdt = findViewById(R.id.txt_sdtprofile)
        txtgender = findViewById(R.id.txt_genderprofile)
        txtngaysinh = findViewById(R.id.txt_ngaysinhprofile)
        txtpass = findViewById(R.id.txt_passprofile)
        admin_logout = findViewById(R.id.admin_logout)
        admin_profile = findViewById(R.id.admin_profile)
        layout_admin_profile = findViewById(R.id.layout_admin_profile)
        layout_admin_profile?.setVisibility(View.GONE)
        thaydoithongtin = findViewById(R.id.thaydoithongtin)
        luuthaydoi = findViewById(R.id.luuthaydoi)
        luuthaydoi?.setVisibility(View.GONE)
        huythaydoi = findViewById(R.id.huythaydoi)
        huythaydoi?.setVisibility(View.GONE)
        thaydoithongtin?.setOnClickListener(View.OnClickListener {
            txtten?.setEnabled(true)
            txtten?.setBackgroundResource(R.drawable.custom_edittext_noframe_no)
            txtsdt?.setBackgroundResource(R.drawable.custom_edittext_noframe_no)
            txtgender?.setBackgroundResource(R.drawable.custom_edittext_noframe_no)
            txtpass?.setBackgroundResource(R.drawable.custom_edittext_noframe_no)
            txtngaysinh?.setBackgroundResource(R.drawable.custom_edittext_noframe_no)
            txtsdt?.setEnabled(true)
            txtgender?.setEnabled(true)
            txtngaysinh?.setClickable(true)
            txtpass?.setEnabled(true)
            luuthaydoi?.setVisibility(View.VISIBLE)
            huythaydoi?.setVisibility(View.VISIBLE)
        })
        luuthaydoi?.setOnClickListener(View.OnClickListener {
            txtten?.setEnabled(false)
            txtsdt?.setEnabled(false)
            txtgender?.setEnabled(false)
            txtpass?.setEnabled(false)
            txtngaysinh?.setClickable(false)
            txtten?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtsdt?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtgender?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtpass?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtngaysinh?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            luuthaydoi?.setVisibility(View.GONE)
            huythaydoi?.setVisibility(View.GONE)
            updateInfor()
        })
        huythaydoi?.setOnClickListener(View.OnClickListener {
            data
            txtten?.setEnabled(false)
            txtsdt?.setEnabled(false)
            txtgender?.setEnabled(false)
            txtpass?.setEnabled(false)
            txtten?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtsdt?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtgender?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtpass?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            txtngaysinh?.setBackgroundResource(R.drawable.custom_edittext_noframe)
            luuthaydoi?.setVisibility(View.GONE)
            huythaydoi?.setVisibility(View.GONE)
            txtngaysinh?.setClickable(false)
        })
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        var month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        setListener = OnDateSetListener { view: DatePicker?, year1: Int, month1: Int, day1: Int ->
            month = month1 + 1
            val date = "$day1/$month1/$year1"
            txtngaysinh?.setText(date)
        }
        txtngaysinh?.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@ThongTinCaNhan, R.style.DatePickerTheme, setListener, year, month, day
            )
            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        })
    }

    private fun updateInfor() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.updateinfo,
            Response.Listener { response ->
                if (response == "Done") {
                    Toast.makeText(
                        applicationContext,
                        "Cập nhật thành công!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Cập nhật không thành công!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    "Lỗi: $error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val param = HashMap<String, String>()
                param["email"] = TrangChu.Companion.email_user.trim { it <= ' ' }
                param["nameuser"] = txtten!!.text.toString().trim { it <= ' ' }
                param["birthday"] = txtngaysinh!!.text.toString().trim { it <= ' ' }
                param["gender"] = txtgender!!.text.toString().trim { it <= ' ' }
                param["phone"] = txtsdt!!.text.toString().trim { it <= ' ' }
                param["password"] = MD5(txtpass!!.text.toString().trim { it <= ' ' })!!
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, TrangChu::class.java))
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
}