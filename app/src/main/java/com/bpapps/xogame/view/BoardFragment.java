package com.bpapps.xogame.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bpapps.xogame.R;
import com.bpapps.xogame.utils.InjectorUtils;
import com.bpapps.xogame.viewmodel.GameViewModel;
import com.bpapps.xogame.viewmodel.GameViewModelFactory;

import java.util.ArrayList;

public class BoardFragment extends Fragment {
    private GameViewModel viewModel;

    private AppCompatEditText etMockDataToAdd;
    private AppCompatButton btnMockData;
    private AppCompatTextView tvResultShower;


    public BoardFragment() {
        // Required empty public constructor
    }

    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GameViewModelFactory factory = InjectorUtils.provideGameViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(GameViewModel.class);

        etMockDataToAdd = view.findViewById(R.id.etMockDataToAdd);
        tvResultShower = view.findViewById(R.id.tvResultShower);

        btnMockData = view.findViewById(R.id.btnMockData);
        btnMockData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mockDataToAdd = etMockDataToAdd.getText().toString();
                Toast.makeText(requireContext(), mockDataToAdd, Toast.LENGTH_SHORT).show();

                viewModel.addMockData(mockDataToAdd);

            }
        });

        viewModel.getMockData().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                StringBuilder mockData = new StringBuilder();

                strings.forEach(s ->
                {
                    mockData.append(s).append("\n\n");
                });

                tvResultShower.setText(mockData.toString());
            }
        });
    }
}