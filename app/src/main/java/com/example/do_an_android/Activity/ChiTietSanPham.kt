package com.example.do_an_android.Activity

import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.example.do_an_android.Util.*
import java.text.DecimalFormat
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

class ChiTietSanPham : AppCompatActivity() {
    var id = 0
    var hinh: ImageView? = null
    var toolbar_back_chitiet: Toolbar? = null
    var tenSP: TextView? = null
    var giaSP: TextView? = null
    var manhinhSP: TextView? = null
    var hdhSP: TextView? = null
    var camerasauSP: TextView? = null
    var cameratruocSP: TextView? = null
    var chipSP: TextView? = null
    var ramSP: TextView? = null
    var bonhotrongSP: TextView? = null
    var simSP: TextView? = null
    var pinsacSP: TextView? = null
    var txtdatmua: TextView? = null
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_san_pham)
        anhxa()
        sharedPreferences = getSharedPreferences(ChiTietSanPham.Companion.FILE_NAME, MODE_PRIVATE)
        ChiTietSanPham.Companion.iduser_share = sharedPreferences.getInt("iduser", 0)
        txtdatmua!!.setOnClickListener {
            finish()
            val it = Intent(this@ChiTietSanPham, TrangChu::class.java)
            startActivity(it)
        }
        toolbar_back_chitiet!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, DanhSachSP::class.java))
        }
        val intent = intent
        id = intent.getIntExtra("idproduct", -1)
        data
        getIdOrder(ChiTietSanPham.Companion.iduser_share)
        ChiTietSanPham.Companion.idorder = sharedPreferences.getInt("idorder", 0)
        txtdatmua!!.setOnClickListener { insertProduct() }
    }

    private fun anhxa() {
        toolbar_back_chitiet = findViewById(R.id.toolbar_back_chitiet)
        tenSP = findViewById(R.id.ten_san_pham_chitiet)
        giaSP = findViewById(R.id.gia_SP_chitiet)
        manhinhSP = findViewById(R.id.txt_manhinh_chitiet)
        hdhSP = findViewById(R.id.txt_hdh_chitiet)
        camerasauSP = findViewById(R.id.txt_camerasau_chitiet)
        cameratruocSP = findViewById(R.id.txt_cameratruoc_chitiet)
        chipSP = findViewById(R.id.txt_chip_chitiet)
        ramSP = findViewById(R.id.txt_ram_chitiet)
        bonhotrongSP = findViewById(R.id.txt_bonhotrong_chitiet)
        simSP = findViewById(R.id.txt_sim_chitiet)
        pinsacSP = findViewById(R.id.txt_pinsac_chitiet)
        txtdatmua = findViewById(R.id.txt_datmua)
        hinh = findViewById(R.id.img_sanpham)
    } //// Uri imgUri=Uri.parse(hinhanh);

    //
    //        requestQueue.add(stringRequest);
    //        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    private val data: Unit
        private get() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                Server.getproductbyid,
                Response.Listener { response ->
                    try {
                        val jsonArray = JSONArray(response)
                        val jsonObject = jsonArray.getJSONObject(0)
                        val idproduct = jsonObject.getInt("idproduct")
                        val nameproduct = jsonObject.getString("nameproduct")
                        val price = jsonObject.getInt("price")
                        ChiTietSanPham.Companion.giasp = price
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
                        val hinhanh = jsonObject.getString("hinh")
                        // Uri imgUri=Uri.parse(hinhanh);
                        Glide.with(applicationContext).load(hinhanh).into(hinh!!)
                        tenSP!!.text = nameproduct
                        val formatter = DecimalFormat("###,###,###")
                        giaSP!!.text = "Giá: " + formatter.format(price.toLong()) + "VNĐ"
                        manhinhSP!!.text = manhinh
                        hdhSP!!.text = hdh
                        camerasauSP!!.text = camerasau
                        cameratruocSP!!.text = cameratruoc
                        chipSP!!.text = chip
                        ramSP!!.text = ram
                        bonhotrongSP!!.text = bnt
                        simSP!!.text = sim
                        pinsacSP!!.text = pinsac

//
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
                    param["idproduct"] = id.toString()
                    return param
                }
            }
            //        requestQueue.add(stringRequest);
            VolleySingleton.Companion.getInstance(this).getRequestQueue().add<String>(stringRequest)
        }

    private fun getIdOrder(iduser: Int) {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.insert,
            Response.Listener { response ->
                try {
//                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                    val jsonArray = JSONArray(response)
                    val jsonObject = jsonArray.getJSONObject(0)
                    val _idorder = jsonObject.getInt("id")
                    //                    Toast.makeText(getApplicationContext(), "id:"+idorder, Toast.LENGTH_SHORT).show();
                    val editor = getSharedPreferences(
                        ChiTietSanPham.Companion.FILE_NAME,
                        MODE_PRIVATE
                    ).edit()
                    editor.putInt("idorder", _idorder)
                    editor.apply()
                    ChiTietSanPham.Companion.idget = _idorder.toString()
                    if (ChiTietSanPham.Companion.idorder != _idorder) {
                        finish()
                        startActivity(Intent(applicationContext, TrangChu::class.java))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    "Lỗi 1$error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val param = HashMap<String, String>()
                param["iduser"] = iduser.toString()
                return param
            }
        }
        requestQueue.add(stringRequest)
        //        VolleySingleton.getInstance(this).getRequestQueue().add(stringRequest);
//        return idget;
    }

    private fun insertProduct() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.insertItemToCart,
            Response.Listener { response ->
                if (response == "Done") {
                    Toast.makeText(applicationContext, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(Intent(applicationContext, GioHang::class.java))
                } else {
                    Toast.makeText(applicationContext, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    "Lỗi 2 $error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val param = HashMap<String, String>()
                param["idorder"] = ChiTietSanPham.Companion.idorder.toString()
                param["priceproduct"] = ChiTietSanPham.Companion.giasp.toString()
                param["idproduct"] = id.toString()
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, TrangChu::class.java))
    }

    fun trovetrangchu(view: View?) {
        startActivity(Intent(applicationContext, TrangChu::class.java))
    }

    companion object {
        private const val FILE_NAME = "myFile"
        private const val iduser_share = 0
        private const val idorder = 0
        private const val kiemtra = 0
        private const val giasp = 0
        private val idget: String? = null
    }
}