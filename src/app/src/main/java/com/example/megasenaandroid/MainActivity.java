package com.example.megasenaandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText quantityGamesInput;
    private EditText numbersPerGame;
    private LinearLayout gamesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        quantityGamesInput = findViewById(R.id.inputQuantityGames);
        numbersPerGame = findViewById(R.id.inputNumbersPerGame);
        gamesLayout = findViewById(R.id.layoutGames);

        Button generateGamesButton = findViewById(R.id.generateButton);
        generateGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateGames();
            }
        });
    }

    private void generateGames() {
        // Limpa todos os jogos anteriores
        gamesLayout.removeAllViews();

        String quantityGamesStr = quantityGamesInput.getText().toString();
        String numbersPerGameStr = numbersPerGame.getText().toString();

        if(quantityGamesStr.isEmpty() || numbersPerGameStr.isEmpty()) {
            Toast.makeText(this, "Por favor, insira a quantidade de jogos e números por jogos", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantityGamesInt = Integer.parseInt(quantityGamesStr);
        int numbersPerGameInt = Integer.parseInt(numbersPerGameStr);

        // Criando um ArrayList de ArrayLists de inteiros
        ArrayList<ArrayList<Integer>> generatedGames = new ArrayList<>();

        // Criando uma aleatoridade
        Random random = new Random();

        for(int i = 0; i < quantityGamesInt; i++) {
            // Criando um ArrayList com os números sorteados
            ArrayList<Integer> numbersArrayList = new ArrayList<>();

            for (int j = 0; j < numbersPerGameInt; j++) {
                // Sorteando números de 1 a 60
                int number = random.nextInt(60) + 1;
                while (numbersArrayList.contains(number)) {
                    // Evitando números repetidos
                    number = random.nextInt(60) + 1;
                }
                // Adicionando para o ArrayList de números sorteados
                numbersArrayList.add(number);
            }
            // Ordenando os números
            Collections.sort(numbersArrayList);

            // Salvando os ArrayList's dos números sorteados
            generatedGames.add(numbersArrayList);

            double prize = calcGamePrize(numbersArrayList.size());
            Toast.makeText(this, "Valor a pagar por jogo: " + (i+1) + " : R$: " + prize, Toast.LENGTH_SHORT).show();
        }

        // Mostrando os jogos em tela
        for(int i = 0; i < generatedGames.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText("Jogo " + (i + 1) + ": " + generatedGames.get(i).toString());
            gamesLayout.addView(textView);
        }
    }

    private double calcGamePrize(int numbers) {
        // Calculando o preço por jogo
        return numbers * 0.5;
    }
}