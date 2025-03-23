package com.example.tictactoe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tictactoe.GameActivity;
import com.example.tictactoe.R;

public class MainScreenFragment extends Fragment {
    public MainScreenFragment() {
        super(R.layout.main_screen_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        EditText player1Name = view.findViewById(R.id.Player1Name);
        EditText player2Name = view.findViewById(R.id.Player2Name);
        String name1 = player1Name.getText().toString();
        String name2 = player2Name.getText().toString();

        Button ComputerButton = view.findViewById(R.id.computerButton);
        ComputerButton.setOnClickListener((v) -> {
            Intent gameIntent = new Intent(requireContext(), GameActivity.class);
            gameIntent.putExtra("gameMode", "AI");
            gameIntent.putExtra("player1Name", name1);
            gameIntent.putExtra("player2Name", name2);
            startActivity(gameIntent);
        });

        Button pvpButton = view.findViewById(R.id.PVPButton);
        pvpButton.setOnClickListener((v) -> {
            Intent gameIntent = new Intent(requireContext(), GameActivity.class);
            gameIntent.putExtra("gameMode", "pvp");
            gameIntent.putExtra("player1Name", name1);
            gameIntent.putExtra("player2Name", name2);
            startActivity(gameIntent);
        });

        Button statsButton = view.findViewById(R.id.StatsButton);
        statsButton.setOnClickListener((v) -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.main_fragment_container, ScoresFragment.class, null)
                    .commit();
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
