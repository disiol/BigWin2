package com.mdz.bigwin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdz.bigwin.ImageViewScrolling.IEventEnd;
import com.mdz.bigwin.ImageViewScrolling.ImageViewScrolling;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    ImageView btn_up, btn_down;
    ImageViewScrolling image, image2, image3;
    TextView txt_score;
    int count_done = 0;
    int money = 1000;
    int beat;
    private Button bet50Button, bet100Button,bet200Button, bet1000Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);

        bet50Button = findViewById(R.id.bet_50_button);
        bet100Button = findViewById(R.id.bet_100_button);
        bet200Button = findViewById(R.id.bet_200_button);
        bet1000Button = findViewById(R.id.bet_1000_button);

        image = findViewById(R.id.image);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        txt_score = findViewById(R.id.txt_score);

        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        txt_score.setText(String.valueOf(money));

        btn_up.setOnClickListener(v -> sratSlot(50));

        bet50Button.setOnClickListener(v -> {
            sratSlot(50);
        });
        bet100Button.setOnClickListener(v -> {
            sratSlot(100);
        });
        bet200Button.setOnClickListener(v -> {
            sratSlot(200);
        });
        bet1000Button.setOnClickListener(v -> {
            sratSlot(1000);
        });

    }

    private void sratSlot(int beat) {
        if (money >= 50) {
            btn_up.setVisibility(View.GONE);
            btn_down.setVisibility(View.VISIBLE);

            image.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);
            image2.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);
            image3.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);

            money -= beat;
            txt_score.setText(String.valueOf(money));
        } else {
            Toast.makeText(MainActivity.this, "You have not enough money. You can restart the game.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void eventEnd(int result, int count) {
        if (count_done < 2) {
            count_done++;
        } else {
            btn_down.setVisibility(View.GONE);
            btn_up.setVisibility(View.VISIBLE);

            count_done = 0;
            if (image.getValue() == image2.getValue() &&
                    image2.getValue() == image3.getValue()) {
                Toast.makeText(this, "You won 300!", Toast.LENGTH_SHORT).show();
                money += 300;
                txt_score.setText(String.valueOf(money));
            } else if (image.getValue() == image2.getValue() ||
                    image2.getValue() == image3.getValue() ||
                    image.getValue() == image3.getValue()) {
                Toast.makeText(this, "You won 100!", Toast.LENGTH_SHORT).show();
                money += 100;
                txt_score.setText(String.valueOf(money));
            } else {
                Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
