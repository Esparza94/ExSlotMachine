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
import android.widget.RadioButton;
import android.widget.TextView;

public class PersonagemActivity extends AppCompatActivity {

    private ImageView imgvSexo;
    private RadioButton rbtnSexoM, rbtnSexoF;
    private EditText txtPlayerName, txtNumFicha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagem);

        imgvSexo = (ImageView) findViewById(R.id.imgvSexo);
        rbtnSexoM = (RadioButton) findViewById(R.id.rbtnSexoM);
        rbtnSexoF = (RadioButton) findViewById(R.id.rbtnSexoF);
        txtPlayerName = (EditText) findViewById(R.id.txtPlayerName);
        txtNumFicha = (EditText) findViewById(R.id.txtNumFicha);
    }

    public void rbtnSexoOnClick(View view){

        boolean checked = ((RadioButton) view).isChecked();

        if (checked) {
            switch (view.getId()) {
                case R.id.rbtnSexoM:
                    imgvSexo.setImageDrawable(
                            ContextCompat.getDrawable(
                                    PersonagemActivity.this,
                                    R.drawable.sonic_select
                            )
                    );
                    rbtnSexoF.setChecked(false);
                    break;
                case R.id.rbtnSexoF:
                    imgvSexo.setImageDrawable(
                            ContextCompat.getDrawable(
                                    PersonagemActivity.this,
                                    R.drawable.amy_rose_select
                            )
                    );
                    rbtnSexoM.setChecked(false);
                    break;
            }
        }
    }

    public void btnSavePlayerOnClick(View view){
        final Player player = new Player(
                txtPlayerName.getText().toString(),
                Integer.parseInt(txtNumFicha.getText().toString()),
                rbtnSexoM.isChecked()
        );
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PersonagemActivity.this, GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("player", player);
                startActivity(intent);
                PersonagemActivity.this.finish();
            }
        }, 0);
    }

    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PersonagemActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                PersonagemActivity.this.finish();
            }
        }, 0);
    }
}
