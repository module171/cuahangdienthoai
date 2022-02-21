package com.example.do_an_android.Activity

import android.widget.*
import com.android.volley.Response
import com.example.do_an_android.Util.*
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

class TimKiemSP : AppCompatActivity() {
    var id: String? = null
    var listView1: ListView? = null
    var adapterSearch: DienthoaiAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val intent = intent
        id = intent.getStringExtra("search_product")
        adapterSearch = DienthoaiAdapter(this, TimKiemSP.Companion.dienthoaiArrayList1)
        listView1 = findViewById(R.id.mylistview1)
        listView1.setAdapter(adapterSearch)
        getdata()
        listView1.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            finish()
            val intent = Intent(applicationContext, ChiTietSanPham::class.java)
            val idpro: Int = TimKiemSP.Companion.dienthoaiArrayList1.get(i).getIdproduct()
            intent.putExtra(
                "idproduct",
                TimKiemSP.Companion.dienthoaiArrayList1.get(i).getIdproduct()
            )
            startActivity(intent)
        })
    }

    fun getdata() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.searchproductbyname,
            Response.Listener { response ->
                TimKiemSP.Companion.dienthoaiArrayList1.clear()
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val idproduct = jsonObject.getInt("idproduct")
                        val nameproduct = jsonObject.getString("nameproduct")
                        val price = jsonObject.getInt("price")
                        val manhinh = jsonObject.getString("manhinh")
                        val hdh = jsonObject.getString("hdh")
                        val camerasau = jsonObject.getString("camerasau")
                        val cameratruoc = jsonObject.getString("cameratruoc")
                        val chip = jsonObject.getString("chip")
                        val ram = jsonObject.getString("ram")
                        val bnt = jsonObject.getString("bnt")
                        val sim = jsonObject.getString("sim")
                        val pinsac = jsonObject.getString("pinsac")
                        val sum = jsonObject.getInt("sum")
                        val idtype = jsonObject.getInt("idtype")
                        val hinh =
                            jsonObject.getString("hinh") // hình nhưu k có cái link hình thì phả
                        val dt = DienThoai(
                            idproduct,
                            nameproduct,
                            price,
                            manhinh,
                            hdh,
                            camerasau,
                            cameratruoc,
                            chip,
                            ram,
                            bnt,
                            sim,
                            pinsac,
                            sum,
                            idtype,
                            hinh
                        )
                        TimKiemSP.Companion.dienthoaiArrayList1.add(dt)
                        adapterSearch!!.notifyDataSetChanged()
                    }
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
                val intent = intent
                id = intent.getStringExtra("search_product")
                param["nameproduct"] = id!!
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, TrangChu::class.java))
    }

    companion object {
        var dienthoaiArrayList1 = ArrayList<DienThoai>()
    }
}