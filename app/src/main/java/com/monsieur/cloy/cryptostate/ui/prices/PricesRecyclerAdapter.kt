package com.monsieur.cloy.cryptostate.ui.prices

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
import com.monsieur.cloy.cryptostate.model.Prices.Prices
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.utilits.myExeptionsTag
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class PricesRecyclerAdapter() : RecyclerView.Adapter<PricesRecyclerAdapter.ViewHolder>() {

    private var items: Prices? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items : Prices){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.prices_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items != null && items!!.items.size > 0){
            val item = items!!.items[position]
            holder.symbol.text = item.symbol
            holder.eurPrice.text = item.priceEUR.toString()
            holder.rubPrice.text = item.priceRUB.toString()
            holder.uahPrice.text = item.priceUAH.toString()
            holder.usdPrice.text = item.priceUSD.toString()
            holder.url.text = item.url
            holder.symbolName.text = item.symbolName
            holder.mainPrice.text = item.getMainPrice().toString()
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
                val viewModel = ViewModelProvider(APP_ACTIVITY).get(MainViewModel::class.java)
                if(!viewModel.removePrice(items!!.items[position])){
                    Log.d(myExeptionsTag, "Ошибка при удалении Price")
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
        var symbol: TextView = itemView.findViewById(R.id.symbol)
        var symbolName: TextView = itemView.findViewById(R.id.symbolName)
        var url: TextView = itemView.findViewById(R.id.url)
        var mainPrice: TextView = itemView.findViewById(R.id.mainPrice)
        var rubPrice: TextView = itemView.findViewById(R.id.price_rub)
        var usdPrice: TextView = itemView.findViewById(R.id.price_usd)
        var eurPrice: TextView = itemView.findViewById(R.id.price_eur)
        var uahPrice: TextView = itemView.findViewById(R.id.price_uah)
        var card: CardView = itemView.findViewById(R.id.card)
        var delete: Button = itemView.findViewById(R.id.delete)
        var hiddenLayout: LinearLayout = itemView.findViewById(R.id.hidden_layout)
        var isOpen = false
    }
}