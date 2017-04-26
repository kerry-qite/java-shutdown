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
 * ��ʱ�ػ���ȡ����ʱ�ػ��ƻ�<br>
 * <strong>Warning:</strong><p>����û�ж�ȡϵͳ�ƻ�����
 * ����ÿ�δ��������ʾ���Ĭ��ֵ��<br/>����ϵͳ��ʱ�ػ�Ĭ��ֵ</p>
 * @author kerry
 * @date 2017��3��22��
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
		frame = new JFrame("��ʱ�ػ�");
		frame.setBounds(500, 200, 300, 270);
		frame.setSize(300, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//������彫��ֳ��Ĳ��֣�������ʽ���֡�1����ʾ���֣�2��ʱ��ѡ�3��������ť��4��������Ϣ
		frame.getContentPane().setLayout(new FlowLayout());
		
		//������Ϣ
		JLabel lblMsg = new JLabel();
		lblMsg.setForeground(Color.RED);
		
		//1����ʾ���ֲ���
		JLabel label = new JLabel();
		label.setText("�ػ�ʱ��");
		//label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		frame.getContentPane().add(label);
		
		//2��ʱ��ѡ���
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
	    label_hour.setText("ʱ");
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
	    label_minute.setText("��");
	    panel_time.add(label_minute);
	    frame.getContentPane().add(panel_time);
		
	    //3��������ť����
	    JLabel label_btn = new JLabel();
	    label_btn.setPreferredSize(new Dimension(280,40));
	    label_btn.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
		JButton btn_submit = new JButton("ȷ��");
		btn_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int s_shutdown = hour_shutdown*3600 + minute_shutdown*60 - 
						Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.HOUR_OF_DAY)*3600 - 
						Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.MINUTE)*60;
				if(s_shutdown > 0){//�ػ�ʱ�䲻��С�ڵ�ǰʱ��
					try {
						Runtime.getRuntime().exec("shutdown -s -t " + s_shutdown);
						System.out.println("cmd���shutdown -s -t " + s_shutdown);
						System.out.println("::ϵͳ����"+hour_shutdown+"ʱ"+minute_shutdown+"�ֹػ�");
						lblMsg.setText("");
						//����ʱ��ѡ��
						comboBox_minute.setEnabled(false);
						comboBox_hour.setEnabled(false);
						//ȷ����ť����
						btn_submit.setEnabled(false);
					} catch (IOException e1) {
						System.out.println("==�ػ��ƻ�ʧ��");
					}
				}else{
					//��ʾ
					lblMsg.setText("�ػ�ʱ�䲻��С�ڵ�ǰϵͳʱ��");
				}
			}
		});
	
		btn_submit.setBounds(10, 30, 30, 20);
		JButton btn_cancel = new JButton("ȡ��");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("shutdown -a");
					System.out.println("cmd���shutdown -a");
					System.out.println("::����ȡ���ػ��ƻ�");
					//ʱ��ѡ������
					comboBox_minute.setEnabled(true);
					comboBox_hour.setEnabled(true);
					//ȷ����ť����
					btn_submit.setEnabled(true);
				} catch (IOException e1) {
					System.out.println("==ȡ���ػ��ƻ�ʧ��");
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
