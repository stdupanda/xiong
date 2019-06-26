package cn.test.browser.v;

import cn.test.browser.e.User;
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

public class LoginDialog extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(LoginDialog.class);

    private static final long serialVersionUID = 1L;

    private JTextField textField;
    private JPasswordField passwordField;

    public LoginDialog(final JFrame frame) {
        super(frame, "登录账户", true);
        Container contentPane = getContentPane();
        this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);
        setBounds(100, 100, 285, 205);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                log.debug("LoginDialog window closed");
            }
        });

        contentPane.setLayout(null);

        final JLabel lblNewLabel = new JLabel("用户名或密码错误！");
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setBounds(71, 90, 168, 15);
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

        JButton button = new JButton("登录");
        button.setBounds(33, 111, 93, 23);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                char[] cs = passwordField.getPassword();
                if (StringUtils.isBlank(text)) {
                    textField.requestFocus();
                    return;
                }
                if (1 > cs.length) {
                    passwordField.requestFocus();
                    return;
                }
                String pwd = new String(cs);
                // 登录
                String sql = "select * from user where name='" + text + "' and password='" + pwd + "'";
                User userRecord = SQLiteService.getInstance().selectOne(sql, User.class);
                if (userRecord == null) {
                    lblNewLabel.setVisible(true);
                    return;
                }
                AppUtil.initLogOper(frame, userRecord);
                log.info("登录成功");
                dispose();
            }
        });
        contentPane.add(button);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(146, 111, 93, 23);
        button_1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(button_1);
    }
}
