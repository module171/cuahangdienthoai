package com.example.do_an_android.Model

import java.io.Serializable

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
import com.example.do_an_android.Util.VolleySingleton
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
import com.example.do_an_android.Util.DienthoaiAdapter
import com.example.do_an_android.Activity.TrangChu
import com.example.do_an_android.Activity.ThemSP
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import com.example.do_an_android.Activity.ChiTietSanPham
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.example.do_an_android.Admin.Admin
import com.example.do_an_android.Util.UserAdapter
import com.example.do_an_android.Util.LoaiAdapter
import com.example.do_an_android.Activity.ThemLoaiSP
import android.widget.EditText
import com.example.do_an_android.Util.CheckConnection
import com.example.do_an_android.Activity.DangNhap
import android.view.WindowManager
import android.content.SharedPreferences
import com.example.do_an_android.Activity.DatHang
import com.example.do_an_android.Util.itemInCartAdapter
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

class DienThoai : Serializable {
    var idproduct = 0
    var nameproduct: String? = null
    var price = 0
    var manhinh: String? = null
    var hdh: String? = null
    var camerasau: String? = null
    var cameratruoc: String? = null
    var chip: String? = null
    var ram: String? = null
    var bnt: String? = null
    var sim: String? = null
    var pinsac: String? = null
    var sum = 0
    var idtype = 0
    var hinh: String? = null

    constructor() {}
    constructor(
        idproduct: Int,
        nameproduct: String?,
        price: Int,
        manhinh: String?,
        hdh: String?,
        camerasau: String?,
        cameratruoc: String?,
        chip: String?,
        ram: String?,
        bnt: String?,
        sim: String?,
        pinsac: String?,
        sum: Int,
        idtype: Int,
        hinh: String?
    ) {
        this.idproduct = idproduct
        this.nameproduct = nameproduct
        this.price = price
        this.manhinh = manhinh
        this.hdh = hdh
        this.camerasau = camerasau
        this.cameratruoc = cameratruoc
        this.chip = chip
        this.ram = ram
        this.bnt = bnt
        this.sim = sim
        this.pinsac = pinsac
        this.sum = sum
        this.idtype = idtype
        this.hinh = hinh
    }
}