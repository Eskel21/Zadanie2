package pl.edu.pb.wi.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

	private static final String KEY_CURRENT_INDEX = "currentIndex";
	public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correct_answer";

	private static final int REQUEST_CODE_PROMPT = 0;
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d("MainActivity","Wywołanie onSaveInstanceState");
		outState.putInt(KEY_CURRENT_INDEX, currentIndex);
	}
	private Button prawdaPrzycisk;
	private Button falszPrzycisk;
	private Button nastepnyPrzycisk;

	private Button restartPrzycisk;
	private TextView pytanieTextView;

	private boolean answerWasShown=false;


	private Button podpowiedz;
	private Question[] pytania = new Question[]{new Question(R.string.q_filary_ziemi, true), new Question(R.string.q_sherlock_holmes, false), new Question(R.string.q_lotr, false), new Question(R.string.q_dewajtis, true), new Question(R.string.q_Hobbit, false), new Question(R.string.q_Harry_Potter, true), new Question(R.string.q_trylogia_husycka, true), new Question(R.string.q_hercules_poirot, true), new Question(R.string.q_cien_i_kosc, false), new Question(R.string.q_metro, false)


	};
	private int currentIndex = 0;
	private int correctAnswers = 0;
	private int questionsAnswered = 0;

	private void showResult() {
		String wiadomosc = "Udzielono " + correctAnswers + " poprawnych odpowiedzi, na " + pytania.length + " pytań";
		Toast.makeText(this, wiadomosc, Toast.LENGTH_LONG).show();
		prawdaPrzycisk.setEnabled(false);
		falszPrzycisk.setEnabled(false);
		nastepnyPrzycisk.setEnabled(false);
		podpowiedz.setEnabled(false);
		restartPrzycisk.setVisibility(View.VISIBLE);
	}

	private void checkAnswerCorrectness(boolean odpowiedzUzytkownika) {
		boolean poprawnaOdpowiedz = pytania[currentIndex].isTrueAnswer();
		int resultMessageId = 0;
		if(answerWasShown){
			resultMessageId = R.string.answer_was_shown;
		}
		else{
			if (odpowiedzUzytkownika == poprawnaOdpowiedz) {
				resultMessageId = R.string.correct_answer;
				correctAnswers++;
			} else {
				resultMessageId = R.string.incorrect_answer;
			}
		}
		Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
		questionsAnswered++;
		prawdaPrzycisk.setEnabled(false);
		falszPrzycisk.setEnabled(false);
		podpowiedz.setEnabled(false);
		if (questionsAnswered == pytania.length) {
			showResult();
		}
	}

	private void setNextQuestion() {
		pytanieTextView.setText(pytania[currentIndex].getQuestionId());

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prawdaPrzycisk = findViewById(R.id.prawda_przycisk);
		falszPrzycisk = findViewById(R.id.falsz_przycisk);
		nastepnyPrzycisk = findViewById(R.id.następny_przycisk);
		pytanieTextView = findViewById(R.id.pole_tekstowe_na_pytanie);
		podpowiedz = findViewById(R.id.podpowiedz_przycisk);
		restartPrzycisk = findViewById(R.id.restart_przycisk);
		if (savedInstanceState != null){
			currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
		}
		pytanieTextView.setText(pytania[currentIndex].getQuestionId());

		prawdaPrzycisk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view1) {
				checkAnswerCorrectness(true);
			}
		});
		falszPrzycisk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view2) {
				checkAnswerCorrectness(false);
			}
		});
		podpowiedz.setOnClickListener((v)->{

				Intent intent = new Intent(MainActivity.this, PromptActivity.class);
				boolean correctAnswer = pytania[currentIndex].isTrueAnswer();
				intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
				startActivityForResult(intent, REQUEST_CODE_PROMPT);

		});
		nastepnyPrzycisk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view3) {
				currentIndex = (currentIndex + 1) % pytania.length;
				prawdaPrzycisk.setEnabled(true);
				falszPrzycisk.setEnabled(true);
				podpowiedz.setEnabled(true);
				answerWasShown=false;
				setNextQuestion();

			}
		});
		restartPrzycisk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				currentIndex = 0;
				correctAnswers = 0;
				questionsAnswered = 0;


				prawdaPrzycisk.setEnabled(true);
				falszPrzycisk.setEnabled(true);
				nastepnyPrzycisk.setEnabled(true);


				pytanieTextView.setText(pytania[currentIndex].getQuestionId());
				restartPrzycisk.setVisibility(View.GONE);
			}
		});
				Log.d("MainActivity", "Wywołanie onCreate");
	}

			@Override
			protected void onStart() {
				super.onStart();
				Log.d("MainActivity", "Wywołanie onStart");
			}
			@Override
			protected void onResume() {
				super.onResume();
				Log.d("MainActivity", "Wywołanie onResume");
	}
			@Override
			protected void onPause() {
				super.onPause();
				Log.d("MainActivity", "Wywołanie onPause");
	}
			@Override
			protected void onStop() {
				super.onStop();
				Log.d("MainActivity", "Wywołanie onStop");
	}
			@Override
			protected void onDestroy() {
				super.onDestroy();
				Log.d("MainActivity", "Wywołanie onDestroy");

	}
			@Override
			protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
				super.onActivityResult(requestCode,resultCode,data);
				if(resultCode != RESULT_OK) {
					return;
				}
				if(requestCode == REQUEST_CODE_PROMPT){
					if (data == null) {return;}
					answerWasShown= data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
				}
			}
}