package com.example.money_manager_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.databinding.ItemLanguageBinding
import com.example.money_manager_app.model.LanguageModel
import com.example.money_manager_app.utils.loadImage

class LanguageAdapter(
    private val onLanguageSelected: (LanguageModel) -> Unit
) : ListAdapter<LanguageModel, LanguageAdapter.LanguageViewHolder>(LanguageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding, onLanguageSelected)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LanguageViewHolder(
        private val binding: ItemLanguageBinding,
        private val onLanguageSelected: (LanguageModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(languageModel: LanguageModel) {
            binding.tvLanguage.text = languageModel.languageName
            binding.ivFlag.loadImage(languageModel.flag)
            binding.ivSelect.isActivated = languageModel.isCheck

            binding.root.setOnClickListener {
                onLanguageSelected(languageModel)
            }
        }
    }
}

class LanguageDiffCallback : DiffUtil.ItemCallback<LanguageModel>() {
    override fun areItemsTheSame(oldItem: LanguageModel, newItem: LanguageModel): Boolean {
        return oldItem.languageName == newItem.languageName
    }

    override fun areContentsTheSame(oldItem: LanguageModel, newItem: LanguageModel): Boolean {
        return oldItem == newItem
    }
}