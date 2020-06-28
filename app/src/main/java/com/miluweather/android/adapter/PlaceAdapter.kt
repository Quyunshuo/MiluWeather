package com.miluweather.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.miluweather.android.R
import com.miluweather.android.bean.Place

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  PlaceAdapter
 * @Remark:
 */
class PlaceAdapter(data: MutableList<Place>) :
    BaseQuickAdapter<Place, BaseViewHolder>(R.layout.place_item, data) {

    override fun convert(holder: BaseViewHolder, item: Place) {
        holder.setText(R.id.placeName, item.name)
        holder.setText(R.id.placeAddress, item.address)
    }
}