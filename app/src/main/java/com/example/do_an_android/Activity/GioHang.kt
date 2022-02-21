package com.example.do_an_android.Activity

import android.view.View
import android.widget.*
import com.android.volley.Response
import com.example.do_an_android.Util.*
import java.text.DecimalFormat
import java.util.ArrayList
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

class GioHang : AppCompatActivity() {
    var backToHome: ImageView? = null
    var cancel: ImageView? = null
    var sharedPreferences: SharedPreferences? = null
    var tongSoLuong: TextView? = null
    var soLuong: TextView? = null
    var them: TextView? = null
    var giam: TextView? = null
    private var adapter: itemInCartAdapter? = null
    private var listView: ListView? = null
    var tongtiengh: TextView? = null
    var getidorder: TextView? = null
    var muaHang: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        sharedPreferences = getSharedPreferences(GioHang.Companion.FILE_NAME, MODE_PRIVATE)
        GioHang.Companion.iduser_share = sharedPreferences!!.getInt("iduser", 0)

        getidorder = findViewById<View>(R.id.txt_idorder) as TextView
        tongtiengh = findViewById<View>(R.id.tongTienGioHang) as TextView
        tongSoLuong = findViewById<View>(R.id.sumincart) as TextView
        backToHome = findViewById<View>(R.id.veTrangChu) as ImageView
        backToHome!!.setOnClickListener {
            finish()
            val i = Intent(this@GioHang, TrangChu::class.java)
            startActivity(i)
        }
        cancel = findViewById<View>(R.id.veDanhSach) as ImageView
        cancel!!.setOnClickListener {
            finish()
            val i = Intent(this@GioHang, DanhSachSP::class.java)
            startActivity(i)
        }
        listView = findViewById(R.id.mylistview_Danhsachsp)
        adapter = itemInCartAdapter(this, GioHang.Companion.giohangArrayList)
        listView?.setAdapter(adapter)
        getdata(GioHang.Companion.iduser_share)
        muaHang = findViewById(R.id.muaHang)
        muaHang?.setOnClickListener(View.OnClickListener { thanhtoan() })
    }

    fun getdata(id: Int) {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.getproductcart,
            Response.Listener { response ->
                GioHang.Companion.giohangArrayList.clear()
                var tong = 0
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val iddetail = jsonObject.getInt("iddetail")
                        val nameproduct = jsonObject.getString("nameproduct")
                        val price = jsonObject.getInt("priceproduct")
                        val sum = jsonObject.getInt("sum")
                        val totalprice = jsonObject.getInt("totalprice")
                        GioHang.Companion.idorder = jsonObject.getInt("idorder")
                        val hinh = jsonObject.getString("hinh")
                        tong += totalprice
                        val gh = cart(iddetail, nameproduct, price, sum, totalprice, hinh)
                        GioHang.Companion.giohangArrayList.add(gh)
                        adapter!!.notifyDataSetChanged()
                    }
                    getidorder?.setText(GioHang.Companion.idorder.toString())
                    val editor =
                        getSharedPreferences(GioHang.Companion.FILE_NAME, MODE_PRIVATE).edit()
                    editor.putInt("idorder_gh", GioHang.Companion.idorder)
                    editor.putInt("tongtien", tong)
                    editor.apply()
                    demSoluongSanPhamCoTrongGioHang(tong)
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
                val i = 1
                val param = HashMap<String, String>()
                param["iduser"] = id.toString()
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    fun demSoluongSanPhamCoTrongGioHang(tong: Int) {
        val dem: Int = GioHang.Companion.giohangArrayList.size
        tongSoLuong!!.text = "Giỏ hàng ($dem)"
        val formatter = DecimalFormat("###,###,###")
        tongtiengh!!.text = formatter.format(tong.toLong()) + "" + " VNĐ"
        adapter!!.notifyDataSetChanged()
        listView!!.adapter = adapter
    }

    fun thanhtoan() {
        finish()
        val intent = Intent(this@GioHang, DatHang::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, TrangChu::class.java))
    }

    companion object {
        private const val FILE_NAME = "myFile"
        var giohangArrayList = ArrayList<cart>()
        private var iduser_share = 0
        private var idorder = 0
    }
}