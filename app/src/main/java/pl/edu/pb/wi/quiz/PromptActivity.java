package pl.edu.pb.wi.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class PromptActivity extends AppCompatActivity {

	public static final String KEY_EXTRA_ANSWER_SHOWN = "pl.edu.pb.wi.quiz.answerShown";
	private boolean correctAnswer;
	private Button pokaz_podpowiedz_przycisk;

	private Button powrot;

	private TextView answerTextView;

	private void setAnswerShownResult(boolean answerWasShown){
		Intent resultIntent = new Intent();
		resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
		setResult(RESULT_OK, resultIntent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prompt);
		pokaz_podpowiedz_przycisk = findViewById(R.id.przycisk_odpowiedz);
		answerTextView = findViewById(R.id.answer_text_view);
		powrot = findViewById(R.id.przycisk_powrot);
		correctAnswer=getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);


	pokaz_podpowiedz_przycisk.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick(View v) {
			int answer = correctAnswer ? R.string.przycisk_prawda : R.string.przycisk_falsz;
			answerTextView.setText(answer);
			setAnswerShownResult(true);

		}
	});
		powrot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

	}
}