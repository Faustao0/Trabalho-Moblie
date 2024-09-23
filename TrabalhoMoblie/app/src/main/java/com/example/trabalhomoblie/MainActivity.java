package com.example.trabalhomoblie;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etIdade, etDisciplina, etNota1, etNota2;
    private TextView tvErro, tvResumo;
    private Button btnEnviar, btnLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.et_nome);
        etEmail = findViewById(R.id.et_email);
        etIdade = findViewById(R.id.et_idade);
        etDisciplina = findViewById(R.id.et_disciplina);
        etNota1 = findViewById(R.id.et_nota1);
        etNota2 = findViewById(R.id.et_nota2);
        tvErro = findViewById(R.id.tv_erro);
        tvResumo = findViewById(R.id.tv_resumo);
        btnEnviar = findViewById(R.id.btn_enviar);
        btnLimpar = findViewById(R.id.btn_limpar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarFormulario();
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparFormulario();
            }
        });
    }

    private void validarFormulario() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String idadeStr = etIdade.getText().toString().trim();
        String disciplina = etDisciplina.getText().toString().trim();
        String nota1Str = etNota1.getText().toString().trim();
        String nota2Str = etNota2.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(idadeStr) ||
                TextUtils.isEmpty(disciplina) || TextUtils.isEmpty(nota1Str) || TextUtils.isEmpty(nota2Str)) {
            tvErro.setText("Todos os campos são obrigatórios.");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tvErro.setText("Email inválido.");
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade <= 0) {
                tvErro.setText("A idade deve ser um número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            tvErro.setText("A idade deve ser um número válido.");
            return;
        }

        float nota1, nota2;
        try {
            nota1 = Float.parseFloat(nota1Str);
            nota2 = Float.parseFloat(nota2Str);

            if (nota1 < 0 || nota1 > 10 || nota2 < 0 || nota2 > 10) {
                tvErro.setText("As notas devem estar entre 0 e 10.");
                return;
            }
        } catch (NumberFormatException e) {
            tvErro.setText("As notas devem ser números válidos.");
            return;
        }

        tvErro.setText("");
        float media = (nota1 + nota2) / 2;
        String status = media >= 6 ? "Aprovado" : "Reprovado";

        String resumoTexto = String.format(
                "Nome: %s\nEmail: %s\nIdade: %d\nDisciplina: %s\nNotas: %.1f e %.1f\nMédia: %.1f\nStatus: ",
                nome, email, idade, disciplina, nota1, nota2, media);

        SpannableString spannableResumo = new SpannableString(resumoTexto + status);

        int startIndex = resumoTexto.length();
        int endIndex = resumoTexto.length() + status.length();
        spannableResumo.setSpan(new ForegroundColorSpan(status.equals("Aprovado") ? Color.GREEN : Color.RED),
                startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableResumo.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvResumo.setText(spannableResumo);
    }

    private void limparFormulario() {
        etNome.setText("");
        etEmail.setText("");
        etIdade.setText("");
        etDisciplina.setText("");
        etNota1.setText("");
        etNota2.setText("");
        tvErro.setText("");
        tvResumo.setText("");
    }
}