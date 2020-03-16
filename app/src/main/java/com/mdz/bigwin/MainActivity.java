package com.mdz.bigwin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdz.bigwin.ImageViewScrolling.IEventEnd;
import com.mdz.bigwin.ImageViewScrolling.ImageViewScrolling;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    public static final int START_MONEY = 1000;
    ImageViewScrolling image, image2, image3;
    TextView txt_score;
    int count_done = 0;
    int money;
    private Button bet50Button, bet100Button, bet200Button, bet1000Button;
    private int minBet = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        money = START_MONEY;


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


        bet50Button.setOnClickListener(v -> {
            sratSlot(50, true);
        });
        bet100Button.setOnClickListener(v -> {
            sratSlot(100, true);
        });
        bet200Button.setOnClickListener(v -> {
            sratSlot(200, true);
        });
        bet1000Button.setOnClickListener(v -> {
            sratSlot(1000, true);
        });

    }

    private void sratSlot(int beat, boolean flag) {
        if (money >= minBet && flag) {


            image.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);
            image2.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);
            image3.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);

            money -= beat;
            txt_score.setText(String.valueOf(money));
        } else if (money < minBet) {
            showMessageEndGame("You have not enough money. You can restart the game.");
        }
    }

    @Override
    public void eventEnd(int result, int count) {
        if (count_done < 2) {
            count_done++;
        } else {


            count_done = 0;
            if (image.getValue() == image2.getValue() &&
                    image2.getValue() == image3.getValue()) {
                showMessage("You won 300!");
                money += 300;
                txt_score.setText(String.valueOf(money));
            } else if (image.getValue() == image2.getValue() ||
                    image2.getValue() == image3.getValue() ||
                    image.getValue() == image3.getValue()) {
                showMessage("You won 100!");
                money += 100;
                txt_score.setText(String.valueOf(money));
            } else {
                showMessage("You lose");

            }
        }
    }

    public void showMessageEndGame(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false).setPositiveButton(R.string.new_game, (dialog, id) -> {
            NewGame();
            dialog.cancel();
        })
                .setNegativeButton(R.string.exit,
                        (dialog, id) -> {
                          finish();
                            dialog.cancel();
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showMessage(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false).setPositiveButton("Ok", (dialog, id) -> {
            sratSlot(money,false);
            dialog.cancel();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void NewGame() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
