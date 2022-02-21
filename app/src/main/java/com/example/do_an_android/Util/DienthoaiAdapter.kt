package com.example.do_an_android.Util

import com.example.do_an_android.Model.LoaiSanPham
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.do_an_android.R
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.example.do_an_android.Activity.ChiTietSanPham
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.example.do_an_android.Admin.Admin
import com.example.do_an_android.Util.UserAdapter
import com.example.do_an_android.Util.LoaiAdapter
import com.example.do_an_android.Activity.ThemLoaiSP
import com.example.do_an_android.Util.CheckConnection
import com.example.do_an_android.Activity.DangNhap
import android.view.WindowManager
import android.content.SharedPreferences
import com.example.do_an_android.Activity.DatHang
import com.example.do_an_android.Util.itemInCartAdapter
import com.example.do_an_android.Activity.DanhSachSP
import com.example.do_an_android.Activity.DangKy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_android.Activity.TimKiemSP
import androidx.viewpager.widget.ViewPager
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.*
import java.text.DecimalFormat
import java.util.ArrayList

class DienthoaiAdapter(var context: Context, var arraydienthoai: ArrayList<DienThoai>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return arraydienthoai.size
    }

    override fun getItem(i: Int): Any {
        return arraydienthoai[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    inner class ViewHolder {
        var title: TextView? = null
        var subtitle: TextView? = null
        var motasanpham: TextView? = null
        var soluong: TextView? = null
        var hinh: ImageView? = null
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        var viewHolder: ViewHolder? = null
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.dong_sanpham_dienthoai, null)
            viewHolder!!.title = view.findViewById(R.id.title)
            viewHolder.subtitle = view.findViewById(R.id.subtitle)
            viewHolder.soluong = view.findViewById(R.id.soluongton_sanpham)
            viewHolder.motasanpham = view.findViewById(R.id.mota_sanpham)
            viewHolder.hinh = view.findViewById(R.id.hinhDienThoai)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val sanpham = getItem(i) as DienThoai
        viewHolder!!.title!!.text = sanpham.getNameproduct() + ""
        val formatter = DecimalFormat("###,###,###")
        viewHolder.subtitle!!.text = formatter.format(sanpham.getPrice().toLong()) + "" + " VNĐ"
        viewHolder.soluong!!.text =
            "Số lượng còn lại : " + formatter.format(+sanpham.getSum().toLong())
        viewHolder.motasanpham!!.text = sanpham.getBnt()
        Glide.with(context).load(sanpham.getHinh()).into(viewHolder.hinh!!)
        //        ImageView selectedImage = (ImageView) view.findViewById(R.id.buckysImage);
        //viewHolder.hinh.setImageResource(sanpham.getHinh());
        return view
    }
}