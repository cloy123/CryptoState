package com.monsieur.cloy.cryptostate.ui.assets

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.model.Assets.Assets
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.utilits.myExeptionsTag
import com.monsieur.cloy.cryptostate.utilits.replaceFragment
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class AssetsRecyclerAdapter() : RecyclerView.Adapter<AssetsRecyclerAdapter.ViewHolder>()  {

    private var items: Assets? = null
    private var viewModel: MainViewModel = ViewModelProvider(APP_ACTIVITY).get(MainViewModel::class.java)

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items : Assets){
        this.items = items
        notifyDataSetChanged()
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

            holder.delete.setOnClickListener {
                if(!viewModel.removeAsset(items!!.items[position])){
                    Log.d(myExeptionsTag, "Ошибка при удалении Asset")
                }
            }

            holder.edit.setOnClickListener {
                val price = viewModel.prices.value?.findPrice(item.symbol)
                if(price != null){
                    replaceFragment(EditAsset(item, price))
                }
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
        var edit: Button = itemView.findViewById(R.id.edit)
        var delete: Button = itemView.findViewById(R.id.delete)
        var hiddenLayout: LinearLayout = itemView.findViewById(R.id.hidden_layout)
        var isOpen = false
    }
}