package com.albertmed.sizlectorcodigos.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albertmed.sizlectorcodigos.data.model.ScanData
import com.albertmed.sizlectorcodigos.databinding.ItemScanBinding

class ScanAdapter : ListAdapter<ScanData, ScanAdapter.ScanViewHolder>(ScanDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        val binding = ItemScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ScanViewHolder(private val binding: ItemScanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scanData: ScanData) {
            binding.scanDataText.text = scanData.scannedData
            binding.scanTimestampText.text = scanData.timestamp
        }
    }
}

class ScanDiffCallback : DiffUtil.ItemCallback<ScanData>() {
    override fun areItemsTheSame(oldItem: ScanData, newItem: ScanData): Boolean {
        return oldItem.timestamp == newItem.timestamp && oldItem.scannedData == newItem.scannedData
    }

    override fun areContentsTheSame(oldItem: ScanData, newItem: ScanData): Boolean {
        return oldItem == newItem
    }
} 