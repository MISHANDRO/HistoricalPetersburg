package com.example.historicalpetersburg.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.FragmentMapBinding
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.filters.GroupFilterChain
import com.example.historicalpetersburg.map.main.filters.TypeFilterChain
import com.example.historicalpetersburg.map.main.views.bottomsheet.GroupsRoutesListBottomSheet
import com.example.historicalpetersburg.map.main.views.bottomsheet.ExtraBottomSheet
import com.example.historicalpetersburg.map.main.views.adapters.GroupListAdapter
import com.example.historicalpetersburg.map.main.views.adapters.HistoricalObjectListAdapter
import com.example.historicalpetersburg.map.main.views.behaviors.ListBottomSheetBehaviorCallback
import com.example.historicalpetersburg.map.main.views.listeners.GroupListSpinnerSelectedListener
import com.example.historicalpetersburg.map.main.views.listeners.TypeSelectionListener
import com.example.historicalpetersburg.tools.value.StringVal
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MapFragment() : Fragment() {
    private var savedView: View? = null

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var bottomSheet: GroupsRoutesListBottomSheet
    lateinit var extraBottomSheet: ExtraBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO создать менеджер
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (savedView != null) {
            return savedView as View
        }

        val mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textMap
//        mapViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        // задание параметров
        setupMapManager()
        savedView = root

        // TODO

//        val style = """
//                    [
//                        {
//                            "types": "point",
//                            "tags": {
//                                "any": ["road"]
//                            },
//                            "stylers": {
//                                "visibility": "off"
//                            }
//                        },
//                        {
//                            "types": ["point", "polyline"],
//                            "tags": {
//                                "any": ["poi", "transit"],
//                                "none": ["major_landmark", "transit_entrance"]
//                            },
//                            "stylers": {
//                                "visibility": "off"
//                            }
//                        },
//                        {
//                            "types": "polyline",
//                            "tags": {
//                                "any": ["road"],
//                                "none": ["road_minor", "road_unclassified"]
//                            },
//                            "elements": ["geometry.fill"],
//                            "stylers": {
//                                "color": "c70000"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["path"]
//                            },
//                            "elements": ["geometry.outline"],
//                            "stylers": {
//                                "color": "592727"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["road_minor", "road_unclassified"]
//                            },
//                            "stylers": {
//                                "color": "c70000",
//                                "scale": 0.3
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["water"]
//                            },
//                            "stylers": {
//                                "color": "00058a"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["land"]
//                            },
//                            "stylers": {
//                                "color": "014242"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["landcover"]
//                            },
//                            "stylers": {
//                                "color": "024d01"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["urban_area", "national_park"]
//                            },
//                            "stylers": {
//                                "color": "024d01"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["terrain"]
//                            },
//                            "stylers": {
//                                "color": "024d01"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["structure"]
//                            },
//                            "stylers": {
//                                "color": "b00707"
//                            }
//                        },
//                        {
//                            "types": "polygon",
//                            "tags": {
//                                "any": ["admin"]
//                            },
//                            "stylers": {
//                                "color": "592727"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["district", "address"]
//                            },
//                            "elements": ["label.text.fill"],
//                            "stylers": {
//                                "color": "000"
//                            }
//                        },
//                        {
//                            "tags": {
//                                "any": ["district", "address"]
//                            },
//                            "elements": ["label.text.outline"],
//                            "stylers": {
//                                "color": "fff"
//                            }
//                        }
//                    ]
//                    """.trimIndent()
//        binding.mapview.map.setMapStyle(style)
        val styleOnlyHide = """
                    [
                        {
                            "types": "point",
                            "tags": {
                                "any": ["road"]
                            },
                            "stylers": {
                                "visibility": "off"
                            }
                        },
                        {
                            "types": ["point", "polyline"],
                            "tags": {
                                "any": ["poi", "transit"],
                                "none": ["major_landmark", "transit_entrance"]
                            },
                            "stylers": {
                                "visibility": "off"
                            }
                        }
                    ]
        """.trimIndent()
        binding.mapview.map.setMapStyle(styleOnlyHide)
        binding.mapview.map.isNightModeEnabled = true

        val groupRouteAdapter = GroupListAdapter(
            StringVal(R.string.all_groups_name),
            MapManager.instance.objectManager.groups.toMutableList()
        )

        val historicalObjectListAdapter = HistoricalObjectListAdapter(MapManager.instance.objectManager.listOfShown)

        // Filters
        val typeFilterChain = TypeFilterChain()
        val groupFilter1 = GroupFilterChain()

        typeFilterChain.addNext(groupFilter1)

        MapManager.instance.objectManager.filterChain = typeFilterChain
        // End filters

        bottomSheet = GroupsRoutesListBottomSheet(binding.bottomSheetMain).apply {
            peekHeight = 235
            maxHeight = 1500
            state = BottomSheetBehavior.STATE_COLLAPSED
            halfExpandedRatio = 0.5f

            historicalObjectListAdapter.actionOnClick = {
                state = BottomSheetBehavior.STATE_COLLAPSED
                binding.mapview.visibility = View.VISIBLE
            }

            setSpinner1(
                groupRouteAdapter,
                GroupListSpinnerSelectedListener(groupFilter1, historicalObjectListAdapter)
            )

            setRecycleViewList(historicalObjectListAdapter)

            setCallback(
                ListBottomSheetBehaviorCallback(
                binding.downContent, behavior
            )
            )
        }

        binding.typeSelection.addOnTabSelectedListener(
            TypeSelectionListener(typeFilterChain, historicalObjectListAdapter)
        )

        extraBottomSheet = ExtraBottomSheet(binding.bottomSheetOther)

        MapManager.instance.objectManager.updateShown() // TODO

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MapManager.instance.objectManager.zoomShown() // TODO
    }

    private fun setupMapManager() {
        MapManager.setupYandexManager(binding.mapview, this)

        MapManager.instance.createDefaultRoutes()
//        MapManager.instance.objectManager.selectAll()

        // -----------------------------
    }
}


