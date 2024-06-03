package com.example.xml_catalog_parser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xml_catalog_parser.R
import com.example.xml_catalog_parser.databinding.CdsListItemBinding
import com.example.xml_catalog_parser.model.CD

class CatalogAdapter(
    private var cds: List<CD>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(cd: CD)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = CdsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val cd = cds[position]
        holder.binding.tvTitle.text = cd.title
        holder.binding.tvArtist.text = cd.artist
    }

    override fun getItemCount(): Int = cds.size

    fun updateData(newCds: List<CD>) {
        cds = newCds
        notifyDataSetChanged()
    }

    inner class CatalogViewHolder(val binding: CdsListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(cds[position])
            }
        }
    }
}
