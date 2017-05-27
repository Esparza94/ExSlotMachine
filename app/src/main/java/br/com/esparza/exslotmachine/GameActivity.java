package br.com.esparza.exslotmachine;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    public static final Random RANDOM = new Random();

    private Player player;
    private Timer timer = new Timer();
    private Roda slot1, slot2, slot3;
    private ImageView imgvPlayerSexo, ivSlot1, ivSlot2, ivSlot3;
    private TextView lblPlayerName, lblNumFicha, lblTimer;
    private Button btnPlaySlotMachine;
    private int minutes, seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        player = (Player)getIntent().getSerializableExtra("player");

        startView();
        startTimer();
        loadPlayer();
    }

    public void btnPlaySlotMachineOnClick(View view){
        btnPlaySlotMachine.setEnabled(false);

        if (player.getNumFicha() == 0){
            Toast.makeText(this, "Fim de Jogo", Toast.LENGTH_SHORT).show();
            carregaMenuActivity();
        }

        player.subFicha();
        startSlotMachine();
    }

    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameActivity.this, PersonagemActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                GameActivity.this.finish();
            }
        }, 0);
    }

    private void carregaMenuActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                GameActivity.this.finish();
            }
        }, 0);
    }

    private void startSlotMachine() {
        rodarSlot1();
        rodarSlot2();
        rodarSlot3();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slot1.pararDeRodar();
                slot2.pararDeRodar();
                slot3.pararDeRodar();
                calculaResultado();
                btnPlaySlotMachine.setEnabled(true);
            }
        }, 3000);
    }

    private void calculaResultado() {
        if (slot1.indiceAtual == slot2.indiceAtual && slot2.indiceAtual == slot3.indiceAtual) {
            Toast.makeText(this, "Você ganhou", Toast.LENGTH_SHORT).show();
            player.addFicha(5);
        } else if (slot1.indiceAtual == slot2.indiceAtual || slot2.indiceAtual == slot3.indiceAtual
                || slot1.indiceAtual == slot3.indiceAtual) {
            Toast.makeText(this, "Pequena Premiação", Toast.LENGTH_SHORT).show();
            player.addFicha(2);
        } else {
            Toast.makeText(this, "Você perdeu", Toast.LENGTH_SHORT).show();
        }
    }

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    private void rodarSlot(Roda slot, final ImageView ivSlot, long duracaoDoQuadro, long iniciaEm) {
        slot = new Roda(new Roda.RodaListener() {
            @Override
            public void novaImagem(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(duracaoDoQuadro, iniciaEm));
        slot.start();
    }
    private void rodarSlot1() {
        slot1 = new Roda(new Roda.RodaListener() {
            @Override
            public void novaImagem(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot1.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(0, 200));
        slot1.start();
    }

    private void rodarSlot2() {
        slot2 = new Roda(new Roda.RodaListener() {
            @Override
            public void novaImagem(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot2.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(150, 400));
        slot2.start();
    }

    private void rodarSlot3() {
        slot3 = new Roda(new Roda.RodaListener() {
            @Override
            public void novaImagem(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot3.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(300, 600));
        slot3.start();
    }

    private void startTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);
    }
    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            seconds++;
            if (seconds == 60){
                minutes++;
                seconds = 0;
            }

            lblTimer.setText(String.format("%d:%02d", minutes, seconds));
            lblNumFicha.setText(Integer.toString(player.getNumFicha()));
        }
    };

    private void startView(){
        btnPlaySlotMachine = (Button) findViewById(R.id.btnPlaySlotMachine);
        imgvPlayerSexo = (ImageView) findViewById(R.id.imgvPlayerSexo);
        lblPlayerName = (TextView) findViewById(R.id.lblPlayerName);
        lblNumFicha = (TextView) findViewById(R.id.lblNumFicha);
        lblTimer = (TextView) findViewById(R.id.lblTimer);
        ivSlot1 = (ImageView) findViewById(R.id.ivSlot1);
        ivSlot2 = (ImageView) findViewById(R.id.ivSlot2);
        ivSlot3 = (ImageView) findViewById(R.id.ivSlot3);
    }

    private void loadPlayer(){
        lblPlayerName.setText(player.getName());
        lblNumFicha.setText(Integer.toString(player.getNumFicha()));

        if (player.isSexoM()){
            imgvPlayerSexo.setImageDrawable(
                    ContextCompat.getDrawable(
                            GameActivity.this,
                            R.drawable.sonic
                    )
            );
        }else{
            imgvPlayerSexo.setImageDrawable(
                    ContextCompat.getDrawable(
                            GameActivity.this,
                            R.drawable.amy_rose
                    )
            );
        }
    }
}
