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
import java.text.DecimalFormat
import java.util.HashMap

class DatHang : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    var tongtien: TextView? = null
    var dathang: TextView? = null
    var huybo: TextView? = null
    var ten: EditText? = null
    var diachi: EditText? = null
    var sdt: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dathang)
        anhxa()
        sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        idorder = sharedPreferences!!.getInt("idorder_gh", 0)
        tien = sharedPreferences!!.getInt("tongtien", 0)
        val formatter = DecimalFormat("###,###,###")
        tongtien!!.text = "Tổng tiền: " + formatter.format(tien.toLong()) + " VND"
        dathang!!.setOnClickListener(View.OnClickListener {
            if (ten!!.text.toString() == "" || diachi!!.text.toString() == "" || sdt!!.text.toString() == "") {
                Toast.makeText(
                    applicationContext,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            } else {
                insertDatHang()
                updateOrder()
            }
        })
        huybo!!.setOnClickListener {
            finish()
            startActivity(Intent(this@DatHang, GioHang::class.java))
        }
    }

    private fun anhxa() {
        dathang = findViewById<View>(R.id.dat_hang_dh) as TextView
        ten = findViewById<View>(R.id.hoten_dh) as EditText
        diachi = findViewById<View>(R.id.dia_chi_dh) as EditText
        sdt = findViewById<View>(R.id.so_dien_thoai_dh) as EditText
        tongtien = findViewById<View>(R.id.tong_tien_thanh_toan_dh) as TextView
        huybo = findViewById<View>(R.id.huy_dat_hang) as TextView
    }

    private fun insertDatHang() {
        val ten = ten!!.text.toString().trim { it <= ' ' }
        val sdt = sdt!!.text.toString().trim { it <= ' ' }
        val dc = diachi!!.text.toString().trim { it <= ' ' }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.insertdathang,
            Response.Listener { response ->
                if (response == "Done") {
                    Toast.makeText(applicationContext, "Thêm thành công", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    startActivity(Intent(applicationContext, TrangChu::class.java))
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Thêm không thành công",
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
                param["hoten"] = ten
                param["sdt"] = sdt
                param["diachi"] = dc
                param["tongtien"] = tien.toString()
                param["idorder"] = idorder.toString()
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun updateOrder() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.updateorder,
            Response.Listener { response ->
                if (response == "Done") {
                    Toast.makeText(applicationContext, "Thêm thành công", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Thêm không thành công",
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
                param["totalprice"] = tien.toString()
                param["idorder"] = idorder.toString()
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, GioHang::class.java))
    }

    companion object {
        private const val FILE_NAME = "myFile"
        private var idorder = 0
        private var tien = 0
    }
}