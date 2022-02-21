package com.example.do_an_android.Activity

import android.view.View
import com.android.volley.Response
import com.example.do_an_android.Util.*
import java.util.HashMap

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

class ThemHoaDon : AppCompatActivity() {
    private var txttrangthai: EditText? = null
    private var txtdate: EditText? = null
    private var txtdiachi: EditText? = null
    private var txtiduser: EditText? = null
    private var btnthemHD: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.themhoadon)
        txttrangthai = findViewById<View>(R.id.txt_trangthaihoadon) as EditText
        txtdate = findViewById<View>(R.id.txt_ngaylaphoadon) as EditText
        txtdiachi = findViewById<View>(R.id.txt_diachihoadon) as EditText
        txtiduser = findViewById<View>(R.id.txt_iduser) as EditText
        btnthemHD = findViewById<View>(R.id.btn_themhoadon) as TextView
        btnthemHD!!.setOnClickListener { ThemMoi() }
    }

    fun ThemMoi() {
        val status = txttrangthai!!.text.toString().trim { it <= ' ' }
        val date = txtdate!!.text.toString().trim { it <= ' ' }
        val address = txtdiachi!!.text.toString().trim { it <= ' ' }
        val iduser = txtiduser!!.text.toString().trim { it <= ' ' }

//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yyyy");
//        Format f = new SimpleDateFormat("dd/MM/yyyy");
//        String datecreate = f.format(new Date());
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.addHD,
            Response.Listener { response ->
                if (response == "Done") {
                    Toast.makeText(applicationContext, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, TrangChu::class.java))
                } else {
                    Toast.makeText(applicationContext, "Thêm thất bại", Toast.LENGTH_SHORT).show()
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
                param["stt"] = status
                param["dateorder"] = date
                param["address"] = address
                param["iduser"] = iduser
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this@ThemHoaDon, TrangChu::class.java))
    }
}