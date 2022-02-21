package com.example.do_an_android.Activity

import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.android.volley.*
import com.example.do_an_android.Model.User
import com.example.do_an_android.Util.*
import java.lang.NullPointerException
import java.util.ArrayList
import java.util.HashMap

import com.example.do_an_android.Model.LoaiSanPham
import com.example.do_an_android.R
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import kotlin.Throws
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

class TrangChu : AppCompatActivity() {
    var editseacrchh: EditText? = null
    var xemtca: TextView? = null
    var listView: ListView? = null
    var search: ImageView? = null
    var adapter: DienthoaiAdapter? = null
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trangchu)
        AnhXa()
        val sharedPreferences = getSharedPreferences(TrangChu.Companion.FILE_NAME, MODE_PRIVATE)
        TrangChu.Companion.email_user = sharedPreferences.getString("email", "")!!
        adapter = DienthoaiAdapter(this, TrangChu.Companion.dienthoaiArrayList)
        getUserIdByEmail(TrangChu.Companion.email_user)
        search!!.setOnClickListener {
            finish()
            val intent6 = Intent(applicationContext, TimKiemSP::class.java)
            val search_product = editseacrchh!!.text.toString().trim { it <= ' ' }
            intent6.putExtra("search_product", search_product)
            startActivity(intent6)
        }
        listView!!.adapter = adapter
        getdata()
        bottomNavigationView = findViewById<View>(R.id.bottom_nav) as BottomNavigationView
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item: MenuItem ->
            finish()
            when (item.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, TrangChu::class.java)
                    startActivity(intent)
                }
                R.id.Click_to_cart -> {
                    val intent1 = Intent(this, GioHang::class.java)
                    startActivity(intent1)
                }
                R.id.page_3 -> {
                    val intent2 = Intent(this, DanhSachSP::class.java)
                    startActivity(intent2)
                }
                R.id.page_4 -> {
                    val intent6 = Intent(this, TrangChu::class.java)
                    Toast.makeText(applicationContext, "Sắp có", Toast.LENGTH_SHORT).show()
                    startActivity(intent6)
                }
                R.id.page_5 -> {
                    val intent5 = Intent(this, ThongTinCaNhan::class.java)
                    startActivity(intent5)
                }
            }
            true
        }
        listView!!.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, ChiTietSanPham::class.java)
            val idpro: Int = TrangChu.Companion.dienthoaiArrayList.get(i).idproduct
            finish()
            intent.putExtra(
                "idproduct",
                TrangChu.Companion.dienthoaiArrayList.get(i).idproduct
            )
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, DangNhap::class.java))
    }

    fun getdata() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        val stringRequest = StringRequest(Request.Method.GET, Server.getAllProduct, { response ->
            TrangChu.Companion.dienthoaiArrayList.clear()
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
                    val hinh = jsonObject.getString("hinh")
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
                    TrangChu.Companion.dienthoaiArrayList.add(dt)
                    //                    Toast.makeText(getApplicationContext(), "" + dienthoaiArrayList.size(), Toast.LENGTH_SHORT).show();
                    adapter!!.notifyDataSetChanged()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }) { error -> Toast.makeText(applicationContext, "Lỗi $error", Toast.LENGTH_SHORT).show() }
        VolleySingleton.Companion.getInstance(this).requestQueue?.add<String>(stringRequest)
        //        requestQueue.add(stringRequest);
    }

    fun AnhXa() {
        xemtca = findViewById(R.id.txt_xemtatca)
        search = findViewById(R.id.image_search)
        editseacrchh = findViewById(R.id.search)
        listView = findViewById(R.id.mylistview)
        // item = (MenuItem) findViewById(R.id.Click_to_cart);
    }

    //
    //Hide keyboard
    fun AnBanPhim() {
        try {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        }
    }

    fun getUserIdByEmail(putemail: String): Int {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.getUserByEmail,
            Response.Listener { response ->
                //                Toast.makeText(getApplicationContext(), "xinchao"+response, Toast.LENGTH_LONG).show();
                try {
                    val jsonArray = JSONArray(response)
                    val jsonObject = jsonArray.getJSONObject(0)
                    val iduser = jsonObject.getInt("iduser")
                    TrangChu.Companion.userid = iduser
                    val editor =
                        getSharedPreferences(TrangChu.Companion.FILE_NAME, MODE_PRIVATE).edit()
                    editor.putInt("iduser", iduser)
                    editor.apply()
                    val nameuser = jsonObject.getString("nameuser")
                    val birthday = jsonObject.getString("birthday")
                    val phone = jsonObject.getString("phone")
                    val email = jsonObject.getString("email")
                    val gender = jsonObject.getString("gender")
                    val password = jsonObject.getString("password")
                    val typeuser = jsonObject.getInt("typeuser")
                    val user = User(
                        iduser,
                        nameuser,
                        birthday,
                        phone,
                        email,
                        gender,
                        password,
                        typeuser
                    )
                    //                    userid = (user.getIduser());
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
                param["email"] = putemail
                return param
            }
        }
        //        requestQueue.add(stringRequest);
        VolleySingleton.Companion.getInstance(this).requestQueue?.add<String>(stringRequest)
        return TrangChu.Companion.userid
    }

    fun icon_dienthoai(view: View?) {
        finish()
        val intent = Intent(applicationContext, DanhSachSP::class.java)
        startActivity(intent)
    }

    fun btngiohang(view: View?) {
        startActivity(Intent(applicationContext, GioHang::class.java))
    }

    fun xemtatca(view: View?) {
        startActivity(Intent(applicationContext, DanhSachSP::class.java))
    }

    companion object {
        var dienthoaiArrayList = ArrayList<DienThoai>()
        private const val FILE_NAME = "myFile"
        var email_user = ""
        private var userid = 0
    }
}