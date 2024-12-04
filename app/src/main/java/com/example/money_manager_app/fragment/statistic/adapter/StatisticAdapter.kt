package com.example.money_manager_app.fragment.statistic.adapter

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.CalendarSummary
import com.example.money_manager_app.data.model.Stats
import com.example.money_manager_app.databinding.ListStatisticBalanceBinding
import com.example.money_manager_app.databinding.ListStatisticOverviewBinding
import com.example.money_manager_app.databinding.ListStatisticPieBinding
import com.example.money_manager_app.databinding.ListStatisticMoreBinding
import com.example.money_manager_app.utils.Helper
import com.example.money_manager_app.utils.SharePreferenceHelper
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.data.Entry
import java.text.NumberFormat
import java.util.Locale

class StatisticAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: OnItemClickListener? = null
    private var openingBalance: Long = 0
    private var endingBalance: Long = 0
    private var summary: CalendarSummary = CalendarSummary(0.0, 0.0)
    private var pieStatsList: List<Stats> = ArrayList()

    init {
        setBalance(5000, 3000)
        setOverviewSummary(CalendarSummary(2000.0, 3000.0))
        setPieStatsList(
            listOf(
                Stats("Food", "#FF0000", R.drawable.ic_statistic, 1000, 50.0, 1, 1, 1),
                Stats("Transport", "#00FF00", R.drawable.all_category, 1000, 50.0, 2, 1, 1)
            )
        )
    }

    inner class BalanceHolder(private val binding: ListStatisticBalanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(openingBalance: Long, endingBalance: Long) {
            binding.openingLabel.text = openingBalance.toString()
            binding.endingLabel.text = endingBalance.toString()
        }
    }

    inner class OverviewHolder(private val binding: ListStatisticOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(summary: CalendarSummary) {
            binding.incomeLabel.text =
                Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(context), summary.income)
            binding.expenseLabel.text =
                Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(context), summary.expense)
            binding.netLabel.text =
                Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(context), summary.netIncome)
        }
    }

    inner class PieHolder(private val binding: ListStatisticPieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pieStatsList: List<Stats>) {
            setPieChart(binding.pieChart)
            binding.pieStatLabel1.text = "Hêllo"
        }
    }

    inner class MoreHolder(private val binding: ListStatisticMoreBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener?.OnItemClick(view, layoutPosition)
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(v: View, position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_BALANCE
            1 -> TYPE_OVERVIEW
            2 -> TYPE_PIE
            else -> TYPE_MORE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BALANCE -> BalanceHolder(ListStatisticBalanceBinding.inflate(inflater, parent, false))
            TYPE_OVERVIEW -> OverviewHolder(ListStatisticOverviewBinding.inflate(inflater, parent, false))
            TYPE_PIE -> PieHolder(ListStatisticPieBinding.inflate(inflater, parent, false))
            else -> MoreHolder(ListStatisticMoreBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BalanceHolder -> holder.bind(openingBalance, endingBalance)
            is OverviewHolder -> holder.bind(summary)
            is PieHolder -> holder.bind(pieStatsList)
        }
    }

    override fun getItemCount(): Int = 5

    fun setBalance(openingBalance: Long, endingBalance: Long) {
        this.openingBalance = openingBalance
        this.endingBalance = endingBalance
    }

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setOverviewSummary(calendarSummary: CalendarSummary) {
        this.summary = calendarSummary
    }

    fun setPieStatsList(list: List<Stats>) {
        this.pieStatsList = list
        notifyDataSetChanged()
    }

    private fun setPieChart(pieChart: PieChart) {
        pieChart.highlightValue(null)
        pieChart.centerText = getCenterAmount()
        pieChart.setUsePercentValues(true)
        pieChart.transparentCircleRadius = 0.0f
        pieChart.holeRadius = 70.0f
        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = true
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.isEnabled = false

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(30.0f, "Part 1"))
        entries.add(PieEntry(70.0f, "Part 2"))

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(Color.RED, Color.BLUE)
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        val data = PieData(dataSet)
        data.setDrawValues(false)
        data.setValueTextSize(14.0f)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextColor(Color.WHITE)

        pieChart.data = data
    }

    companion object{
        private const val TYPE_BALANCE = 0
        private const val TYPE_OVERVIEW = 1
        private const val TYPE_PIE = 2
        private const val TYPE_MORE = 3
    }


    private fun getCenterAmount(): SpannableString {
        var totalAmount: Long = 0
        for (stat in pieStatsList) {
            totalAmount += stat.amount
        }
        val beautifyAmount = Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(context), totalAmount.toDouble())
        val spannableString = SpannableString(context.getString(R.string.expense) + "\n" + beautifyAmount)
        spannableString.setSpan(RelativeSizeSpan(1.0f), 0, spannableString.length - beautifyAmount.length, 0)
        return spannableString
    }
}