package sudoku;

import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;

public class SudokuTimer extends JPanel {

	Timer timer;	
	int second, minute;
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

		second =0;
		minute =0;
		normalTimer();
		timer.start();
	}

	public void normalTimer() {
		
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

	public void stopTimer() {
		timer.stop();
	}

	public void resetTimer() {
		timer.stop();
		second =0;
		minute =0;
		ddSecond = dFormat.format(second);
		ddMinute = dFormat.format(minute);
		counterLabel.setText(ddMinute + ":" + ddSecond);
	}
}

