package com.example.tictactoe.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoe.R;
import com.example.tictactoe.viewmodels.ScoresAdapter;
import com.example.tictactoe.viewmodels.ScoresViewModel;

public class ScoresFragment extends Fragment {
    public ScoresFragment() {
        super(R.layout.scores_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.scores_fragment_recycler_view);
        ScoresViewModel viewModel = new ViewModelProvider(getActivity()).get(ScoresViewModel.class);
        ObservableArrayList scoresEntries = viewModel.getEntries();
        ScoresAdapter adapter = new ScoresAdapter(scoresEntries);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        super.onViewCreated(view, savedInstanceState);
    }
}
