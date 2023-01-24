package sudoku;

import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;

public class SudokuTimer extends JPanel {

	Timer timer;	
	int second = 0, minute = 0;
	String ddSecond, ddMinute;	
	DecimalFormat dFormat = new DecimalFormat("00");
	JLabel counterLabel;

	public SudokuTimer() {
		this.setPreferredSize(new Dimension(100,25));
		
		counterLabel = new JLabel("");
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(new Font("Arial", Font.PLAIN, 20));

		counterLabel.setText("00:00");
		this.add(counterLabel);

		timer = new Timer(1000, e -> {

			second++;

			ddSecond = dFormat.format(second);
			ddMinute = dFormat.format(minute);
			counterLabel.setText(ddMinute + ":" + ddSecond);

			if(second==60) {
				second=0;
				minute++;

				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);
				counterLabel.setText(ddMinute + ":" + ddSecond);
			}
		});
	}

	public void startTimer() {
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
	}

	public void resetTimer() {
		second =0;
		minute =0;
		ddSecond = dFormat.format(second);
		ddMinute = dFormat.format(minute);
		counterLabel.setText(ddMinute + ":" + ddSecond);
		timer.restart();
	}

	public String getTimer() {
		return counterLabel.getText();
	}
}

