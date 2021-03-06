package com.musicarray.codeclan.blackjack;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultPokerActivity extends AppCompatActivity {

    ImageView computerCard1;
    ImageView computerCard2;
    ImageView computerCard3;
    ImageView computerCard4;
    ImageView computerCard5;
    ImageView playerCard1;
    ImageView playerCard2;
    ImageView playerCard3;
    ImageView playerCard4;
    ImageView playerCard5;
    TextView playerName;
    TextView pokerResult;
    TextView computerName;
    GameMaster gameMaster;
    Button playAgainButton;
    Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_poker);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "PlayfairDisplay-Regular.otf");
        computerCard1 = findViewById(R.id.computer_card_1);
        computerCard2 = findViewById(R.id.computer_card_2);
        computerCard3 = findViewById(R.id.computer_card_3);
        computerCard4 = findViewById(R.id.computer_card_4);
        computerCard5 = findViewById(R.id.computer_card_5);
        playerCard1 = findViewById(R.id.player_card_1_result);
        playerCard2 = findViewById(R.id.player_card_2_result);
        playerCard3 = findViewById(R.id.player_card_3_result);
        playerCard4 = findViewById(R.id.player_card_4_result);
        playerCard5 = findViewById(R.id.player_card_5_result);
        playerName = findViewById(R.id.player_poker_name_result);
        computerName = findViewById(R.id.computer_poker_name);
        pokerResult = findViewById(R.id.poker_result_screen);
        playAgainButton = findViewById(R.id.poker_play_again);
        playAgainButton.setTypeface(typeface);
        finishButton = findViewById(R.id.finish_button);
        finishButton.setTypeface(typeface);
        Intent intent = getIntent();
        gameMaster = (GameMaster) intent.getSerializableExtra("gameMaster");
        if (gameMaster.gameOver() == true){
            playAgainButton.setText(R.string.start_screen_text);
        }
        computerCard1.setImageResource(getResources().getIdentifier(gameMaster.getComputer().getHand().getCardsHeld().get(0).getCardPicture(), "drawable", getPackageName()));
        computerCard2.setImageResource(getResources().getIdentifier(gameMaster.getComputer().getHand().getCardsHeld().get(1).getCardPicture(), "drawable", getPackageName()));
        if (gameMaster.getComputer().getHand().getCardsHeld().size() > 2) {
            computerCard3.setImageResource(getResources().getIdentifier(gameMaster.getComputer().getHand().getCardsHeld().get(2).getCardPicture(),"drawable",getPackageName()));
        }
        if (gameMaster.getComputer().getHand().getCardsHeld().size() > 3) {
            computerCard4.setImageResource(getResources().getIdentifier(gameMaster.getComputer().getHand().getCardsHeld().get(3).getCardPicture(),"drawable",getPackageName()));
        }
        if (gameMaster.getComputer().getHand().getCardsHeld().size() > 4) {
            computerCard5.setImageResource(getResources().getIdentifier(gameMaster.getComputer().getHand().getCardsHeld().get(4).getCardPicture(),"drawable",getPackageName()));
        }
        playerCard1.setImageResource(getResources().getIdentifier(gameMaster.getPlayer().getHand().getCardsHeld().get(0).getCardPicture(), "drawable", getPackageName()));
        playerCard2.setImageResource(getResources().getIdentifier(gameMaster.getPlayer().getHand().getCardsHeld().get(1).getCardPicture(), "drawable", getPackageName()));
        if (gameMaster.getPlayer().getHand().getCardsHeld().size() > 2) {
            playerCard3.setImageResource(getResources().getIdentifier(gameMaster.getPlayer().getHand().getCardsHeld().get(2).getCardPicture(),"drawable",getPackageName()));
        }
        if (gameMaster.getComputer().getHand().getCardsHeld().size() > 3) {
            playerCard4.setImageResource(getResources().getIdentifier(gameMaster.getPlayer().getHand().getCardsHeld().get(3).getCardPicture(),"drawable",getPackageName()));
        }
        if (gameMaster.getComputer().getHand().getCardsHeld().size() > 4) {
            playerCard5.setImageResource(getResources().getIdentifier(gameMaster.getPlayer().getHand().getCardsHeld().get(4).getCardPicture(),"drawable",getPackageName()));
        }
        playerName.setText(gameMaster.getPlayer().getName());
        playerName.setTypeface(typeface);
        pokerResult.setText(gameMaster.getGameStatus());
        pokerResult.setTypeface(typeface);
        computerName.setTypeface(typeface);
    }

    public void onPokerPlayAgainButtonClicked(View button){
        gameMaster.getPlayer().getHand().clearHand();
        gameMaster.getPlayer().getHand().setHighestPokerCard(0);
        if (gameMaster.gameOver() == false) {
            gameMaster.getPlayer().setWinner(false);
            gameMaster.getComputer().setWinner(false);
            Intent intent = new Intent(this,GamePokerActivity.class);
            intent.putExtra("player",gameMaster.getPlayer());
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);
        }
    }

    public void onFinishButtonClicked(View button){
        Intent intent = new Intent(this, PokerGameOverActivity.class);
        intent.putExtra("player",gameMaster.getPlayer());
        startActivity(intent);
    }
}
