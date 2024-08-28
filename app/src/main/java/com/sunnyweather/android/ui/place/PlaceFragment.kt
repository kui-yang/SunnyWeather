package com.sunnyweather.android.ui.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.databinding.FragmentPlaceBinding

/**
 * @author: YangKui
 * @data: 2024/8/28 09:40
 * @des:
 *
 */
class PlaceFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this)[PlaceViewModel::class.java]
    }
    private lateinit var adapter: PlaceAdapter
    private lateinit var placeBinding: FragmentPlaceBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchPlaceEdit: EditText
    private lateinit var bgImageView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        placeBinding = FragmentPlaceBinding.inflate(inflater)
        return placeBinding.root
    }

    private fun initView() {
        recyclerView = placeBinding.recyclerView
        searchPlaceEdit = placeBinding.searchPlaceEdit
        bgImageView = placeBinding.bgImageView
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}