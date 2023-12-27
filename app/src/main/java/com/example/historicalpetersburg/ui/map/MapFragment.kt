package com.example.historicalpetersburg.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.FragmentMapBinding
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.filters.CompleteFilterChain
import com.example.historicalpetersburg.map.main.filters.GroupFilterChain
import com.example.historicalpetersburg.map.main.filters.TypeFilterChain
import com.example.historicalpetersburg.map.main.views.adapters.HistoricalObjectListAdapter
import com.example.historicalpetersburg.map.main.views.behaviors.ListBottomSheetBehaviorCallback
import com.example.historicalpetersburg.map.main.views.bottomsheet.HistoricalObjectListBottomSheet
import com.example.historicalpetersburg.map.main.views.listeners.GroupListSpinnerSelectedListener
import com.example.historicalpetersburg.map.main.views.listeners.TypeSelectionListener
import com.example.historicalpetersburg.tools.GlobalTools
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.Timer
import java.util.TimerTask


class MapFragment : Fragment() {
    private var savedView: View? = null

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var timer: Timer? = null

    lateinit var bottomSheet: HistoricalObjectListBottomSheet

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        println(savedView)

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

        val historicalObjectListAdapter = HistoricalObjectListAdapter(MapManager.instance.objectManager.listOfShown)

        // Filters
        val typeFilterChain = TypeFilterChain()

        val groupFilter1 = GroupFilterChain()
        val groupFilter2 = GroupFilterChain()

        val completedFilter = CompleteFilterChain(false)

        typeFilterChain.addNext(groupFilter1)
        typeFilterChain.addNext(groupFilter2)
        typeFilterChain.addNext(completedFilter)

        MapManager.instance.objectManager.filterChain = typeFilterChain
        val unions = MapManager.instance.objectManager.groupRepository.getUnions(2)
        // End filters

        bottomSheet = HistoricalObjectListBottomSheet(binding.bottomSheetMain).apply {
            peekHeight = 250

            state = BottomSheetBehavior.STATE_COLLAPSED
            halfExpandedRatio = 0.4f
            behavior.isFitToContents = false
            behavior.expandedOffset = GlobalTools.instance.getStatusBarHeight() + 5

            historicalObjectListAdapter.actionOnClick = {
//                state = BottomSheetBehavior.STATE_COLLAPSED
                binding.mapview.visibility = View.VISIBLE
            }

            setSpinner1(
                unions[0],
                GroupListSpinnerSelectedListener(groupFilter1, historicalObjectListAdapter)
            )

            setSpinner2(
                unions[1],
                GroupListSpinnerSelectedListener(groupFilter2, historicalObjectListAdapter)
            )

            setRecycleViewList(historicalObjectListAdapter)

            setCallback(
                ListBottomSheetBehaviorCallback(
                binding.downContent, requireActivity().window, behavior)
            )
        }

        binding.typeSelection.addOnTabSelectedListener(
            TypeSelectionListener(typeFilterChain, historicalObjectListAdapter)
        )

        binding.zoomInButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> startZooming(0.3f)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> stopZooming()
            }
            true
        }

        binding.zoomOutButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> startZooming(-0.3f)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> stopZooming()
            }
            true
        }

        binding.zoomLocationButton.setOnClickListener {
            MapManager.instance.locationManager.let {
                it.zoomInPosition()
                it.follow = true
            }
        }

        MapManager.instance.map.addCameraPositionChangedListener {
            MapManager.instance.locationManager.follow = false
        }

        MapManager.instance.map.apply {
            zoomPadding.right = (binding.zoomInButton.layoutParams.width + binding.mapToolsWindow.marginEnd).toFloat()
            zoomPadding.top = 100f
            zoomPadding.bottom = 450f // TODO
        }

        MapManager.instance.locationManager.actionsToFollowChange.add {
            binding.zoomLocationButton.background = if (it) {
                ContextCompat.getDrawable(requireContext(), R.drawable.background_on_secondary_round_all_30_inset_10)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.background_on_primary_round_all_30_inset_10)
            }
        }

        binding.notCompletedCheck.setOnCheckedChangeListener { _, isChecked ->
            completedFilter.active = isChecked
            MapManager.instance.objectManager.let {
                it.updateShown()
                it.zoomShown()
            }
        }

        MapManager.instance.objectManager.zoomShown()

        return root
    }

    override fun onStart() {
        super.onStart()

        MapManager.instance.locationManager.startUpdate()
        if (!MapManager.instance.locationManager.zoomInPosition()) {
            MapManager.instance.objectManager.zoomShown()
        }
    }

    override fun onStop() {
        super.onStop()

        MapManager.instance.locationManager.stopUpdate()
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroy() {
        super.onDestroy()
        MapManager.instance.routeInspector.stop()
    }

    private fun setupMapManager() {
        MapManager.setupYandexManager(binding.mapview, this)

        MapManager.instance.createDefaultRoutes()
        MapManager.instance.objectManager.updateShown()
    }

    private fun startZooming(step: Float) {
        val mainHandler = Handler(Looper.getMainLooper())
        timer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    mainHandler.post {
                        MapManager.instance.map.zoom(step, 0.1f)
                    }
                }
            }, 0, 100)
        }
    }

    private fun stopZooming() {
        println(MapManager.instance.map.camera.zoom)
        timer?.cancel()
        timer = null
    }
}


