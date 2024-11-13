package com.example.money_manager_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.databinding.ItemLanguage1Binding
import com.example.money_manager_app.databinding.ItemLanguage2Binding
import com.example.money_manager_app.model.LanguageModel
import com.example.money_manager_app.utils.loadImage
import com.example.money_manager_app.utils.setOnSafeClickListener

class LanguageAdapter(
    private val languages: List<LanguageModel>,
    private val viewType: Int,
    private val onLanguageSelected: (LanguageModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> {
                val binding = ItemLanguage1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                LanguageViewHolder1(binding)
            }
            VIEW_TYPE_2 -> {
                val binding = ItemLanguage2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                LanguageViewHolder2(binding, onLanguageSelected)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LanguageViewHolder1 -> holder.bind(languages[position])
            is LanguageViewHolder2 -> holder.bind(languages[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun getItemCount(): Int = languages.size

    inner class LanguageViewHolder1(private val binding: ItemLanguage1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(language: LanguageModel) {
            binding.tvLanguage.text = language.languageName
            binding.root.setOnSafeClickListener { onLanguageSelected(language) }
        }
    }

    inner class LanguageViewHolder2(
        private val binding: ItemLanguage2Binding,
        private val onLanguageSelected: (LanguageModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(languageModel: LanguageModel) {
            binding.tvLanguage.text = languageModel.languageName
            binding.ivFlag.loadImage(languageModel.flag)
            binding.ivSelect.isActivated = languageModel.isCheck

            binding.root.setOnClickListener {
                click(languageModel)
            }
        }

        private fun click(languageModel: LanguageModel) {
            onLanguageSelected(languageModel)
            languageModel.isCheck = true
            notifyItemChanged(adapterPosition)
            handleLangDisplay(languageModel)
        }

        private fun handleLangDisplay(languageModel: LanguageModel) {
            for (i in languages.indices) {
                if (languages[i].languageName != languageModel.languageName && languages[i].isCheck) {
                    languages[i].isCheck = false
                    notifyItemChanged(i, "payload")
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE_1 = 1
        const val VIEW_TYPE_2 = 2
    }
}