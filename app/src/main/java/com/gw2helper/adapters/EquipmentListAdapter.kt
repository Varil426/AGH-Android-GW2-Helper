package com.gw2helper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gw2helper.R
import com.gw2helper.entities.Equipment

class EquipmentListAdapter(private val context: Context, private val listOfEquipment: List<Equipment>) : RecyclerView.Adapter<EquipmentListAdapter.EquipmentView>() {

    class EquipmentView(val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView = view.findViewById<TextView>(R.id.equipmentNameTextView)
        val iconImageView = view.findViewById<ImageView>(R.id.equipmentIconImageView)
        val slotTextView = view.findViewById<TextView>(R.id.equipmentSlotTextView)
        val levelTextView = view.findViewById<TextView>(R.id.equipmentLevelTextView)
        val attributesTextView = view.findViewById<TextView>(R.id.equipmentAttributesTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentView {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_equipment_row, parent, false)
        return EquipmentView(view)
    }

    override fun onBindViewHolder(holder: EquipmentView, position: Int) {
        val currentEquipment = listOfEquipment[position]

        holder.nameTextView.text = currentEquipment.name
        when(currentEquipment.rarity){
            "Fine" -> holder.nameTextView.setTextColor(context.resources.getColor(R.color.itemFine))
            "Masterwork" -> holder.nameTextView.setTextColor(context.resources.getColor(R.color.itemMasterwork))
            "Rare" -> holder.nameTextView.setTextColor(context.resources.getColor(R.color.itemMasterwork))
            "Exotic" -> holder.nameTextView.setTextColor(context.resources.getColor(R.color.itemExotic))
            "Ascended" -> holder.nameTextView.setTextColor(context.resources.getColor(R.color.itemAscended))
            "Legendary" -> holder.nameTextView.setTextColor(context.resources.getColor(R.color.itemLegendary))
            else ->  holder.nameTextView.setTextColor(context.resources.getColor(R.color.itemBasic))
        }
        holder.slotTextView.text = currentEquipment.slot
        holder.levelTextView.text = currentEquipment.level.toString()

        val attributesBuilder = StringBuilder()
        for (attributeKey in currentEquipment.attributes.keys) {
            attributesBuilder.appendLine("$attributeKey: ${currentEquipment.attributes[attributeKey]}")
        }
        holder.attributesTextView.text = attributesBuilder.toString()

        if (currentEquipment.icon != null) {
            Glide.with(holder.view).load(currentEquipment.icon).into(holder.iconImageView)
        }
    }

    override fun getItemCount(): Int {
        return listOfEquipment.size
    }

}