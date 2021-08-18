package com.monsieur.cloy.cryptostate.ui.assets

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.model.Assets.Assets
import com.monsieur.cloy.cryptostate.model.Rates.Rates
import com.monsieur.cloy.cryptostate.ui.rates.RatesRecyclerAdapter
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.viewModels.RatesViewModel

class AssetsRecyclerAdapter() : RecyclerView.Adapter<AssetsRecyclerAdapter.ViewHolder>()  {

    private var items: Assets? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items : Assets){
        this.items = items
        notifyDataSetChanged()
    }

    fun getSize() : Int {
        return if(items != null){
            items!!.items.size
        } else{
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.assets_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items != null && items!!.items.size > 0){
            val item = items!!.items[position]
            holder.symbol.text = item.symbol
            holder.asset.text = item.asset.toString()
            holder.mainQuantity.text = item.mainQuantity.toString()
            holder.quantityRUB.text = item.quantityRUB.toString()
            holder.quantityUSD.text = item.quantityUSD.toString()
            holder.quantityEUR.text = item.quantityEUR.toString()
            holder.quantityUAH.text = item.quantityUAH.toString()
            holder.averagePrice.text = item.averagePrice.toString()
            holder.change.text = item.change.toString()
            holder.card.setOnClickListener {
                if(holder.isOpen){
                    holder.hiddenLayout.visibility = View.GONE
                    holder.isOpen = false
                } else{
                    holder.hiddenLayout.visibility = View.VISIBLE
                    holder.isOpen = true
                }
            }

            holder.edit.setOnClickListener {
                Toast.makeText(APP_ACTIVITY, "edit", Toast.LENGTH_SHORT).show()
                //TODO окно изменения
            }
        }
    }

    override fun getItemCount(): Int {
        return if(items != null){
            items!!.items.size
        } else{
            0
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var asset: TextView = itemView.findViewById(R.id.asset)
        var mainQuantity: TextView = itemView.findViewById(R.id.mainQuantity)
        var symbol: TextView = itemView.findViewById(R.id.symbol)
        var quantityRUB: TextView = itemView.findViewById(R.id.quantity_rub)
        var quantityUSD: TextView = itemView.findViewById(R.id.quantity_usd)
        var quantityEUR: TextView = itemView.findViewById(R.id.quantity_eur)
        var quantityUAH: TextView = itemView.findViewById(R.id.quantity_uah)
        var averagePrice: TextView = itemView.findViewById(R.id.averagePrice)
        var change: TextView = itemView.findViewById(R.id.change)
        var card: CardView = itemView.findViewById(R.id.card)
        var edit: TextView = itemView.findViewById(R.id.edit)
        var hiddenLayout: LinearLayout = itemView.findViewById(R.id.hidden_layout)
        var isOpen = false
    }
}