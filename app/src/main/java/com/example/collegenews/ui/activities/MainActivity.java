package com.example.collegenews.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.collegenews.R;
import com.example.collegenews.databinding.ActivityMainBinding;
import com.example.collegenews.ui.fragments.ExploreFragment;
import com.example.collegenews.ui.fragments.HomeFragment;
import com.example.collegenews.ui.fragments.SettingFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) { // setting default as Home Frag..
            binding.bottomNav.setSelectedItemId(R.id.home);
            getSupportFragmentManager().beginTransaction().replace(binding.FragmentContainer.getId(), new HomeFragment()).commit();

        }





        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:

                        changeFrag(new HomeFragment());
                        break;

                    case R.id.explore:
                        changeFrag(new ExploreFragment());
                        break;

                    case R.id.setting:
                        changeFrag(new SettingFragment());
                        break;


                    default:
                        Toast.makeText(MainActivity.this, "Invalid Options", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("news");
//        NewsModel news1 = new NewsModel();
//        news1.setCategory("academics");
//        news1.setTitle("Mid Semester exam");
//        news1.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged");
//        news1.setAuthor("Test Author");
//        news1.setUploadedat("21-03-23, 08:27 PM");
//
//        NewsModel news2 = new NewsModel();
//        news2.setCategory("placements");
//        news2.setTitle("Microsoft");
//        news2.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged");
//        news2.setAuthor("Test Author");
//        news2.setUploadedat("21-03-23, 08:27 PM");
//
//
//        List<NewsModel> l = new ArrayList<>();
//        l.add(news1);
//        l.add(news2);
//        // to write the values
//        myRef.setValue(l);
//        // to read the values
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//
//                List msg = (List) snapshot.getValue();
//                Toast.makeText(MainActivity.this, "this was the msg " + msg.toString(), Toast.LENGTH_LONG).show();
//
//                NewsModel n1 = (NewsModel) msg.get(0);
//                String title = n1.getTitle();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    void changeFrag(Fragment fragment) {
        FragmentTransaction fragmentTransaction0 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction0.replace(binding.FragmentContainer.getId(), fragment, "");
        fragmentTransaction0.commit();

    }
}