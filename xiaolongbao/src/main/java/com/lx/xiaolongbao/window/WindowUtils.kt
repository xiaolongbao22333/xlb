package com.lx.xiaolongbao.window;

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import cn.carbswang.android.numberpickerview.library.NumberPickerView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.CustomListener
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.contrarywind.view.WheelView
import com.lx.xiaolongbao.R
import com.lx.xiaolongbao.bean.CityDataBean
import com.lx.xiaolongbao.bean.Pcascode
import com.lx.xiaolongbao.utils.PopWinUtils
import com.lx.xiaolongbao.utils.ToastUtils
import com.lx.xiaolongbao.widget.JEditText
import com.lx.xiaolongbao.widget.JTextView
import com.lx.xiaolongbao.widget.JView
import java.util.*

import kotlin.collections.ArrayList


object WindowUtils {


    fun selectTime(activity: Activity, block: (ms: Long) -> Unit) {
        selectTime(activity, "", booleanArrayOf(true, true, true, false, false, false), block)
    }


    fun selectTime(
        activity: Activity,
        title: String,
        type: BooleanArray,
        block: (ms: Long) -> Unit
    ) {

        val selectedDate = Calendar.getInstance()//系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(2010, 0, 1)
        val endDate = Calendar.getInstance()
        endDate.set(2099, 11, 30)

        var pickerView: TimePickerView? = null

        val timeSelectListener = OnTimeSelectListener { date, v ->
            block(date.time)
        }

        val customListener = CustomListener { v ->

            val confirm = v.findViewById<View>(R.id.tv_confirm) as View
            val cancel = v.findViewById<View>(R.id.tv_cancel) as View
            val titles = v.findViewById<JTextView>(R.id.title) as JTextView

            titles.text(title)
            confirm.setOnClickListener {
                pickerView!!.returnData()
                pickerView!!.dismiss()
            }
            cancel.setOnClickListener { pickerView!!.dismiss() }
        }

        pickerView = TimePickerBuilder(activity, timeSelectListener)
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setLayoutRes(R.layout.pickerview_custom_date_options2, customListener)
            .setType(type)
            .setLabel("年", "月", "日", "时", "分", "秒")
            .setItemVisibleCount(5)
            .setTextXOffset(0, 0, 0, 0, 0, 0)
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setDecorView(activity.window.decorView.findViewById(android.R.id.content) as ViewGroup)
            .isDialog(false)
            .isAlphaGradient(false)
            .setContentTextSize(16)
            .setLineSpacingMultiplier(2.4f)
            .setDividerColor(Color.parseColor("#E6E6E6"))
            .build()
        pickerView.show()
    }



    fun showNotTitlePopWin(activity: Activity, msg: String, block: () -> Unit) {
        val view = JView.inflate(activity, R.layout.popwin_not_title)

        val popwin = PopWinUtils(view, activity, true, false)

        val content = view.findViewById(R.id.content) as JTextView
        val cancel = view.findViewById(R.id.cancel) as JTextView
        val confirm = view.findViewById(R.id.confirm) as JTextView

        content.text(msg)
        confirm.setOnClickListener {
            block()
            popwin.dismiss()
        }
        cancel.setOnClickListener { popwin.dismiss() }

        popwin.setAnimationStyle(R.style.popwin_common_anim_style)
        popwin.show(Gravity.CENTER)
    }

    fun showNotTitlePopWin(
        activity: Activity,
        msg: String,
        confirmText: String,
        block: () -> Unit
    ) {
        val view = JView.inflate(activity, R.layout.popwin_not_title)

        val popwin = PopWinUtils(view, activity, true, false)

        val content = view.findViewById(R.id.content) as JTextView
        val cancel = view.findViewById(R.id.cancel) as JTextView
        val confirm = view.findViewById(R.id.confirm) as JTextView
        confirm.text(confirmText)
        content.text(msg)
        confirm.setOnClickListener {
            block()
            popwin.dismiss()
        }
        cancel.setOnClickListener { popwin.dismiss() }

        popwin.setAnimationStyle(R.style.popwin_common_anim_style)
        popwin.show(Gravity.CENTER)
    }

    fun showNotTitlePopWin(
        activity: Activity,
        msg: String,
        confirmText: String,
        cancelText: String,
        block: () -> Unit
    ) {
        val view = JView.inflate(activity, R.layout.popwin_not_title)

        val popwin = PopWinUtils(view, activity, true, false)
        val content = view.findViewById(R.id.content) as JTextView
        val cancel = view.findViewById(R.id.cancel) as JTextView
        val confirm = view.findViewById(R.id.confirm) as JTextView
        confirm.text(confirmText)
        cancel.text(cancelText)
        content.text(msg)
        confirm.setOnClickListener {
            block()
            popwin.dismiss()
        }
        cancel.setOnClickListener { popwin.dismiss() }

        popwin.setAnimationStyle(R.style.popwin_common_anim_style)
        popwin.show(Gravity.CENTER)
    }

    /**
     * desc    : 显示带标题的popwin
     */
    fun showTitlePopWin(activity: Activity, title: String, msg: String, block: () -> Unit) {
        val view = JView.inflate(activity, R.layout.popwin_title)

        val popwin = PopWinUtils(view, activity, true, false)

        val content = view.findViewById(R.id.content) as JTextView
        val cancel = view.findViewById(R.id.cancel) as JTextView
        val confirm = view.findViewById(R.id.confirm) as JTextView
        val titles = view.findViewById(R.id.title) as JTextView

        titles.text(title)
        content.text(msg)
        confirm.setOnClickListener {
            block()
            popwin.dismiss()
        }
        cancel.setOnClickListener { popwin.dismiss() }

        popwin.setAnimationStyle(R.style.popwin_common_anim_style)
        popwin.show(Gravity.CENTER)
    }


    /**
     * desc    : 单键弹框
     */
    fun showNotTitlePopWin(activity: Activity, msg: String) {
        val view = JView.inflate(activity, R.layout.popwin_not_title_single_key)

        val popwin = PopWinUtils(view, activity, true, false)

        val content = view.findViewById(R.id.content) as JTextView
        val confirm = view.findViewById(R.id.confirm) as JTextView

        content.text(msg)
        confirm.setOnClickListener {
            popwin.dismiss()
        }

        popwin.setAnimationStyle(R.style.popwin_common_anim_style)
        popwin.show(Gravity.CENTER)
    }


    /**
     * desc    : 带标题单键弹框
     */
    fun showTitlePopWin(activity: Activity, title: String, msg: String) {
        val view = JView.inflate(activity, R.layout.popwin_title_single_key)

        val popwin = PopWinUtils(view, activity, true, false)

        val content = view.findViewById(R.id.content) as JTextView
        val confirm = view.findViewById(R.id.confirm) as JTextView
        val titles = view.findViewById(R.id.title) as JTextView

        titles.text(title)
        content.text(msg)
        confirm.setOnClickListener {
            popwin.dismiss()
        }

        popwin.setAnimationStyle(R.style.popwin_common_anim_style)
        popwin.show(Gravity.CENTER)
    }

    fun showEditPopWin(activity: Activity, hint: String?, block: (String) -> Unit) {
        val view = JView.inflate(activity, R.layout.popwin_edit)

        val popwin = PopWinUtils(view, activity, true, false)

        val confirm = view.findViewById(R.id.confirm) as JTextView
        val cancel = view.findViewById(R.id.cancel) as JTextView
        val edit = view.findViewById(R.id.edit) as JEditText

        edit.hint = hint
        confirm.setOnClickListener {
            block(edit.text())
            popwin.dismiss()
        }
        cancel.setOnClickListener { popwin.dismiss() }

        popwin.setAnimationStyle(R.style.popwin_common_anim_style)
        popwin.show(Gravity.CENTER)
    }


    fun showSelect(activity: Activity, vararg str: String, block: (IMsg) -> Unit) {

        val list = mutableListOf<IMsg>()

        for (i in str.indices) {
            list.add(object : IMsg {
                override fun getMsg(): String = str[i]
            })
        }
        showSelect(activity, null, list, block)
    }


    /**
     * author  : jack(黄冲)
     * time    : 2020-04-10 11:26
     * desc    : 通用单键选择
     */
    fun <T : IMsg> showSelect(activity: Activity, list: List<T>?, block: (T) -> Unit) {
        showSelect(activity, null, list, block)
    }

    fun showSelectStr(activity: Activity, list: List<String>?, block: (IMsg) -> Unit) {

        val iMsgList = ArrayList<IMsg>()

        list?.forEach {
            iMsgList.add(object : IMsg {
                override fun getMsg(): String = it
            })
        }

        showSelect(activity, null, iMsgList, block)
    }

    /**
     * desc    : 通用单键选择
     * @param reset 传入指定的对象, 复原位置
     */
    fun <T : IMsg> showSelect(
        activity: Activity,
        reset: String?,
        list: List<T>?,
        block: (T) -> Unit
    ) {

        if (list.isNullOrEmpty()) {
            ToastUtils.d("当前无数据")
            return
        }

        val view = JView.inflate(activity, R.layout.popwin_select_epository_type)

        val popWinUtils = PopWinUtils(view, activity, true, false)

        val pickerView = view.findViewById<View>(R.id.numberPickerView) as NumberPickerView

        val arrayOf = arrayOfNulls<String>(list.size)


        for (i in list.indices) {
            arrayOf[i] = list.get(i).getMsg()
        }

        val index = arrayOf.indexOfFirst { it == reset }
        pickerView.displayedValues = arrayOf
        pickerView.minValue = 0
        pickerView.maxValue = arrayOf.size - 1
        pickerView.value = if (index == -1) 0 else index

        pickerView.wrapSelectorWheel = false


        view.findViewById<View>(R.id.tv_cancel).setOnClickListener { v -> popWinUtils.dismiss() }
        view.findViewById<View>(R.id.tv_confirm).setOnClickListener { v ->
            block(list[pickerView.value])
            popWinUtils.dismiss()
        }
        popWinUtils.setAnimationStyle(R.style.popwin_b_to_t_tran_anim_style)
        popWinUtils.show(Gravity.BOTTOM)
    }


    fun isShow(): Boolean = PopWinUtils.isShow()

    fun selectCity(
        activity: Activity,
        list: List<CityDataBean>,
        block: (name1: String, id1: String, name2: String, id2: String, name3: String, id3: String) -> Unit
    ) {
        var pvOptions: OptionsPickerView<CityDataBean>? = null
        val options1Items: MutableList<CityDataBean> = ArrayList()//第一级数据源

        val options2Items: MutableList<List<CityDataBean>> = ArrayList() //第二级数据源

        val options3Items: MutableList<List<List<CityDataBean>>> = ArrayList()


        for (a in list.indices) { //遍历省份
            options1Items.add(list.get(a))//1级
            val cityList: MutableList<CityDataBean> = java.util.ArrayList() //该省的城市列表（第二级）
            //该省的城市列表（第二级）
            val areaList: MutableList<List<CityDataBean>> =
                java.util.ArrayList() //该省的所有地区列表（第三极）
            val citys = list[a].cityList
            for (b in citys.indices) { //遍历该省份的所有城市
                cityList.add(citys[b]) //添加城市
                val cityAreaList = java.util.ArrayList<CityDataBean>() //该城市的所有三级地区列表
                //如果无地区数据，添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (citys[b] == null
                    || citys[b].cityList.size == 0
                ) {
                    val cityDataBean = CityDataBean()
                    cityDataBean.cityName = ""
                    cityDataBean.cityId = ""
                    cityAreaList.add(cityDataBean)
                } else {
                    for (c in citys[b].cityList.indices) {
                        cityAreaList.add(citys[b].cityList[c])
                    }
                }
                areaList.add(cityAreaList) //添加该省所有地区数据
            }
            options2Items.add(cityList)
            /**
             * 添加地区数据
             */
            options3Items.add(areaList)
        }

        pvOptions = OptionsPickerBuilder(
            activity,
            object : OnOptionsSelectListener {
                override fun onOptionsSelect(
                    pos1: Int, pos2: Int, pos3: Int,v: View?
                ) {
                    block(
                    options1Items[pos1].cityName,
                    options1Items[pos1].cityId,
                    options2Items[pos1][pos2].cityName,
                    options2Items[pos1][pos2].cityId,
                    options3Items[pos1][pos2][pos3].cityName,
                    options3Items[pos1][pos2][pos3].cityId
                )
                }

                override fun onOptionsSel(
                    options1: Int,
                    options2: Int,
                    options3: Int,
                    options4: Int,
                    v: View?
                ) {
                    ToastUtils.show("options1:$options1---options2:$options2---options3:$options3---options4:$options4")

                }
            }
                )
            .setDividerColor(Color.BLACK)
            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
            .setContentTextSize(14)
            .setDividerColor(Color.parseColor("#F2F2F5"))
            .setDividerType(WheelView.DividerType.FILL)
            .setDecorView(activity.window.decorView.findViewById(android.R.id.content))
            .setLayoutRes(R.layout.view_pick) {
                it.findViewById<JTextView>(R.id.cancel).setOnClickListener {
                    pvOptions!!.dismiss()
                }
                it.findViewById<JTextView>(R.id.confirm).setOnClickListener {
                    pvOptions!!.returnData()
                    pvOptions!!.dismiss()
                }
            }
            .setDecorView(activity.window.decorView.findViewById(android.R.id.content))
            .build()
        pvOptions!!.setPicker(options1Items, options2Items, options3Items)
        pvOptions.show()
    }

    fun selectCityF(
        activity: Activity,
        list: List<Pcascode>,
        block: (name1: String, id1: String, name2: String, id2: String,
                name3: String, id3: String, name4: String,id4: String) -> Unit
    ) {
        var pvOptions: OptionsPickerView<Pcascode>? = null
        val options1Items: MutableList<Pcascode?> = ArrayList() //第一级数据源
        val options2Items: MutableList<List<Pcascode?>> = ArrayList()//第二级数据源
        val options3Items: MutableList<List<List<Pcascode?>>> =ArrayList() //第三级数据源
        val options4Items: MutableList<List<List<List<Pcascode?>>>> = ArrayList() //第四级数据源
        for (a in list.indices) {
            options1Items.add(list[a])
            val cityList: MutableList<Pcascode?> = ArrayList() //该省的城市列表（第二级）
            val areaList: MutableList<List<Pcascode?>> = ArrayList() //该省的所有地区列表（第三级）
            val districtList: MutableList<List<List<Pcascode?>>> =ArrayList() //该省的所有街道地区列表（第四级）
            val citys = list[a].children //二级数据源
            for (b in citys.indices) {
                cityList.add(citys[b])
                val cityAreaList =  ArrayList<Pcascode>() //三级
                val cityDistrictList :MutableList<List<Pcascode>> =ArrayList()//四级
                if (null == citys[b] || citys[b].children.size == 0) {
                    val cityDataBean = Pcascode()
                    cityDataBean.code = ""
                    cityDataBean.name = ""
                    cityDataBean.children = ArrayList()
                    cityAreaList.add(cityDataBean)
                } else {
                    val areas = citys[b]!!.children //三级数据源
                    for (c in areas.indices) {
                        cityAreaList.add(areas[c])
                        val lis=ArrayList<Pcascode>()
                        if (null == areas[c] || areas[c].children.size == 0) {
                            val cityDataBean = Pcascode()
                            cityDataBean.name = ""
                            cityDataBean.code = ""
                            cityDataBean.children = ArrayList()
                            lis.add(cityDataBean)
                            cityDistrictList.add(lis)
                        } else {
                            lis.addAll(areas[c].children)
                            cityDistrictList.add(lis)
                        }
                    }
                    districtList.add(cityDistrictList)//添加地区街道数据
                }
                areaList.add(cityAreaList) //添加该省所有地区数据
            }
            //市
            options2Items.add(cityList)
            //区
            options3Items.add(areaList)
            //街道
            options4Items.add(districtList)
        }

        pvOptions = OptionsPickerBuilder(
            activity,
            object : OnOptionsSelectListener {
                override fun onOptionsSelect(
                    pos1: Int, pos2: Int, pos3: Int,v: View?
                ) {

                }

                override fun onOptionsSel(
                    options1: Int,
                    options2: Int,
                    options3: Int,
                    options4: Int,
                    v: View
                ) {
                    block(
                        options1Items[options1]!!.name,
                        options1Items[options1]!!.code,
                        options2Items[options1][options2]!!.name,
                        options2Items[options1][options2]!!.code,
                        options3Items[options1][options2][options3]!!.name,
                        options3Items[options1][options2][options3]!!.code,
                        options4Items[options1][options2][options3][options4]!!.name,
                        options4Items[options1][options2][options3][options4]!!.code,
                    )
                    ToastUtils.show("options1:$options1---options2:$options2---options3:$options3---options4:$options4")

                }
            }
        )
            .setDividerColor(Color.BLACK)
            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
            .setContentTextSize(14)
            .setDividerColor(Color.parseColor("#F2F2F5"))
            .setDividerType(WheelView.DividerType.FILL)
            .setDecorView(activity.window.decorView.findViewById(android.R.id.content))
            .setLayoutRes(R.layout.view_pick) {
                it.findViewById<JTextView>(R.id.cancel).setOnClickListener {
                    pvOptions!!.dismiss()
                }
                it.findViewById<JTextView>(R.id.confirm).setOnClickListener {
                    pvOptions!!.returnData()
                    pvOptions!!.dismiss()
                }
            }
            .setDecorView(activity.window.decorView.findViewById(android.R.id.content))
            .build()
        pvOptions!!.setPicker(options1Items, options2Items, options3Items,options4Items)
        pvOptions.show()
    }
    interface IMsg {
        fun getMsg(): String
    }





}