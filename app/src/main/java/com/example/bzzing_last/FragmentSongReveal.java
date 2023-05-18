package com.example.bzzing_last;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSongReveal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSongReveal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSongReveal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSongReveal.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSongReveal newInstance(String param1, String param2) {
        FragmentSongReveal fragment = new FragmentSongReveal();
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
        return inflater.inflate(R.layout.fragment_song_reveal, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GameRoom gameRoom = AppUtilities.gameRoom;

        int index = gameRoom.getCurrentSongIndex();

        ImageView imageView = getView().findViewById(R.id.image);
        int id = getResources().getIdentifier(gameRoom.getSongs().get(index).getImage(), "drawable", getContext().getPackageName());
        imageView.setImageResource(id);

        TextView name = getView().findViewById(R.id.songName);
        name.setText(gameRoom.getSongs().get(index).getName());

        TextView singer = getView().findViewById(R.id.singerName);
        singer.setText(gameRoom.getSongs().get(index).getSinger());



        TextView textView = getView().findViewById(R.id.who);
        textView.setText(gameRoom.getActivePlayer());
        randomSentence();
    }

    private void randomSentence() {
        GameRoom gameRoom = AppUtilities.gameRoom;
        TextView textView = getView().findViewById(R.id.sentence);
        textView.setText(gameRoom.getPercentSentences().get(gameRoom.getRounds()));
    }
}