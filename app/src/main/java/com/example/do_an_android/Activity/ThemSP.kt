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
import java.util.HashMap

class ThemSP : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var nameproduct: EditText? = null
    private var maloai: EditText? = null
    private var price: EditText? = null
    private var soluong: EditText? = null
    private var manhinh: EditText? = null
    private var hedieuhanh: EditText? = null
    private var cmrsau: EditText? = null
    private var cmrtruoc: EditText? = null
    private var chip: EditText? = null
    private var ram: EditText? = null
    private var bonhotrong: EditText? = null
    private var sim: EditText? = null
    private var pinsac: EditText? = null
    private var btnADD: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setContentView(R.layout.activity_themdienthoai)
        nameproduct = findViewById<View>(R.id.txtTenDT) as EditText
        price = findViewById<View>(R.id.txt_Giasp_themdt) as EditText
        soluong = findViewById<View>(R.id.txt_SoLuongsp_themdt) as EditText
        maloai = findViewById<View>(R.id.txt_MaLoai_themdt) as EditText
        manhinh = findViewById<View>(R.id.txt_ManHinh_themdt) as EditText
        hedieuhanh = findViewById<View>(R.id.txt_hdh_themdt) as EditText
        cmrsau = findViewById<View>(R.id.txt_CMRSau_themdt) as EditText
        cmrtruoc = findViewById<View>(R.id.txt_CMRTruoc_themdt) as EditText
        chip = findViewById<View>(R.id.txt_Chipsp_themdt) as EditText
        ram = findViewById<View>(R.id.txt_Ramsp_themdt) as EditText
        bonhotrong = findViewById<View>(R.id.txt_Bntsp_themdt) as EditText
        sim = findViewById<View>(R.id.txt_Simsp_themdt) as EditText
        pinsac = findViewById<View>(R.id.txt_PinSacsp_themdt) as EditText
        btnADD = findViewById<View>(R.id.btnThemDT) as TextView
        btnADD!!.setOnClickListener { ThemMoi() }
    }

    fun ThemMoi() {
        val nameproduct = nameproduct!!.text.toString().trim { it <= ' ' }
        val price = price!!.text.toString().trim { it <= ' ' }
        val soluong = soluong!!.text.toString().trim { it <= ' ' }
        val maloai = maloai!!.text.toString().trim { it <= ' ' }
        val manhinh = manhinh!!.text.toString().trim { it <= ' ' }
        val hedieuhanh = hedieuhanh!!.text.toString().trim { it <= ' ' }
        val cmrsau = cmrsau!!.text.toString().trim { it <= ' ' }
        val cmrtruoc = cmrtruoc!!.text.toString().trim { it <= ' ' }
        val chip = chip!!.text.toString().trim { it <= ' ' }
        val ram = ram!!.text.toString().trim { it <= ' ' }
        val bonhotrong = bonhotrong!!.text.toString().trim { it <= ' ' }
        val sim = sim!!.text.toString().trim { it <= ' ' }
        val pinsac = pinsac!!.text.toString().trim { it <= ' ' }
        if (nameproduct == "" || price == "" || maloai == "" || manhinh == "" || hedieuhanh == "" || cmrsau == "" || cmrtruoc == "" || chip == "" || ram == "" || bonhotrong == "" || sim == "" || pinsac == "") {
            Toast.makeText(applicationContext, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            val requestQueue = Volley.newRequestQueue(applicationContext)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                Server.addproduct,
                Response.Listener { response ->
                    if (response == "Done") {
                        Toast.makeText(
                            applicationContext,
                            "Thêm thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        startActivity(Intent(applicationContext, TrangChu::class.java))
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Thêm thất bại",
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
                    param["nameproduct"] = nameproduct
                    param["price"] = price
                    param["manhinh"] = manhinh
                    param["hdh"] = hedieuhanh
                    param["camerasau"] = cmrsau
                    param["cameratruoc"] = cmrtruoc
                    param["chip"] = chip
                    param["ram"] = ram
                    param["bnt"] = bonhotrong
                    param["sim"] = sim
                    param["pinsac"] = pinsac
                    param["sum"] = soluong
                    param["idtype"] = maloai
                    return param
                }
            }
            requestQueue.add(stringRequest)
        }
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {}
    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
}