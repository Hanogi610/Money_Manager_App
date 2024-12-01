package com.example.moneymanager.fragment.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.CalendarRecord
import com.example.money_manager_app.utils.CalendarHelper
import com.example.money_manager_app.utils.Helper
import com.example.money_manager_app.utils.SharePreferenceHelper
import java.util.Calendar
import java.util.Date

class CalendarAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listener: OnItemClickListener? = null
    private var date: Date = Date()
    private val weekdays: Array<String> = arrayOf(
        context.getString(R.string.sun),
        context.getString(R.string.mon),
        context.getString(R.string.tue),
        context.getString(R.string.wed),
        context.getString(R.string.thu),
        context.getString(R.string.fri),
        context.getString(R.string.sat)
    )
    private var list: List<CalendarRecord> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(view: View)
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weekLabel: TextView = itemView.findViewById(R.id.weekLabel)
    }

    inner class ItemViewHolder(itemView: View, val parent: ViewGroup) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val dayLabel: TextView = itemView.findViewById(R.id.dayLabel)
        val incomeLabel: TextView = itemView.findViewById(R.id.incomeLabel)
        val expenseLabel: TextView = itemView.findViewById(R.id.expenseLabel)
        val totalLabel: TextView = itemView.findViewById(R.id.totalLabel)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener?.onItemClick(view)
        }
    }

    override fun getItemCount(): Int {
        val dayOfMonth = CalendarHelper.getDayOfMonth(date)
        var dayOfWeek = CalendarHelper.getDayOfWeek(date) - SharePreferenceHelper.getFirstDayOfWeek(context)
        if (dayOfWeek < 0) dayOfWeek += 7
        val totalDays = dayOfMonth + dayOfWeek
        return (((totalDays / 7) + if (totalDays % 7 == 0) 0 else 1) * 7) + 7
    }

    override fun getItemViewType(position: Int): Int = if (position >= 7) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            HeaderViewHolder(inflater.inflate(R.layout.list_calendar_header, parent, false))
        } else {
            ItemViewHolder(inflater.inflate(R.layout.list_calendar_item, parent, false), parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            val headerViewHolder = holder as HeaderViewHolder
            var firstDayOfWeek = SharePreferenceHelper.getFirstDayOfWeek(context) - 1 + position
            if (firstDayOfWeek >= 7) {
                firstDayOfWeek -= 7
            }
            headerViewHolder.weekLabel.setTextColor(
                context.getColor(R.color.white)
            )
            headerViewHolder.weekLabel.text = weekdays[firstDayOfWeek]
            return
        }
        val itemViewHolder = holder as ItemViewHolder
        var height = (itemViewHolder.parent.height - Helper.convertDpToPixel(context, 30f)) / ((itemCount - 7) / 7f)
        val layoutParams = itemViewHolder.itemView.layoutParams as RecyclerView.LayoutParams
        if (height <= Helper.convertDpToPixel(context, 70f)) {
            height = Helper.convertDpToPixel(context, 70f)
        }
        layoutParams.height = height.toInt()
        itemViewHolder.itemView.layoutParams = layoutParams
        var dayOfWeek = CalendarHelper.getDayOfWeek(date) - SharePreferenceHelper.getFirstDayOfWeek(context)
        if (dayOfWeek < 0) {
            dayOfWeek += 7
        }
        val dayOfMonth = CalendarHelper.getDayOfMonth(date)
        val i = position - 7
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val isSunday = ((SharePreferenceHelper.getFirstDayOfWeek(context) - 1) + position) % 7 == 0
        if (i < dayOfWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - i))
            itemViewHolder.dayLabel.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
            itemViewHolder.expenseLabel.text = ""
            itemViewHolder.incomeLabel.text = ""
            itemViewHolder.totalLabel.text = ""
            itemViewHolder.dayLabel.background = null
            itemViewHolder.dayLabel.setTextColor(
                if (isSunday) context.getColor(R.color.calendar_red_light)
                else context.getColor(R.color.black_light)
            )
            itemViewHolder.itemView.tag = null
            return
        }
        val i2 = i - dayOfWeek
        calendar.add(Calendar.DAY_OF_MONTH, i2)
        if (i2 >= dayOfMonth) {
            if (i2 - dayOfMonth == 0) {
                itemViewHolder.dayLabel.text = "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}"
            } else {
                itemViewHolder.dayLabel.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
            }
            itemViewHolder.expenseLabel.text = ""
            itemViewHolder.incomeLabel.text = ""
            itemViewHolder.totalLabel.text = ""
            itemViewHolder.dayLabel.background = null
            itemViewHolder.dayLabel.setTextColor(
                if (isSunday) context.getColor(R.color.calendar_red_light)
                else context.getColor(R.color.black_light)
            )
            itemViewHolder.itemView.tag = null
            return
        }
        itemViewHolder.dayLabel.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val i3 = i2 + 1
        val isToday = CalendarHelper.isSameMonth(date) == i3
        itemViewHolder.expenseLabel.text = ""
        itemViewHolder.incomeLabel.text = ""
        itemViewHolder.totalLabel.text = ""
        for (record in list) {
            if (record.date == i3) {
                val income = record.income + record.expense
                itemViewHolder.dayLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.stickynote, 0)
                itemViewHolder.expenseLabel.text = Helper.getBeautifyAmount("", record.expense)
                itemViewHolder.incomeLabel.text = Helper.getBeautifyAmount("", record.income)
                itemViewHolder.totalLabel.text = Helper.getBeautifyAmount("", income)
                break
            }
        }
        if (isToday) {
            itemViewHolder.dayLabel.setBackgroundColor(context.getColor(R.color.blue_alpha))
        } else {
            itemViewHolder.dayLabel.background = null
        }
        itemViewHolder.dayLabel.setTextColor(
            if (isSunday) context.getColor(R.color.calendar_red_dark)
            else context.getColor(R.color.black_dark)
        )
        itemViewHolder.itemView.tag = i3
    }

    fun setDate(date: Date) {
        this.date = date
        notifyDataSetChanged()
    }

    fun setList(newList: List<CalendarRecord>) {
        this.list = newList
        notifyDataSetChanged()
    }

    fun setListener(newListener: OnItemClickListener) {
        this.listener = newListener
    }
}