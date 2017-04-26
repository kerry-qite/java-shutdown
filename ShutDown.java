import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 定时关机、取消定时关机计划<br>
 * <strong>Warning:</strong><p>由于没有读取系统计划任务，
 * 所以每次打开软件均显示软件默认值，<br/>而非系统定时关机默认值</p>
 * @author kerry
 * @date 2017年3月22日
 * @version V0.1
 */
public class ShutDown {

	private JFrame frame;

	private int hour_shutdown;
	private int minute_shutdown;
	
	public int getMinute_shutdown() {
		return minute_shutdown;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					T1 window = new T1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public T1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("定时关机");
		frame.setBounds(500, 200, 300, 270);
		frame.setSize(300, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//整个面板将会分成四部分，采用流式布局。1、提示文字；2、时间选项；3、操作按钮；4、警告信息
		frame.getContentPane().setLayout(new FlowLayout());
		
		//警告信息
		JLabel lblMsg = new JLabel();
		lblMsg.setForeground(Color.RED);
		
		//1、提示文字部分
		JLabel label = new JLabel();
		label.setText("关机时间");
		//label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		frame.getContentPane().add(label);
		
		//2、时间选项部分
		JPanel panel_time = new JPanel();
		panel_time.setPreferredSize(new Dimension(280,40));
		//panel_time.setBorder(BorderFactory.createLineBorder(Color.red));
		
		Integer labels_hour[] = { 0, 1, 2, 3, 4, 5, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
				21, 22, 23};
		JComboBox comboBox_hour = new JComboBox(labels_hour);
		comboBox_hour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hour_shutdown = (Integer)((JComboBox)e.getSource()).getSelectedItem();
			}
		});
		comboBox_hour.setBounds(10, 20, 40, 40);
		comboBox_hour.setSize(30, 30);
		
		
	    panel_time.add(comboBox_hour);
	    JLabel label_hour = new JLabel();
	    label_hour.setText("时");
	    panel_time.add(label_hour);
	    
	    Integer labels_minute[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
	    		11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 
	    		21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
	    		31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 
	    		41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
	    		51, 52, 53, 54, 55, 56, 57, 58, 59
	    		};
		JComboBox comboBox_minute = new JComboBox(labels_minute);
		comboBox_minute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				minute_shutdown = (Integer)((JComboBox)e.getSource()).getSelectedItem();
			}
		});
		comboBox_minute.setBounds(10, 20, 40, 40);
		comboBox_minute.setSize(30, 30);
		
	    panel_time.add(comboBox_minute);
	    JLabel label_minute = new JLabel();
	    label_minute.setText("分");
	    panel_time.add(label_minute);
	    frame.getContentPane().add(panel_time);
		
	    //3、操作按钮部分
	    JLabel label_btn = new JLabel();
	    label_btn.setPreferredSize(new Dimension(280,40));
	    label_btn.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
		JButton btn_submit = new JButton("确定");
		btn_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int s_shutdown = hour_shutdown*3600 + minute_shutdown*60 - 
						Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.HOUR_OF_DAY)*3600 - 
						Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.MINUTE)*60;
				if(s_shutdown > 0){//关机时间不得小于当前时间
					try {
						Runtime.getRuntime().exec("shutdown -s -t " + s_shutdown);
						System.out.println("cmd命令：shutdown -s -t " + s_shutdown);
						System.out.println("::系统将于"+hour_shutdown+"时"+minute_shutdown+"分关机");
						lblMsg.setText("");
						//锁定时间选项
						comboBox_minute.setEnabled(false);
						comboBox_hour.setEnabled(false);
						//确定按钮锁定
						btn_submit.setEnabled(false);
					} catch (IOException e1) {
						System.out.println("==关机计划失败");
					}
				}else{
					//提示
					lblMsg.setText("关机时间不得小于当前系统时间");
				}
			}
		});
	
		btn_submit.setBounds(10, 30, 30, 20);
		JButton btn_cancel = new JButton("取消");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("shutdown -a");
					System.out.println("cmd命令：shutdown -a");
					System.out.println("::您已取消关机计划");
					//时间选出解锁
					comboBox_minute.setEnabled(true);
					comboBox_hour.setEnabled(true);
					//确定按钮解锁
					btn_submit.setEnabled(true);
				} catch (IOException e1) {
					System.out.println("==取消关机计划失败");
				}
			}
		});
		
		btn_cancel.setBounds(10, 30, 30, 20);
		label_btn.add(btn_submit);
		label_btn.add(btn_cancel);
		frame.getContentPane().add(label_btn);
		
		frame.getContentPane().add(lblMsg);
		
	}
}
