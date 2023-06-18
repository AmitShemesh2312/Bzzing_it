package com.example.bzzing_last;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNextPlayer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNextPlayer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentNextPlayer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNextPlayer.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNextPlayer newInstance(String param1, String param2) {
        FragmentNextPlayer fragment = new FragmentNextPlayer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GameRoom gameRoom = AppUtilities.gameRoom;

        TextView rounds = getView().findViewById(R.id.rounds);
        rounds.setText((gameRoom.getRounds() + 1) + " / " + gameRoom.getPlayers().size());

        for (int i = 0; i < gameRoom.getMaxPlayers(); i++) {
            int id = getResources().getIdentifier( "nextPlayer_Player" + i, "id", getContext().getPackageName());
            TextView textView = getView().findViewById(id);
            textView.setText("");
        }
//
//        for (int i = 0; i < gameRoom.getPlayers().size(); i++) {
//            int id = getResources().getIdentifier( "nextPlayer_Player" + i, "id", getContext().getPackageName());
//            TextView textView = getView().findViewById(id);
//            textView.setText(gameRoom.getPlayers().get(i).getName());
//        }
    }
}