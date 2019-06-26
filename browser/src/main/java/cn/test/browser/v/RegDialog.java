package cn.test.browser.v;

import cn.test.browser.s.AppUtil;
import cn.test.browser.s.SQLiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegDialog extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(RegDialog.class);

    private static final long serialVersionUID = 1L;

    private JTextField textField;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;

    public RegDialog(final JFrame frame) {
        super(frame, "注册账户", true);
        Container contentPane = getContentPane();
        this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);
        setBounds(100, 100, 285, 228);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                log.debug("RegDialog window closed");
            }
        });

        contentPane.setLayout(null);

        final JLabel lblNewLabel = new JLabel("错误！");
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setBounds(71, 141, 168, 15);
        lblNewLabel.setVisible(false);
        contentPane.add(lblNewLabel);

        JLabel label = new JLabel("用户名：");
        label.setBounds(33, 24, 54, 15);
        contentPane.add(label);

        JLabel label_1 = new JLabel("密码：");
        label_1.setBounds(33, 65, 54, 15);
        contentPane.add(label_1);

        textField = new JTextField();
        textField.setBounds(97, 21, 114, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(97, 62, 114, 21);
        contentPane.add(passwordField);

        final JLabel label_2 = new JLabel("确认密码：");
        label_2.setBounds(33, 113, 65, 15);
        getContentPane().add(label_2);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(97, 110, 114, 21);
        getContentPane().add(passwordField_1);

        JButton button = new JButton("注册");
        button.setBounds(33, 166, 93, 23);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                char[] cs = passwordField.getPassword();
                char[] cs2 = passwordField_1.getPassword();
                if (StringUtils.isBlank(text)) {
                    textField.requestFocus();
                    return;
                }
                if (1 > cs.length) {
                    passwordField.requestFocus();
                    return;
                }
                if (1 > cs2.length) {
                    passwordField_1.requestFocus();
                    return;
                }
                String pwd = new String(cs);
                String pwd2 = new String(cs2);
                if (!pwd.equals(pwd2)) {
                    lblNewLabel.setText("两次输入密码不一致！");
                    lblNewLabel.setVisible(true);
                    passwordField_1.requestFocus();
                    return;
                }
                // 注册
                String sql = "select count(*) from user where name='" + text + "' ";
                Integer count = SQLiteService.getInstance().count(sql);
                if (count == null || 0 < count) {
                    lblNewLabel.setText("用户名已存在！");
                    textField.requestFocus();
                    lblNewLabel.setVisible(true);
                    return;
                }
                String sql2 = "insert into user(name,password,gmtCreate) values('" + text + "','" + pwd + "','"
                        + AppUtil.genDate() + "') ";
                SQLiteService.getInstance().exec(sql2);
                JOptionPane.showMessageDialog(frame.getContentPane(), "注册成功，请登录！");
                log.info("注册成功");
                dispose();
            }
        });
        contentPane.add(button);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(146, 166, 93, 23);
        button_1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(button_1);
    }
}
