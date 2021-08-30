package com.monsieur.cloy.cryptostate.ui.assets

import android.annotation.SuppressLint
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
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.utilits.replaceFragment
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class AssetsRecyclerAdapter() : RecyclerView.Adapter<AssetsRecyclerAdapter.ViewHolder>()  {

    private var assets: List<Asset>? = null
    private var viewModel: MainViewModel = ViewModelProvider(APP_ACTIVITY).get(MainViewModel::class.java)

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(assets : List<Asset>){
        this.assets = assets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.assets_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(assets != null && assets!!.isNotEmpty()){
            val asset = assets!![position]
            holder.symbol.text = asset.symbol
            holder.asset.text = asset.asset.toString()
            holder.mainQuantity.text = asset.mainQuantity.toString()
            holder.quantityRUB.text = asset.quantityRUB.toString()
            holder.quantityUSD.text = asset.quantityUSD.toString()
            holder.quantityEUR.text = asset.quantityEUR.toString()
            holder.quantityUAH.text = asset.quantityUAH.toString()
            holder.averagePrice.text = asset.averagePrice.toString()
            holder.change.text = asset.change.toString()
            if(asset.isDefaultFiatAsset){
              //  holder.delete.visibility = View.GONE
                holder.linearAveragePrice.visibility = View.GONE
                holder.linearChange.visibility = View.GONE
            }else{
               // holder.delete.visibility = View.VISIBLE
                holder.linearAveragePrice.visibility = View.VISIBLE
                holder.linearChange.visibility = View.VISIBLE
            }
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
                viewModel.removeAsset(assets!![position])
            }

            holder.edit.setOnClickListener {
                replaceFragment(EditAsset(asset))
            }
        }
    }

    override fun getItemCount(): Int {
        return if(assets != null){
            assets!!.size
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
        var linearAveragePrice: LinearLayout = itemView.findViewById(R.id.linear_average_price)
        var linearChange: LinearLayout = itemView.findViewById(R.id.linear_change)
    }
}